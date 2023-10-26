package com.busygind.lab3.database;

import com.busygind.lab3.entities.Hit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class DatabaseController {

    private SessionFactory factory;

    public DatabaseController() {
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Hit.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Exception during session factory init: " + e.getMessage());
        }
    }

    public void uploadHitToDatabase(Hit hit) {
        Session session = factory.getCurrentSession();

        session.beginTransaction();
        session.save(hit);
        session.getTransaction().commit();
    }

    public void deleteHitsBySessionId(String sessionId) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from Hit where sessionId= :session_id")
                .setParameter("session_id", sessionId).executeUpdate();
        session.getTransaction().commit();
    }

}
