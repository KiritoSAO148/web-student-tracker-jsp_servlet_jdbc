package com.example.web.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil {
    private DataSource dataSource;

    public StudentDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Student> getStudents() throws Exception {
        List<Student> students = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "select * from student";
            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()){
                int id = myRs.getInt("id");
                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                String email = myRs.getString("email");
                Student tempStudent = new Student(id, firstName, lastName, email);
                students.add(tempStudent);
            }

            return students;
        }
        finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void close(Connection myConn, Statement myStmt, ResultSet myRs){
        try {
            if (myRs != null){
                myRs.close();
            }
            if (myStmt != null){
                myStmt.close();
            }
            if (myConn != null){
                myConn.close(); //doesn't really close it ... just puts back in connection pool
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addStudent(Student theStudent) throws Exception{
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "insert into student "
                    + "(first_name, last_name, email) "
                    + " values (?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, theStudent.getFirstName());
            myStmt.setString(2, theStudent.getLastName());
            myStmt.setString(3, theStudent.getEmail());

            myStmt.execute();
        }
        finally {
            close(myConn, myStmt, null);
        }
    }
}
