-- Create a package specification
CREATE OR REPLACE PACKAGE gpa_update_pkg IS
  TYPE student_id_list IS TABLE OF Students.student_id%TYPE INDEX BY PLS_INTEGER;
  students_to_update student_id_list;
  PROCEDURE add_student_to_update(student_id IN Students.student_id%TYPE);
  PROCEDURE process_updated_students;
  function calc_GPA(v_student_id number)
return number;
END gpa_update_pkg;
show errors;
/

-- Create a package body
CREATE OR REPLACE PACKAGE BODY gpa_update_pkg IS
  PROCEDURE add_student_to_update(student_id IN Students.student_id%TYPE) IS
  BEGIN
    students_to_update(student_id) := 1;
  END add_student_to_update;

  PROCEDURE process_updated_students IS
  BEGIN
    FORALL i IN students_to_update.FIRST..students_to_update.LAST
      UPDATE Students
      SET GPA = calc_GPA(students_to_update(i))
      WHERE student_id = students_to_update(i);

    students_to_update.DELETE;
  END process_updated_students;
  
  function calc_GPA(v_student_id number)
return number
is 

 cursor student_grade_cursor 
is
select c.course_id , e.grade , c.credit_hours
from enrollment e , Courses c
where e.course_id = c.course_id  and e.student_id = v_student_id;

total_credit_hours number := 0.0;
total_grade_points number := 0.0;

begin 
for rec in student_grade_cursor loop
if rec.grade = 'A+' then total_grade_points := total_grade_points +(4.0* rec.credit_hours);
elsif rec.grade = 'A' then total_grade_points := total_grade_points +(4.0* rec.credit_hours);
elsif rec.grade = 'A-' then total_grade_points := total_grade_points +(3.7* rec.credit_hours);
elsif rec.grade = 'B+' then total_grade_points := total_grade_points +(3.3* rec.credit_hours);
elsif rec.grade = 'B' then total_grade_points := total_grade_points +(3* rec.credit_hours);
elsif rec.grade = 'B-' then total_grade_points := total_grade_points +(2.7* rec.credit_hours);
elsif rec.grade = 'C+' then total_grade_points := total_grade_points +(2.3* rec.credit_hours);
elsif rec.grade = 'C' then total_grade_points := total_grade_points +(2* rec.credit_hours);
elsif rec.grade = 'C-' then total_grade_points := total_grade_points +(1.7* rec.credit_hours);
elsif rec.grade = 'D' then total_grade_points := total_grade_points +(1* rec.credit_hours);
else  total_grade_points := total_grade_points +(0* rec.credit_hours);
end if;
total_credit_hours := total_credit_hours + rec.credit_hours;
end loop;
 if total_credit_hours = 0 then
    return null;
  else
    return total_grade_points / total_credit_hours;
  end if;
end;
END gpa_update_pkg;
/

-- Create a compound trigger
CREATE OR REPLACE TRIGGER update_GPA
FOR INSERT OR UPDATE ON enrollment
COMPOUND TRIGGER

AFTER STATEMENT IS
BEGIN
  gpa_update_pkg.process_updated_students;
END AFTER STATEMENT;

BEFORE EACH ROW IS
BEGIN
  IF INSERTING OR UPDATING THEN
    gpa_update_pkg.add_student_to_update(:NEW.student_id);
  END IF;
END BEFORE EACH ROW;

END update_GPA;
/
