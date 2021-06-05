package Database;

public class Main {


    public static void main(String[] args) throws SQLException {
        Oracle18cHandler dbHandler = new Oracle18cHandler("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system", "test1");


        try(OracleConnection conn = dbHandler.getConnection()) {
            HashMap<Integer, String> arguments = new HashMap<>();
            arguments.put(1, "TOM");

            ResultSet result = dbHandler.getData(conn,"SELECT * FROM lp_test WHERE NOT NAME=?", arguments);

            while (result.next())
                System.out.println(result.getString("id") + " "
                        + result.getString("name") + " ");

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}