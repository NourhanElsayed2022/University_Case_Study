create or replace procedure add_enrollments( v_student_id number ,
v_course_id number ,
v_grade varchar2 ,
v_en_date date)
is
begin
insert into enrollment (student_id , course_id , grade , enrollment_date)
values(v_student_id ,v_course_id , v_grade ,v_en_date  );
end;
show errors;

DECLARE
   v_student_id NUMBER := 1; 
   v_course_id NUMBER := 101; 
   v_grade VARCHAR2(3) := 'A'; 
   v_en_date DATE := TO_DATE('2022-01-20', 'YYYY-MM-DD'); 
BEGIN
   add_enrollments(v_student_id, v_course_id, v_grade, v_en_date);
   COMMIT;
   DBMS_OUTPUT.PUT_LINE('Enrollment added successfully.');
EXCEPTION
   WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
      ROLLBACK;
END;
/
