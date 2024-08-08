CREATE TABLE IF NOT EXISTS clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shared_key VARCHAR(255) NOT NULL UNIQUE,
    business_id VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL
);
DELETE FROM clients;
INSERT INTO clients (shared_key, business_id, email, phone, start_date, end_date) VALUES ('jgutierrez', 'Juliana Gutierrez', 'jgutierrez@gmail.com', '1234567', '2024-08-01', '2024-08-30');
INSERT INTO clients (shared_key, business_id, email, phone, start_date, end_date) VALUES ('mmartinez', 'Manuel Martinez', 'mmartinez@gmail.com', '78945612', '2024-07-01', '2024-07-30');