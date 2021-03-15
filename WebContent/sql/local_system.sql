create user jspStudy identified by 1234
default tablespace users
temporary tablespace temp;

-- 사용자 권한 부여
grant connect, resource, dba to jspStudy;