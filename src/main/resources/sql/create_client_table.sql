DROP TABLE IF EXISTS client;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE client(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name CHARACTER VARYING(30) NOT NULL,
    surname CHARACTER VARYING(30) NOT NULL,
    patronymic CHARACTER VARYING(30),
    gender CHARACTER(1) NOT NULL CHECK (gender = 'М' OR gender = 'Ж'),
    birth_date DATE NOT NULL CHECK (birth_date >= '1900-01-01' AND birth_date < CURRENT_DATE),
    email CHARACTER VARYING NOT NULL UNIQUE CHECK (email ~ '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$'),
    phone CHARACTER(10) NOT NULL UNIQUE CHECK (phone ~ '^\d{10}$')
);

-- Функиция для приведения к нижнему регистру поля email
CREATE OR REPLACE FUNCTION lower_email()
RETURNS TRIGGER AS $$
BEGIN
    NEW.email := LOWER(NEW.email);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Триггер для срабатывания функции перед внесением изменений в бд
CREATE TRIGGER email_lowercase
BEFORE INSERT OR UPDATE ON client
FOR EACH ROW
EXECUTE FUNCTION lower_email();
