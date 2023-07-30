/*
1. Create Artist table
2. Load data from csv to table
3. Create Artist detail table
*/
CREATE TABLE artists (
    id INT AUTO_INCREMENT PRIMARY KEY,
    artist VARCHAR(255)
);

LOAD DATA INFILE '/var/lib/mysql-files/dataset.csv' INTO TABLE artists COLUMNS TERMINATED BY ',' LINES TERMINATED BY '\r\n' IGNORE 1 LINES (artist);


CREATE TABLE  artists_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    column1_data_type column1_name,
    column2_data_type column2_name,
    ... -- Add more columns as needed
);

/*
Only for demo
*/
CREATE TABLE artists2 (
    artist_id INT AUTO_INCREMENT PRIMARY KEY,
    artist VARCHAR(255)
);

LOAD DATA INFILE '/var/lib/mysql-files/dataset.csv' INTO TABLE artists2 COLUMNS TERMINATED BY ',' LINES TERMINATED BY '\r\n' IGNORE 1 LINES (artist);



drop database batch_repo;
create  database batch_repo;

ALTER TABLE artists_details
ADD image VARCHAR(255);

DROP TABLE IF EXISTS artists_details;
CREATE TABLE artists_details (
    artist_id INT AUTO_INCREMENT PRIMARY KEY,
    artist VARCHAR(255),
    popularity BIGINT,
    followers BIGINT,
    image VARCHAR(255)
);

