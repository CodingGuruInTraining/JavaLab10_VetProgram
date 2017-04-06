import java.sql.*;

public class VetDB {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/vet";     //Connection string – where's the database?
    static final String USER = "";   //TODO replace with your username
    static final String PASSWORD = "";   //TODO remember to set the environment variable
    // static final String PASSWORD = "password";   // If on lab PC, uncomment this line and replace "password" with your own password


    public static void main(String[] args) {

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drivers and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }

        //Try with resources to open the connection and create a statement. Make sure your language level is 1.7+
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {
            //You should have already created a database via terminal/command prompt OR MySQL Workbench

            //Create a table in the database, if it does not exist already
            String createTableSQL = "CREATE TABLE IF NOT EXISTS dogs (Name varchar(30), Age int)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Created Dogs table");

            //Add some data
            String addDataSQL = "INSERT INTO dogs VALUES ('Poppy', 3)";
            statement.executeUpdate(addDataSQL);

            addDataSQL = "INSERT INTO dogs VALUES ('Zelda', 5)";
            statement.executeUpdate(addDataSQL);
            System.out.println("Added two rows of data");

            addDataSQL = "insert into dogs values ('Lassie', 12)";
            statement.executeUpdate(addDataSQL);

            String fetchAllDataSQL = "select * from dogs";
            ResultSet rs = statement.executeQuery(fetchAllDataSQL);

            while (rs.next()) {
                String dogName = rs.getString("Name");
                int dogAge = rs.getInt("Age");
                System.out.println("Dog name = " + dogName + " dog age = " + dogAge);
            }
            rs.close();

            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

        System.out.println("End of program");
    }
}