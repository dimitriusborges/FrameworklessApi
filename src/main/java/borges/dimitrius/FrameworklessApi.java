package borges.dimitrius;

import borges.dimitrius.factory.DbConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FrameworklessApi {

    public static void main(String[] args) {

        try {
            Connection connection = DbConnectionFactory.getConnection();

            Statement statement = connection.createStatement();

            boolean res = statement.execute("SELECT * FROM pacient");

            if(res){
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()){
                    System.out.println("Client ID: " + resultSet.getInt(1));
                    System.out.println("Client name: " + resultSet.getString(3));

                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
