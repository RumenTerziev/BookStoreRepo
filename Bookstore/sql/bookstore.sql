CREATE DATABASE `bookstore`;

USE `bookstore`;

CREATE TABLE `books`(
	`id` INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `title` VARCHAR(100) NOT NULL,
    `author` VARCHAR(100) NOT NULL
);


CREATE TABLE `comments`(
	`id` INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `comment` VARCHAR(200) NOT NULL,
    `book_id` INT NOT NULL,
    CONSTRAINT `fk_comments_books`
    FOREIGN KEY (`book_id`)
    REFERENCES `books`(`id`)
);


INSERT INTO `books`(`title`, `author`)
VALUES ('Game Of Thrones', 'George R.R. Martin'),
		('Harry Potter', 'J. K. Rowling'),
        ('Madame Bovary', 'Gustave Flaubert'),
        ('The Green Mile', 'Stephen King'),
        ('The Shining', 'Stephen King'),
        ('It', 'Stephen King'),
        ('The Boogeyman', 'Stephen King'),
        ('Fairy Tale', 'Stephen King');