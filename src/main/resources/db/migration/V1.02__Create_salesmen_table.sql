CREATE TABLE IF NOT EXISTS salesmen (
  id         SERIAL NOT NULL PRIMARY KEY,
  name       TEXT   NOT NULL,
  salary     INTEGER,
  birth_year INTEGER
);