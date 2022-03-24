CREATE DATABASE IF NOT EXISTS labyrinthadventure;
USE labyrinthadventure;
CREATE TABLE IF NOT EXISTS HighScore (
	LabSize 	     INT NOT NULL,
	Score  			 INT, 
  PRIMARY KEY(Score,LabSize
);