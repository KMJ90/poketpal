create database PoketPalDB;
use PoketPalDB;

CREATE TABLE TBL_USER (
				USER_ID varchar(15) NOT NULL primary key COMMENT '아이디',
                USER_PWD varchar(15) COMMENT '비밀번호',
                USER_NAME varchar(7) COMMENT '이름'
                ) COMMENT = '회원정보';
	
CREATE TABLE TBL_PAL_GRADE (
				PAL_GRADE varchar(3) NOT NULL primary key COMMENT '팰등급',
                CHANCE int COMMENT '잡힐확률'
                ) COMMENT = '팰등급정보';
                
CREATE TABLE TBL_PAL (
				PAL_NO int NOT NULL auto_increment primary key COMMENT '팰번호',
				PAL_NAME varchar(6) COMMENT '팰이름',
				PAL_TYPE varchar(3) COMMENT '팰타입',
				PAL_GRADE varchar(3) NOT NULL COMMENT '팰등급',
				FOREIGN KEY (PAL_GRADE) REFERENCES TBL_PAL_GRADE(PAL_GRADE)
				) COMMENT = '팰정보';

CREATE TABLE TBL_CATCH_PAL (
				PAL_INDEX int NOT NULL auto_increment primary key COMMENT '보유팰번호',
                USER_ID varchar(15) NOT NULL COMMENT '회원 아이디',
                PAL_NO int NOT NULL COMMENT '팰번호',
                FOREIGN KEY (USER_ID) REFERENCES TBL_USER(USER_ID),
                FOREIGN KEY (PAL_NO) REFERENCES TBL_PAL(PAL_NO)
                )COMMENT = '보유중인 팰정보';
                
CREATE TABLE TBL_PALDEX_BOOK (
				PALDEX_NO int NOT NULL auto_increment primary key COMMENT '도감번호',
                USER_ID varchar(15) NOT NULL COMMENT '회원 아이디',
                PAL_NO int NOT NULL COMMENT '팰번호',
                DISCOVERY_TIME date COMMENT '발견시간',
                CATCH_COUNT int COMMENT '잡은횟수',
                FOREIGN KEY (USER_ID) REFERENCES TBL_USER(USER_ID),
                FOREIGN KEY (PAL_NO) REFERENCES TBL_PAL(PAL_NO)
                )COMMENT = '팰 도감 정보';
                
select * from TBL_USER;
select * from TBL_PAL;
select * from TBL_PAL_GRADE;
select * from TBL_CATCH_PAL;
select * from TBL_PALDEX_BOOK;


-- DROP TABLE IF EXISTS TBL_USER;
-- DROP TABLE IF EXISTS TBL_PAL;
-- DROP TABLE IF EXISTS TBL_PAL_GRADE;
-- DROP TABLE IF EXISTS TBL_CATCH_PAL;
-- DROP TABLE IF EXISTS TBL_PALDEX_BOOK;


INSERT INTO TBL_PAL_GRADE (PAL_GRADE, CHANCE) 
VALUES 
('일반', 70), 
('희귀', 50), 
('전설', 20);
insert into tbl_pal (PAL_GRADE, PAL_NAME, PAL_TYPE)
values
('일반', '치커리타', '풀'),
('일반', '펭키', '물'),
('일반', '불꽃숭이', '불'),
('일반', '모달피', '풀'),
('일반', '물짱이', '물'),
('일반', '앗차!모', '불'),
('일반', '이상해싹', '풀'),
('일반', '미룡', '물'),
('일반', '불켜마', '불'),
('일반', '초롱이', '풀'),
('일반', '마릴링', '물'),
('일반', '파이리', '불'),
('희귀', '리자몽', '불'),
('희귀', '홍토조', '불'),
('희귀', '리프라스', '물'),
('희귀', '흰수염고래', '물'),
('희귀', '그린모스', '풀'),
('희귀', '쉐이미', '풀'),
('전설', '아르세우스', '빛');

