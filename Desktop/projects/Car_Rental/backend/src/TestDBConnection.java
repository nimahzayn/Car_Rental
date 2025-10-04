import java.sql.Connection;
import java.sql.DriverManager;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/car_rental";
        String user = "root"; // your MySQL username
        String password = "2005"; // your MySQL password

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to database successfully!");
            conn.close();
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
