SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departments (
id int PRIMARY KEY auto_increment,
  departmentName VARCHAR,
  departmentDescription VARCHAR,
  departmentEmployeesNumber INTEGER
);

CREATE TABLE IF NOT EXISTS users (
id int PRIMARY KEY auto_increment,
  userName VARCHAR,
  userCompanyPosition VARCHAR,
  userCompanyRole VARCHAR,
  departmentId INTEGER
);

CREATE TABLE IF NOT EXISTS news (
id int PRIMARY KEY auto_increment,
  newsTitle VARCHAR,
  newsContent VARCHAR,
  departmentId INTEGER
);

CREATE TABLE IF NOT EXISTS departments_users (
id int PRIMARY KEY auto_increment,
departmentId INTEGER,
userId INTEGER
);

CREATE TABLE IF NOT EXISTS departments_news(
id int PRIMARY KEY auto_increment,
departmentId INTEGER,
newsId INTEGER
);