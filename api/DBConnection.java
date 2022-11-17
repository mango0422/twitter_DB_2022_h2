package api;

import java.sql.*;

public class DBConnection {
    protected Connection con = null; // object for connection
    protected Statement stmt = null; // object for query
    protected PreparedStatement pstmt = null;
    protected ResultSet rs = null; // object of dealing with result of query

    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/twitter","root","0512");
            System.out.println(con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
