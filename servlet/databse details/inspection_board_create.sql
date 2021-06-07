-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2021-06-03 17:51:25.459

-- tables
-- Table: Account
CREATE TABLE Account (
    id serial  NOT NULL,
    login varchar(250)  NOT NULL,
    password varchar(250)  NOT NULL,
    role_id int  NOT NULL,
    CONSTRAINT account_unique UNIQUE (login) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Account_pk PRIMARY KEY (id)
);

-- Table: Enrollee
CREATE TABLE Enrollee (
    id int  NOT NULL,
    first_name varchar(250)  NOT NULL,
    father_name varchar(250)  NOT NULL,
    last_name varchar(250)  NOT NULL,
    email varchar(250)  NOT NULL,
    city varchar(250)  NOT NULL,
    region varchar(250)  NOT NULL,
    school_name varchar(250)  NOT NULL,
    certificate_scan bytea  NULL,
    CONSTRAINT Enrollee_pk PRIMARY KEY (id)
);

-- Table: Faculty
CREATE TABLE Faculty (
    id serial  NOT NULL,
    name varchar(250)  NOT NULL,
    budget_places int  NOT NULL,
    all_places int  NOT NULL,
    CONSTRAINT Faculty_pk PRIMARY KEY (id)
);

-- Table: Mark
CREATE TABLE Mark (
    enrollee_id int  NOT NULL,
    subject_id int  NOT NULL,
    mark int  NOT NULL,
    CONSTRAINT Mark_pk PRIMARY KEY (subject_id,enrollee_id)
);

-- Table: Registration
CREATE TABLE Registration (
    id serial  NOT NULL,
    faculty_id int  NOT NULL,
    enrolle_id int  NOT NULL,
    registration_status_id int  NOT NULL,
    CONSTRAINT Registration_pk PRIMARY KEY (id)
);

-- Table: Registration_Status
CREATE TABLE Registration_Status (
    id serial  NOT NULL,
    status_name varchar(250)  NOT NULL,
    CONSTRAINT Registration_Status_pk PRIMARY KEY (id)
);

-- Table: Required_subject
CREATE TABLE Required_subject (
    faculty_id int  NOT NULL,
    subject_id int  NOT NULL,
    minimal_grade int  NOT NULL,
    CONSTRAINT Required_subject_pk PRIMARY KEY (subject_id,faculty_id)
);

-- Table: Role
CREATE TABLE Role (
    id serial  NOT NULL,
    name varchar(250)  NOT NULL,
    CONSTRAINT Role_pk PRIMARY KEY (id)
);

-- Table: Subject
CREATE TABLE Subject (
    id serial  NOT NULL,
    name varchar(250)  NOT NULL,
    CONSTRAINT name_unique UNIQUE (name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Subject_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: Account_Role (table: Account)
ALTER TABLE Account ADD CONSTRAINT Account_Role
    FOREIGN KEY (role_id)
    REFERENCES Role (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Enrollee_Account (table: Enrollee)
ALTER TABLE Enrollee ADD CONSTRAINT Enrollee_Account
    FOREIGN KEY (id)
    REFERENCES Account (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Mark_Enrollee (table: Mark)
ALTER TABLE Mark ADD CONSTRAINT Mark_Enrollee
    FOREIGN KEY (enrollee_id)
    REFERENCES Enrollee (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Mark_Subject (table: Mark)
ALTER TABLE Mark ADD CONSTRAINT Mark_Subject
    FOREIGN KEY (subject_id)
    REFERENCES Subject (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Registration_Enrollee (table: Registration)
ALTER TABLE Registration ADD CONSTRAINT Registration_Enrollee
    FOREIGN KEY (enrolle_id)
    REFERENCES Enrollee (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Registration_Faculty (table: Registration)
ALTER TABLE Registration ADD CONSTRAINT Registration_Faculty
    FOREIGN KEY (faculty_id)
    REFERENCES Faculty (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Registration_Registration_Status (table: Registration)
ALTER TABLE Registration ADD CONSTRAINT Registration_Registration_Status
    FOREIGN KEY (registration_status_id)
    REFERENCES Registration_Status (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Required_subjects_Faculty (table: Required_subject)
ALTER TABLE Required_subject ADD CONSTRAINT Required_subjects_Faculty
    FOREIGN KEY (faculty_id)
    REFERENCES Faculty (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Required_subjects_Subject (table: Required_subject)
ALTER TABLE Required_subject ADD CONSTRAINT Required_subjects_Subject
    FOREIGN KEY (subject_id)
    REFERENCES Subject (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

