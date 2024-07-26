DROP TABLE IF EXISTS posts;
CREATE TABLE IF NOT EXISTS posts
(
   id varchar (255) NOT NULL,
   author varchar (20) NULL,
   title varchar (20) NULL,
   body varchar (1000) NULL,
   deleted bool NOT NULL,
   created_date timestamp NULL,
   updated_date timestamp NULL,
   PRIMARY KEY (id)
);