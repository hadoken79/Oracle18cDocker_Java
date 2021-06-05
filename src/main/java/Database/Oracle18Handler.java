package Database;

public class Oracle18cHandler {

    private OracleDataSource ods;

    public Oracle18cHandler(String connectionString, String user, String password) throws SQLException {
        ods = new OracleDataSource();
        ods.setURL(connectionString);
        ods.setUser(user);
        ods.setPassword(password);
    }

    public ResultSet getData(OracleConnection conn, String query, HashMap<Integer, String> arguments) throws SQLException {

        if(conn == null)throw new RuntimeException("No valid database connection supplied");

        try{

            if(arguments.isEmpty()){
                Statement stmt = conn.createStatement();
                return stmt.executeQuery(query);
            }

            PreparedStatement ps = conn.prepareStatement(query);

            arguments.entrySet().stream()
                    .filter(arg -> arg.getKey() != 0)
                    .forEach(arg -> {
                        try {
                            ps.setString(arg.getKey(), arg.getValue());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            throw new RuntimeException("could not map prepared statement arguments");
                        }
                    });
            return ps.executeQuery();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public OracleConnection getConnection() throws SQLException {
        return (OracleConnection) ods.getConnection();
    }


}

