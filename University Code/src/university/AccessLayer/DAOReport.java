/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.AccessLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import university.models.DTOReport;
import university.models.DTOStudent;

/**
 *
 * @author Software
 */
public class DAOReport {
    
     public List<DTOReport> getReportData(String course_name) throws SQLException {
    List<DTOReport> reportDataList = new ArrayList<>();

    try (PreparedStatement statement = DAO.getConnection().prepareCall(
            "SELECT\n" +
                    "    c.code, c.name ,  s.f_name ||' ' ||s.l_name  , e.grade  ,CALCULATE_AVG_GPA(c.course_id) \n" +
                    "FROM Courses c\n" +
                    "JOIN  enrollment e ON c.course_id = e.course_id\n" +
                    "JOIN Students s ON e.student_id = s.student_id\n" +
                    "WHERE  c.name = ?")) {
        statement.setString(1, course_name);
        System.out.println("SQL Query: " + "SELECT\n" +
                "    c.code , c.name ,  s.f_name ||' ' ||s.l_name  , e.grade  ,CALCULATE_AVG_GPA(c.course_id) \n" +
                "FROM Courses c\n" +
                "JOIN  enrollment e ON c.course_id = e.course_id\n" +
                "JOIN Students s ON e.student_id = s.student_id\n" +
                "WHERE  c.name = ?");

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                DTOReport dtoReport = new DTOReport(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)
                );

                reportDataList.add(dtoReport);
            }
        }
    }

    return reportDataList;
}

    
    
}
