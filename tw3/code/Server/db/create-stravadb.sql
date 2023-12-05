/* DELETE 'stravadb' database */
DROP SCHEMA IF EXISTS stravadb;

/* DELETE USER 'strava_user' AT LOCAL SERVER */
DROP USER IF EXISTS 'strava_user'@'%';

/* CREATE 'stravadb' database */
CREATE SCHEMA IF NOT EXISTS stravadb;

/* CREATE USER 'strava_user' AT LOCAL SERVER WITH PASSWORD 'password' */
CREATE USER IF NOT EXISTS 'strava_user'@'%' IDENTIFIED BY 'password';

/* GRANT FULL ACCESS TO THE DATABASE 'stravadb' FOR THE USER 'strava_user' AT LOCAL SERVER */
GRANT ALL ON stravadb.* TO 'strava_user'@'%';