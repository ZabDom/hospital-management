CREATE TABLE patients
(
    id              int         NOT NULL,
    name            varchar(30) NOT NULL,
    date_of_birth   varchar(10) NOT NULL,
    date_of_arrival varchar(10) NOT NULL,
    date_of_leave   varchar(10),
    current_status  int         NOT NULL CHECK (current_status >= 1 AND current_status <= 5)
);