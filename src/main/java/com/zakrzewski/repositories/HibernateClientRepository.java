package com.zakrzewski.repositories;

import com.zakrzewski.entity.Client;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class HibernateClientRepository implements ClientRepository {
    @Override
    public void saveClient(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        client
                .getAccounts()
                .forEach(session::save);
        session.save(client);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public Client findClientByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query<Client> query = session.createQuery("from Client where mail=:mail", Client.class);
        query.setParameter("mail", email);
        Client client = query.uniqueResult();
        session.close();
        return client;
    }
}
