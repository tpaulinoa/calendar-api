CREATE TABLE IF NOT EXISTS
    interviewer (
        id varchar(255) NOT NULL,
        name varchar(255) NOT NULL,
        email varchar(255) NOT NULL,
        phone varchar(20),
        PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS
    candidate (
        id varchar(255) NOT NULL,
        name varchar(255) NOT NULL,
        email varchar(255) NOT NULL,
        phone varchar(20),
        linkedin_profile varchar(100),
        PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS
    slot (
        id varchar(255) NOT NULL,
        start_datetime timestamp NOT NULL,
        end_datetime timestamp NOT NULL,
        person_id varchar(255) NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT UC_Slot UNIQUE (start_datetime, end_datetime, person_id)
    );
