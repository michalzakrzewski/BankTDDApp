package com.zakrzewski.repositories;

import com.zakrzewski.models.ClientModel;

import java.sql.*;

public class JDBCClientRepository implements ClientRepository {

    public static final String USER = "postgres";
    public static final String PASSWORD = "kanapka";
    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/test_db";
    @Override
    public void addClient(ClientModel client) {
        try(Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String firstName = client.getName();
            String emailAddress = client.getEmailAddress();
            //Dlaczego nie VALUES('"+firstName"', '"+emailAddress+"')? Dlatego, że jak ktoś wpisze email jakiś i dopisze do niego DROP TABLE xxx to poleci baza.
            //Dlatego zawsze jest jakaś funkcja, która umożliwia wstawienie naszych stringow.
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(first_name, mail) VALUES (?,?)");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, emailAddress);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClientModel findClientByEmail(String email) {
        try(Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            //Dlaczego nie VALUES('"+firstName"', '"+emailAddress+"')? Dlatego, że jak ktoś wpisze email jakiś i dopisze do niego DROP TABLE xxx to poleci baza.
            //Dlatego zawsze jest jakaś funkcja, która umożliwia wstawienie naszych stringow.
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT first_name, mail FROM USERS WHERE mail=?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String first_name = resultSet.getString("first_name");
                String mail = resultSet.getString("mail");
                return new ClientModel(first_name, mail, 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
