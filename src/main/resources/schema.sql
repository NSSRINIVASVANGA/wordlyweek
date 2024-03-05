CREATE TABLE if not exists magazine(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    title TEXT,
    publicationDate TEXT
);

CREATE TABLE if not exists writer(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    bio TEXT
);

CREATE TABLE if not exists writer_magazine(
    writerId INTEGER,
    magazineId INTEGER,
    PRIMARY KEY(writerId,magazineId),
    FOREIGN KEY (writerId) REFERENCES writer(id),
    FOREIGN KEY (magazineId) REFERENCES magazine(id)
);