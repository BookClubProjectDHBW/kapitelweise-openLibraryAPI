-- Bücher

CREATE TABLE IF NOT EXISTS found_book (
                      found_book_id      BIGSERIAL PRIMARY KEY,
                      title              VARCHAR(200) NOT NUll,
                      author             VARCHAR(75),
                      isbn               VARCHAR(45) NOT NULL UNIQUE,
                      pages              INT,
                      year_of_publishing INT,
                      saved_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
