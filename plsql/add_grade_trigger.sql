CREATE OR REPLACE TRIGGER UNIVERSITY.add_grade_trigger
BEFORE INSERT OR UPDATE OF grade ON UNIVERSITY.ENROLLMENT FOR EACH ROW
BEGIN
    IF :new.grade IN ('A+', 'A', 'A-', 'B+', 'B', 'B-', 'C+', 'C', 'C-', 'D', 'F') THEN
        :new.grade := UPPER(:new.grade);  
    ELSE
        raise_application_error(-20122, 'Please enter a valid value for the grade!');
    END IF;
END;
/