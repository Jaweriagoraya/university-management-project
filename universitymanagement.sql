CREATE DATABASE college;
USE college;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL
);

select * from users;
INSERT INTO users (username, password) VALUES ('admin', 'admin123');
INSERT INTO users (username, password) VALUES ('jaweria', 'jaweria123');
INSERT INTO users (username, password) VALUES ('arisha', 'arisha');

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    frame VARCHAR(100),
    rollno VARCHAR(50) NOT NULL,
    dob DATE,
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    classX FLOAT,
    classXII FLOAT,
    aadhar VARCHAR(20),
    course VARCHAR(100),
    branch VARCHAR(100)
);

select * from students;
CREATE TABLE fee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    course VARCHAR(100),
    branch VARCHAR(100),
    rollno VARCHAR(50),
    semester VARCHAR(50),
    total_payable FLOAT,
    status VARCHAR(20));
SELECT * FROM fee ;
insert into fee (name,course,branch,rollno, semester,total_payable,status) VALUE('jaweria','oop','cs',103,3,15000,'unpaid');


CREATE TABLE teachers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    father_name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    aadhar_number VARCHAR(20), -- Fixed from "aadhar"
    qualification VARCHAR(100),
    department VARCHAR(100)
);
select * from teachers;
CREATE TABLE leave_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL,
    leave_date DATE NOT NULL,
    duration VARCHAR(50) NOT NULL
);
select * from leave_requests;

CREATE TABLE marks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(50) NOT NULL,
    semester VARCHAR(50) NOT NULL,
    subject VARCHAR(100) NOT NULL,
    marks FLOAT NOT NULL
);
select* from marks;

CREATE TABLE fee_structure (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course VARCHAR(100) NOT NULL,
    semester1 FLOAT,
    semester2 FLOAT,
    semester3 FLOAT,
    semester4 FLOAT,
    semester5 FLOAT,
    semester6 FLOAT,
    semester7 FLOAT,
    semester8 FLOAT
);
select * from fee_structure;
-- Insert sample data
INSERT INTO fee_structure (course, semester1, semester2, semester3, semester4, semester5, semester6, semester7, semester8)
VALUES 
    ('B.Tech', 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000),
    ('BCA', 30000, 30000, 30000, 30000, 30000, 30000, 30000, 30000),
    ('M.Tech', 60000, 60000, 60000, 60000, 60000, 60000, 60000, 60000),
    ('M.Sc', 40000, 40000, 40000, 40000, 40000, 40000, 40000, 40000),
    ('MCA', 45000, 45000, 45000, 45000, 45000, 45000, 45000, 45000),
    ('B.Com', 25000, 25000, 25000, 25000, 25000, 25000, 25000, 25000),
    ('M.Com', 35000, 35000, 35000, 35000, 35000, 35000, 35000, 35000);



CREATE TABLE classes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(50) NOT NULL
);


CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL
);

INSERT INTO courses (course_name) VALUES 
('BTech'), ('BCA'), ('MTech'), ('MCA'), ('M.Sc'), ('B.Com'), ('M.Com');

CREATE TABLE branches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    branch_name VARCHAR(100) NOT NULL
);

INSERT INTO branches (branch_name) VALUES 
('Computer Science'), ('IT'), ('OOP'), ('Database'), ('ECE');

CREATE TABLE semesters (
    id INT AUTO_INCREMENT PRIMARY KEY,
    semester_name VARCHAR(50) NOT NULL
);

INSERT INTO semesters (semester_name) VALUES 
('Semester1'), ('Semester2'), ('Semester3'), ('Semester4'),
('Semester5'), ('Semester6'), ('Semester7'), ('Semester8');

