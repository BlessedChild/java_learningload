public class ConnectToDatabase
{
    public static void main(String[] args)
    {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/?useCursorFetch=true", "user", "s3cr3t");
        stmt = conn.createStatement();
        stmt.setFetchSize(100);
        rs = stmt.executeQuery("SELECT * FROM your_table_here");
    }
}
