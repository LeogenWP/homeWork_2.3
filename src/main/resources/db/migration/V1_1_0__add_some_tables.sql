CREATE TABLE IF NOT EXISTS `writers` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` varchar(255),
    `last_name` varchar(255)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `posts` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `content` varchar(255),
    `created` varchar(255),
    `updated` varchar(255),
    `post_status` varchar(255),
    'writer_id' int,
    FOREIGN KEY (writer_id) REFERENCES writers(id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;


CREATE TABLE IF NOT EXISTS `labels` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `description` varchar(255)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `posts_labels` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `post_id` int,
    `label_id` int,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (label_id) REFERENCES labels(id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

