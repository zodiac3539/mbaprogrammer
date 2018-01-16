CREATE TABLE usermgmt (
    userid VARCHAR(200) primary key,
    userpw VARCHAR(200),
    username VARCHAR(200),
    userlevel INT
)

RENAME TABLE memorize TO memorize_tmp;

CREATE TABLE memorize (
    seq BIGINT primary key,
    categoryseq BIGINT,
    userid VARCHAR(200),
    question NVARCHAR(4000),
    answer NVARCHAR(4000),
    wheninserted BIGINT,
    correct INT,
    wrong INT
);

INSERT INTO memorize SELECT seq, categoryseq, userid, question, answer, wheninserted, correct, wrong FROM memorize_tmp;
DROP TABLE memorize_tmp;


CREATE TABLE category (
    categoryseq BIGINT primary key,
    userid VARCHAR(200),
    categoryname VARCHAR(200)
)

INSERT INTO usermgmt VALUES('grechoi', '1q2w3e4r!', 'Gregory Choi', 1);
INSERT INTO category VALUES(1, 'grechoi', 'English');
INSERT INTO category VALUES(2, 'grechoi', 'Japanese');

CREATE TABLE bulletin (
    categoryseq BIGINT primary key,
    bcategory VARCHAR(255),
    subject VARCHAR(255),
    content NVARCHAR(4000),
    userid VARCHAR(200),
    filename VARCHAR(200),
    realname VARCHAR(200)
);

