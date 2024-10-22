import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    public static void main(String[] args) {
        // Database URL, Username, and Password
        String url = "jdbc:mysql://127.0.0.1:3306/";  // Change the URL if needed
        String user = "root";
        String password = "Hout@8850";  // Replace with the actual password

        try {
            // Establish the connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully!");

            // Close the connection
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}
