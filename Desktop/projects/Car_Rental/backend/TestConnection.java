package backend;

public class TestConnection {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager(); // Connect
        db.closeConnection(); // Close connection
    }
}
