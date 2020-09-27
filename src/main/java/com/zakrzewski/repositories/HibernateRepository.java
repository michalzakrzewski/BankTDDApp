package com.zakrzewski.repositories;

import com.zakrzewski.models.ClientModel;
import org.hibernate.Session;

public class HibernateRepository implements ClientRepository {
    @Override
    public void addClient(ClientModel client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(client);
        session.getTransaction().commit();

    }

    @Override
    public ClientModel findClientByEmail(String email) {
        return null;
    }
}
