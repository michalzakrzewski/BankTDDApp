package com.zakrzewski.repositories;

import com.zakrzewski.annotations.JDBCRepository;
import com.zakrzewski.entity.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@JDBCRepository
public class JDBCClientRepository implements ClientRepository {

    public final String user;
    public final String password;
    public final String jdbcUrl;

    public JDBCClientRepository(@Value("${jdbc.user}") String user, @Value("${jdbc.password}") String password, @Value("${jdbc.url}") String jdbcUrl) {
        this.user = user;
        this.password = password;
        this.jdbcUrl = jdbcUrl;
    }

    @Override
    public void saveClient(Client client) {
        try(Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
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
    public Client findClientByEmail(String email) {
        try(Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
            //Dlaczego nie VALUES('"+firstName"', '"+emailAddress+"')? Dlatego, że jak ktoś wpisze email jakiś i dopisze do niego DROP TABLE xxx to poleci baza.
            //Dlatego zawsze jest jakaś funkcja, która umożliwia wstawienie naszych stringow.
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT first_name, mail FROM USERS WHERE mail=?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String first_name = resultSet.getString("first_name");
                String mail = resultSet.getString("mail");
                return new Client(first_name, mail, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
