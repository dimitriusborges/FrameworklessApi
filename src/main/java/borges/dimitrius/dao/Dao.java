package borges.dimitrius.dao;


import borges.dimitrius.model.Entity;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public abstract class Dao {

    private final Connection dbConn;
    private final String tableName;

    public Dao(Connection connection, String tableName){
        this.dbConn = connection;
        this.tableName = tableName;
    }

    private String prepareInsertQuery(String cols, int nValues){

        StringBuilder sbQuery = new StringBuilder();
        Formatter fmt = new Formatter(sbQuery);

        fmt.format("INSERT INTO %s (%s) VALUES(%s", this.tableName, String.join(",", cols), "?,".repeat(nValues));

        sbQuery.replace(sbQuery.length() -1, sbQuery.length(), ")");
        //System.out.println(sbQuery);
        return sbQuery.toString();
    }

    private String prepareUpdateQuery(Set<String> cols, int nValues, String cond){
        StringBuilder sbQuery = new StringBuilder();
        Formatter fmt = new Formatter(sbQuery);

        fmt.format("UPDATE %s SET ", this.tableName);

        for(String col: cols){
            sbQuery.append(String.format("%s = ?,", col));
        }

        sbQuery.delete(sbQuery.length() -1 , sbQuery.length());

        sbQuery.append(cond);

        return sbQuery.toString();
    }

    private PreparedStatement fillQueryStmt(String query, Map<String, Object> valMap) throws SQLException {

        PreparedStatement stmt = this.dbConn.prepareStatement(query);

        int idx = 0;
        for(Object val: valMap.values()){
            String valType = val.getClass().getName();

            switch (valType) {
                case "java.lang.String" -> stmt.setString(++idx, (String) val);
                case "java.lang.Double" -> stmt.setDouble(++idx, (Double) val);
                case "java.lang.Integer"-> stmt.setInt(++idx, (Integer) val);
                case "java.lang.Long" -> stmt.setLong(++idx, (Long) val);
                case "java.sql.Date" -> stmt.setDate(++idx, (Date) val);
                default -> System.out.println("Type: " + valType + " Not treated yet");
            }
        }


        return stmt;
    }

    protected void insert(Map<String, Object> valMap) throws SQLException{

        String query  = this.prepareInsertQuery(String.join(",", valMap.keySet()), valMap.size());
        fillQueryStmt(query, valMap).executeUpdate();

    }

    protected void update(Map<String, Object> valMap, String cond) throws SQLException{

        String query = this.prepareUpdateQuery(valMap.keySet(), valMap.size(), cond);
        fillQueryStmt(query, valMap).executeUpdate();
    }

    protected ResultSet fetchAll() throws SQLException {
        Statement stmt = dbConn.createStatement();

        boolean hasRes = stmt.execute("SELECT * FROM " + this.tableName);

        return stmt.getResultSet();
    }

    public abstract <E extends Entity> void insert(E entity) throws SQLException;

    public abstract <E extends Entity> void updateById(E entity, Long id) throws SQLException;

    public abstract List<? extends Entity> findAll() throws SQLException;

    public abstract <E extends Entity> Map<String, Object> buildValMapping(E entity);
}
