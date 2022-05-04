package borges.dimitrius;

import borges.dimitrius.dao.PatientDao;
import borges.dimitrius.factory.DbConnectionFactory;
import borges.dimitrius.model.Patient;

import java.sql.*;

public class FrameworklessApi {

    public static void main(String[] args) {

        try {
            Connection connection = DbConnectionFactory.getConnection();

            PatientDao pacientDao = new PatientDao(connection);

            //pacientDao.updateById(new Patient(Date.valueOf("2000-02-01"), "Fulano"), 5L);

            pacientDao.findAll().forEach(System.out::println);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
