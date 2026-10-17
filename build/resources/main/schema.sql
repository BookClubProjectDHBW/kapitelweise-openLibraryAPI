-- Bücher

CREATE TABLE IF NOT EXISTS book_suggestions (
                      book_suggestion_id           BIGSERIAL PRIMARY KEY,
                      title              VARCHAR(45) NOT NUll,
                      author             VARCHAR(75),
                      isbn               VARCHAR(45) UNIQUE,
                      pages              INT,
                      year_of_publishing INT,
                      saved_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

