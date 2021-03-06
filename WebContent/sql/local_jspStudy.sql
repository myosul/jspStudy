create table member (
member_no number not null,
member_id varchar2(10) not null,
member_password varchar2(250) not null,
member_name varchar2(15) not null,
member_gender varchar2(50) not null check (member_gender in ('M', 'F')),
member_born_year number not null,
member_postcode number not null,
member_address varchar2(150) not null,
member_detail_address varchar2(150) not null,
member_extra_address varchar2(150) null,
member_regi_date timestamp default current_timestamp,
primary key(member_no)
);

create sequence seq_member start with 1 increment by 1 nomaxvalue nocache;



create table memo (
memo_no number not null,
memo_writer_no number not null,
memo_subject varchar2(100) not null,
memo_content clob not null,
memo_modi_date timestamp default current_timestamp,
memo_regi_date timestamp default current_timestamp,
primary key(memo_no)
);

create sequence seq_memo start with 1 increment by 1 nomaxvalue nocache;


create table guestbook (
guestbook_no number not null,
guestbook_writer_name varchar2(50) not null,
guestbook_writer_email varchar2(50) not null,
guestbook_passwd varchar2(50) not null,
guestbook_content clob not null,
guestbook_regi_date timestamp default current_timestamp,
primary key(guestbook_no)
);

create sequence seq_guestbook start with 1 increment by 1 nomaxvalue nocache;



create table survey (
survey_no number not null,
survey_question varchar2(4000) not null,
survey_answer1 varchar2(500) not null,
survey_answer2 varchar2(500) not null,
survey_answer3 varchar2(500) not null,
survey_answer4 varchar2(500) not null,
survey_answer5 varchar2(500) not null,
survey_status char(1) default '1', -- 설문진행상태 (1 진행중, 0 종료)
survey_start_date timestamp default current_timestamp,
survey_end_date timestamp default current_timestamp,
survey_regi_date timestamp default current_timestamp,
primary key(survey_no)
);

create sequence seq_survey start with 1 increment by 1 nomaxvalue nocache;



create table survey_answer (
survey_answer_no number not null primary key,
survey_no number not null references survey(survey_no),
survey_answer number not null ,
survey_answer_regi_date timestamp default current_timestamp
);

create sequence seq_survey_answer start with 1 increment by 1 nomaxvalue nocache;



create table board (
board_no number not null,
board_num number not null,
board_tbl varchar2(50) not null,
board_writer varchar2(50) not null,
board_subject varchar2(50) not null,
board_content clob not null,
board_email varchar2(50) not null,
board_passwd varchar2(50) not null,
board_ref_no number not null,
board_step_no number not null,
board_level_no number not null,
board_parent_no number not null,
board_hit number not null,
board_ip varchar2(50) not null,
member_no number not null,
board_notice_no number not null,
board_secret varchar2(1) not null check (board_secret in ('T', 'F')),
board_regi_date timestamp default current_timestamp,
primary key(board_no)
);

create sequence seq_board start with 1 increment by 1 nomaxvalue nocache;


CREATE TABLE board_comment (
board_comment_no NUMBER NOT NULL,
board_no NUMBER NOT NULL REFERENCES board(board_no),
board_comment_writer varchar2(50) NOT NULL,
board_comment_content clob NOT NULL,
board_comment_passwd varchar2(50) NOT NULL,
member_no NUMBER NOT NULL,
board_comment_ip varchar2(50) NOT NULL,
board_comment_regi_date timestamp default current_timestamp,
PRIMARY KEY(board_comment_no)
);

CREATE SEQUENCE seq_board_comment start with 1 increment by 1 nomaxvalue nocache;


CREATE TABLE board_type (
board_type_no NUMBER NOT NULL, 
board_type varchar2(50) NOT NULL, 
board_type_name varchar2(50) NOT NULL, 
board_type_use varchar2(1) not null check (board_type_use in ('T', 'F')), 
board_type_regi_date timestamp default current_timestamp,
PRIMARY KEY(board_type_no)
);

CREATE SEQUENCE seq_board_type start with 1 increment by 1 nomaxvalue nocache;

INSERT INTO BOARD_TYPE 
VALUES (
seq_board_type.nextval, 'freeboard', '자유게시판', 'T', current_timestamp
);

INSERT INTO BOARD_TYPE 
VALUES (
seq_board_type.nextval, 'funnyboard', '유머게시판', 'T', current_timestamp
);

INSERT INTO BOARD_TYPE 
VALUES (
seq_board_type.nextval, 'memberboard', '회원전용게시판', 'F', current_timestamp
);

INSERT INTO BOARD_TYPE 
VALUES (
seq_board_type.nextval, 'javaboard', '자바게시판', 'T', current_timestamp
);

COMMIT;

CREATE TABLE product (
product_no NUMBER NOT NULL,
product_name varchar2(50) NOT NULL,
product_price NUMBER DEFAULT 0,
product_description clob,
product_img varchar2(1000) NOT NULL,
product_regi_date timestamp default current_timestamp,
PRIMARY KEY(product_no)
);

CREATE SEQUENCE seq_product start with 1 increment by 1 nomaxvalue nocache;


CREATE TABLE cart (
cart_no NUMBER NOT NULL PRIMARY KEY, -- 일련코드
member_no NUMBER NOT NULL, -- 사용자코드
product_no NUMBER NOT NULL, -- 상품코드
product_amount NUMBER DEFAULT 0, -- 수량
cart_regi_date timestamp DEFAULT current_timestamp
);

CREATE SEQUENCE seq_cart start with 1 increment by 1 nomaxvalue nocache;

ALTER TABLE cart ADD constraint fk_cart_member_no
FOREIGN key(member_no) REFERENCES member(member_no);

ALTER TABLE cart ADD constraint fk_cart_product_no
FOREIGN key(product_no) REFERENCES product(product_no);

-- ALTER TABLE cart DROP CONSTRAINT fk_cart_product_no;

-- alter table cart drop constraint fk_cart_member_no;

SELECT * FROM product;

TRUNCATE TABLE product;

SELECT * FROM cart;

TRUNCATE TABLE cart;

COMMIT;


select c.*, p.PRODUCT_NAME , p.PRODUCT_PRICE , p.PRODUCT_IMG, (c.product_amount * p.PRODUCT_PRICE) cart_buy_money 
from cart c, product p
where c.product_no = p.product_no
AND member_no = 1
order by cart_regi_date DESC;
