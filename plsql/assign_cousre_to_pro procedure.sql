create or replace procedure assign_cousre_to_pro(v_pro_id number , v_course_id number)
is 
begin
insert into professor_courses (pro_id , course_id)
values(v_pro_id ,v_course_id );
commit;
end;
show errors;