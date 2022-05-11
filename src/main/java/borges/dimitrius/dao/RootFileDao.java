package borges.dimitrius.dao;

import borges.dimitrius.model.Entity;
import borges.dimitrius.model.RootFile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootFileDao extends Dao{

    private enum RootFileCols {
        ID("id"),
        NAMETYPE("name_type"),
        BRAND("brand");

        private final String colName;

        RootFileCols(String colName){
            this.colName = colName;
        }

        @Override
        public String toString(){
            return this.colName;
        }
    }


    public RootFileDao (Connection connection){
        super(connection, "rootfile");
    }

    @Override
    protected List<RootFile> loadFromResultSet(ResultSet resultSet) throws SQLException{

        List<RootFile> rootFileList = new ArrayList<>();

        while(resultSet.next()){

            rootFileList.add(new RootFile(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));

        }
        return rootFileList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RootFile findById(Long id) throws SQLException {

        return (RootFile) this.fetchById(id);
    }

    @Override
    public List<RootFile> findAll() throws SQLException {

        return loadFromResultSet(super.fetchAll());
    }

    @Override
    public Map<String, Object> buildValMapping(Entity entity) {
        RootFile rootFile = (RootFile) entity;

        Map<String, Object> valMapping = new HashMap<>();

        valMapping.put(RootFileCols.NAMETYPE.colName, rootFile.getNameType());
        valMapping.put(RootFileCols.BRAND.colName, rootFile.getBrand());


    return valMapping;
    }

}
