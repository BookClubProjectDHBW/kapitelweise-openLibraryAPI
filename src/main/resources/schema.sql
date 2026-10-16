-- Bücher

CREATE TABLE IF NOT EXISTS book_suggestions (
                      book_suggestion_id           BIGSERIAL PRIMARY KEY,
                      title              VARCHAR(45) NOT NULL,
                      author             VARCHAR(75) NOT NULL,
                      isbn               VARCHAR(45) NOT NULL UNIQUE,
                      pages              INT,
                      year_of_publishing DATE
);

