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
