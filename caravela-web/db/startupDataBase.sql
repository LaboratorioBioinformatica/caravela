CREATE USER 'caravela-user'@'localhost' IDENTIFIED BY 'c@r@vela';

/* create database */ 
create schema caravela;

/* giving privileges to user METAZOO on database METAZOO */  
GRANT ALL PRIVILEGES ON caravela.* TO 'caravela-user'@'localhost';
flush privileges;

USE caravela;
