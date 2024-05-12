create or replace procedure delete_student(
    v_student_id number
)
is
begin
   delete from Enrollment where student_id = v_student_id;
    delete from Students where student_id = v_student_id;
    commit;
end;

show errors;
