create or replace procedure add_new_course(
    v_name varchar2,
    v_description varchar2,
    v_code varchar2,
    v_credit_hours number,
    v_semester varchar2
)
is
begin
   insert into Courses(course_id, name, description, code, credit_hours, semester)
    values (seq_courses.nextval,v_name, v_description, v_code, v_credit_hours, v_semester);
    commit;
end;
show errors;
