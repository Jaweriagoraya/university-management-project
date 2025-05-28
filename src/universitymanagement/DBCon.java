package universitymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class  DBCon{
    private static final String URL = "jdbc:mysql://localhost:3306/college_db"; // Update your database name
    private static final String USER = "root"; // Update username
    private static final String PASSWORD = "JAW_0909_"; // Update password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return getConnection();
        }
    }
}

