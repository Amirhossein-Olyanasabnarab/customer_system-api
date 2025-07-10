CREATE TABLE customer(
    id BIGINT PRIMARY KEY AUTO_INCREMENT ,
    name  VARCHAR(30),
    family VARCHAR(50),
    phone_number VARCHAR(25),
    type VARCHAR(10),
);

CREATE TABLE real_customer(
    id BIGINY PRIMARY KEY ,
    nationality VARCHAR(20),
    FOREIGN KEY (id) REFERENCES customer(id) ON DELETE CASCADE
);

CREATE TABLE legal_customer(
    id BIGINT PRIMARY KEY ,
    industry VARCHAR(20),
    FOREIGN KEY (id) REFERENCES customer(id) ON DELETE CASCADE
);