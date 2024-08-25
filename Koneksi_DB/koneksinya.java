package Koneksi_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class koneksinya {
    private static Connection koneksi;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/apotek";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection GetConnection() throws SQLException {  
        if (koneksi == null || koneksi.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                koneksi = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                System.out.println("Koneksi ke database berhasil!");
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Koneksi gagal: " + e.getMessage());
                throw new SQLException("Koneksi ke database gagal!");
            }
        }
        return koneksi; 
    }
}
