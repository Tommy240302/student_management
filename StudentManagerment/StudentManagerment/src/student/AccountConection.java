/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountConection {
    private static final String URL = "jdbc:mysql://localhost:3306/students_management";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static Connection con = null;

    public static Connection getConnection()  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return con;
    }
}

