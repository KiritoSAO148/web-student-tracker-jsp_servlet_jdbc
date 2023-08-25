package com.example.web.jdbc;

import jakarta.annotation.Resource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentControllerServlet", value = "/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
    private StudentDbUtil studentDbUtil;

    //    @Resource(name="jdbc/WEB_STUDENT_TRACKER")
    InitialContext ic = new InitialContext();
    private DataSource dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/WEB_STUDENT_TRACKER");

    public StudentControllerServlet() throws NamingException {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            studentDbUtil = new StudentDbUtil(dataSource);
        } catch (Exception e){
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String theCommand = request.getParameter("command");
            if (theCommand == null) theCommand = "LIST";
            switch (theCommand){
                case "LIST":
                    listStudents(request, response);
                    break;

                case "ADD":
                    addStudents(request, response);
                    break;

                case "LOAD":
                    loadStudents(request, response);
                    break;

                case "UPDATE":
                    updateStudents(request, response);
                    break;

                case "DELETE":
                    deleteStudent(request, response);
                    break;

                default:
                    listStudents(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String theStudentId = request.getParameter("studentId");
        studentDbUtil.deleteStudent(theStudentId);
        listStudents(request, response);
    }

    private void updateStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int id = Integer.parseInt(request.getParameter("studentId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        Student theStudent = new Student(id, firstName, lastName, email);

        studentDbUtil.updateStudent(theStudent);

        listStudents(request, response);
    }

    private void loadStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String studentId = request.getParameter("studentId");

        Student theStudent = studentDbUtil.getStudent(studentId);

        request.setAttribute("THE_STUDENT", theStudent);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
        dispatcher.forward(request, response);
    }

    private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        Student theStudent = new Student(firstName, lastName, email);

        studentDbUtil.addStudent(theStudent);

        listStudents(request, response);
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
    throws Exception{
        List<Student> students = studentDbUtil.getStudents();
        request.setAttribute("STUDENT_LIST", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
