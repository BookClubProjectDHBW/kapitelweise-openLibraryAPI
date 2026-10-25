package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao.BookSearchDao;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain.FoundBook;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain.SuggestionEvent;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain.SuggestionResultEvent;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.request.BookSearchRequest;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response.BookResponse;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response.BookSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AmazonSQSService {

    private static final Logger log = LoggerFactory.getLogger(AmazonSQSService.class);


    private final AmazonSQS sqs;
    private final JsonMapper jsonMapper;
    private final BookSearchDao bookSearchDao;
    private final BookSearchService bookSearchService;
    private static final String CHECK_SUGGESTION_URL = "https://sqs.eu-north-1.amazonaws.com/608531435564/suggestionCheck.fifo";
    private static final String SUGGESTION_RESULT_URL = "https://sqs.eu-north-1.amazonaws.com/608531435564/suggestionResult.fifo";

    public AmazonSQSService(AmazonSQS sqs, JsonMapper jsonMapper, BookSearchDao bookSearchDao, BookSearchService bookSearchService) {
        this.sqs = sqs;
        this.jsonMapper = jsonMapper;
        this.bookSearchDao = bookSearchDao;
        this.bookSearchService = bookSearchService;
    }

    public void sendSuggestionResult(SuggestionResultEvent resultEvent){

        try {
            String body = jsonMapper.writeValueAsString(resultEvent);
            SendMessageRequest send = new SendMessageRequest()
                    .withQueueUrl(SUGGESTION_RESULT_URL)
                    .withMessageBody(body)
                    .withMessageGroupId(String.valueOf(resultEvent.suggestionId()))
                    .withMessageDeduplicationId(String.valueOf(resultEvent.suggestionId()));
            sqs.sendMessage(send);
        } catch (Exception e) {
            log.error("SuggestionResultEvent konnte nicht gesendet werden: " + e.getMessage() + resultEvent.suggestionId());
        }
    }


    @Scheduled(fixedDelay = 1000)
    public void pollNewSuggestionCheck(){
        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(CHECK_SUGGESTION_URL)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(5);


        List<Message> messages = sqs.receiveMessage(request).getMessages();
        for(Message message:messages){
            log.info("SQS-Nachricht von SuggestionQueue empfangen: " + message.getBody());
            try {
                SuggestionEvent event = jsonMapper.readValue(message.getBody(), SuggestionEvent.class);

                if (event.type().equals("SEND_SUGGESTION")) {
                    Optional<FoundBook> found = bookSearchDao.getDetails(event.isbn());

                    if(found.isEmpty()){
                        BookSearchResponse result = bookSearchService.searchBooks(new BookSearchRequest(null, null, null, event.isbn()));

                        if (result != null && result.getDocs() != null && !result.getDocs().isEmpty()) {
                            BookResponse book = result.getDocs().get(0);
                            found = Optional.of(new FoundBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPages(), book.getFirstPublishYear()));
                        }
                    }

                    if (found.isEmpty()) {
                        sendSuggestionResult(new SuggestionResultEvent("SUGGESTION_RESULT", event.suggestionId(), "FAILED", null, null, event.isbn(), null, null));
                    } else {
                        FoundBook book = found.get();
                        sendSuggestionResult(new SuggestionResultEvent("SUGGESTION_RESULT", event.suggestionId(), "READY", book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPages(), book.getFirstPublishYear()));
                    }
                }

                sqs.deleteMessage(CHECK_SUGGESTION_URL, message.getReceiptHandle());
            } catch (Exception e) {
                log.error("SQS-Nachricht konnte nicht verarbeitet werden: " + e.getMessage());
            }
        }
    }


}
