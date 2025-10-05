/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author acer
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final static String DB_HOST = "localhost";
    private final static String DB_NAME = "bank_sampah";
    private final static String DB_USERNAME = "root";
    private final static String DB_PASSWORD = "";

    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load driver: " + e.getMessage());
        }
    }
    
    public static Connection connect() throws SQLException{
        String url = "jdbc:mysql://" + DB_HOST + "/" + DB_NAME;
        return DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
    }
    
}
