package borges.dimitrius;

import borges.dimitrius.dao.PatientDao;
import borges.dimitrius.factory.DbConnectionFactory;
import borges.dimitrius.model.Patient;

import java.sql.*;

public class FrameworklessApi {

    public static void main(String[] args) {

        try {
            Connection connection = DbConnectionFactory.getConnection();


            //when I consider my Dao class good enough, I will build a set of tests instead of doing stuff on main.java
            PatientDao pacientDao = new PatientDao(connection);

            pacientDao.insert(new Patient(Date.valueOf("2022-01-01"), "Ciclano"));

           // pacientDao.findAll().forEach(System.out::println);

            //System.out.println("\n");
           // pacientDao.deleteById(6L);

           // pacientDao.findAll().forEach(System.out::println);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
