CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) UNIQUE NOT NULL,
                                     password VARCHAR(100) NOT NULL
);


CREATE TABLE IF NOT EXISTS login_attempts (
                                              id SERIAL PRIMARY KEY,
                                              username VARCHAR(255) NOT NULL,
                                              attempts INT NOT NULL,
                                              blocked BOOLEAN NOT NULL,
                                              blocked_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS imdb_top (
                             id SERIAL PRIMARY KEY,
                             rank INT,
                             title VARCHAR(255),
                             year VARCHAR(10),
                             rating VARCHAR(10)
);
drop table imdb_top;