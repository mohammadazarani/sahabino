CREATE USER 'spring'@'localhost';
GRANT ALL PRIVILEGES ON candles.* To 'spring'@'localhost' IDENTIFIED BY 'spring';
FLUSH PRIVILEGES;
