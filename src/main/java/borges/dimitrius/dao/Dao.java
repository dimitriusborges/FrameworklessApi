package borges.dimitrius.dao;


import borges.dimitrius.model.entities.Entity;

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

        return sbQuery.toString();
    }

    private String prepareUpdateQuery(Set<String> cols, int nValues, String conditions){
        StringBuilder sbQuery = new StringBuilder();
        Formatter fmt = new Formatter(sbQuery);

        fmt.format("UPDATE %s SET ", this.tableName);

        Iterator<String> iterator = cols.iterator();

        while(iterator.hasNext()){
            String element = iterator.next();
            if(iterator.hasNext()) {
                sbQuery.append(String.format("%s = ?,", element));
            }
            else{
                sbQuery.append(String.format("%s = ?", element));
            }
        }

        sbQuery.append(conditions);

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
                default -> throw new SQLException("Type: " + valType + " Not implemented yet");
            }
        }


        return stmt;
    }

    protected void insert(Map<String, Object> valMap) throws SQLException{

        String query  = this.prepareInsertQuery(String.join(",", valMap.keySet()), valMap.size());
        fillQueryStmt(query, valMap).executeUpdate();

    }

    public void insert(Entity entity) throws SQLException{
        this.insert(this.buildValMapping(entity));
    }

    protected void update(Map<String, Object> valMap, String conditions) throws SQLException{

        String query = this.prepareUpdateQuery(valMap.keySet(), valMap.size(), conditions);
        fillQueryStmt(query, valMap).executeUpdate();
    }

    public void updateById(Entity entity) throws SQLException{
        this.update(this.buildValMapping( entity), " WHERE id = '" + entity.getId() + "'");
    }

    protected void delete(String conditions) throws SQLException {
        Statement stmt = dbConn.createStatement();

        stmt.execute("DELETE FROM " + this.tableName + conditions);
    }

    public void deleteById(Long id) throws SQLException {
        this.delete(" WHERE id = " + id);
    }

    protected Entity fetchById(Long id) throws SQLException{
        Statement stmt = dbConn.createStatement();

        stmt.execute("SELECT * FROM " + this.tableName + " WHERE id = " + id);

        @SuppressWarnings("unchecked")
        List<Entity> entities = (List<Entity>) loadFromResultSet(stmt.getResultSet());

        if(entities.size() > 1){
            throw new SQLException("Unexpected number of registers on result while reading by id from"
                    + this.tableName
                    +  " table");
        }

        else if(entities.isEmpty()){
            return null;
        }

        return entities.get(0);

    }

    protected ResultSet fetchAll() throws SQLException {
        Statement stmt = dbConn.createStatement();

        stmt.execute("SELECT * FROM " + this.tableName);

        return stmt.getResultSet();
    }

    protected abstract List<? extends Entity> loadFromResultSet(ResultSet resultSet) throws SQLException;

    public abstract List<? extends Entity> findAll() throws SQLException;

    public abstract <E extends Entity> E findById(Long id) throws SQLException;

    public abstract Map<String, Object> buildValMapping(Entity entity);
}
