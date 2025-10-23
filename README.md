
EchoShelf Library Management System
===================================

EchoShelf is a Java Swing–based library management application that connects to a local MySQL database.
It allows users to view, search, borrow, and return books with a simple desktop interface.

------------------------------------------------------------
FEATURES
------------------------------------------------------------
- View all books from the database
- Search by title, author, or ISBN
- Borrow a book (stores borrower name and due date)
- Return a book
- Persistent MySQL storage using JDBC

------------------------------------------------------------
TECH STACK
------------------------------------------------------------
Language: Java 17 or higher
GUI: Swing
Database: MySQL 8+
JDBC Driver: MySQL Connector/J 9.5.0
IDE: Visual Studio Code (PowerShell terminal)

------------------------------------------------------------
SETUP INSTRUCTIONS
------------------------------------------------------------
1. Clone the project
   git clone https://github.com/yourusername/echoshelf.git
   cd echoshelf

2. Create the MySQL database
   Login to MySQL:
       mysql -u root -p
   Then run:
       CREATE DATABASE IF NOT EXISTS echoshelf;
       USE echoshelf;
       CREATE TABLE IF NOT EXISTS books (
         id INT AUTO_INCREMENT PRIMARY KEY,
         title VARCHAR(255),
         author VARCHAR(255),
         isbn VARCHAR(100),
         available BOOLEAN DEFAULT TRUE,
         borrower VARCHAR(255),
         due_date DATE
       );
       INSERT INTO books (title, author, isbn, available) VALUES
       ('The Hobbit', 'J.R.R. Tolkien', '9780261102217', TRUE),
       ('1984', 'George Orwell', '9780451524935', TRUE),
       ('Clean Code', 'Robert C. Martin', '9780132350884', TRUE);

3. Verify MySQL credentials in src/echo/core/Database.java
       USER = "root";
       PASSWORD = "Leo@net$44";

4. Compile the project (PowerShell command)
       mkdir bin
       javac -cp "lib/mysql-connector-j-9.5.0.jar" -d bin (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })

5. Run the application
       java -cp "bin;lib/mysql-connector-j-9.5.0.jar" echo.Main

------------------------------------------------------------
USAGE
------------------------------------------------------------
1. Click "Show All Books" to load data.
2. Use "Search" to find a specific title/author/ISBN.
3. Select a row and click "Borrow Book" → enter borrower name and number of days.
4. Select a borrowed book and click "Return Book" to mark it available again.

------------------------------------------------------------
PROJECT STRUCTURE
------------------------------------------------------------
echoshelf/
 ├─ lib/
 │   └─ mysql-connector-j-9.5.0.jar
 ├─ src/
 │   └─ echo/
 │        ├─ core/
 │        │    ├─ Database.java
 │        │    └─ Library.java
 │        ├─ models/
 │        │    └─ Book.java
 │        ├─ LibraryGUI.java
 │        └─ Main.java
 ├─ bin/
 ├─ README.txt
 └─ LICENSE (optional)

------------------------------------------------------------
TROUBLESHOOTING
------------------------------------------------------------
Problem: Access denied error
  → Check MySQL username/password in Database.java

Problem: ClassNotFoundException for MySQL driver
  → Ensure mysql-connector-j-9.5.0.jar exists in lib/

Problem: GUI opens but empty table
  → Confirm database and data were created in echoshelf.books

Problem: Connection refused
  → Ensure MySQL service is running on port 3306

------------------------------------------------------------
FUTURE IMPROVEMENTS
------------------------------------------------------------
- Add user login and role management
- Support for late-return tracking
- Export reports to CSV or PDF
- Modernize GUI with JavaFX

------------------------------------------------------------
LICENSE
------------------------------------------------------------
You may use this project under the MIT License or another open-source license.
