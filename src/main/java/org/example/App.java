package org.example;

import org.example.Data.Lot;
import org.example.Data.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    private static final int NUM_USERS = 8;
    private static SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();
    private static Session session = null;

    public static void main(String[] args) {
        PrepareData.forcePrepareDada();
        PrepareData.forcePrepareDada();
        List<User> users = getAllUsers();
        List<Lot> lots = getAllLots();

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < NUM_USERS; i++) {
            User user = users.get(i % users.size());
            Thread thread = new GameThread(factory, lots, user);
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int expectedTotalBet = NUM_USERS * GameThread.NUM_LOOPS * GameThread.BET_AMOUNT;
        int actualTotalBet = lots.stream().mapToInt(Lot::getCurrentBet).sum();
        System.out.println("Expected total bet: " + expectedTotalBet);
        System.out.println("Actual total bet: " + actualTotalBet);

        factory.close();
    }

    private static List<Lot> getAllLots() {
        List<Lot> lots = new ArrayList<>();
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            lots = session.createQuery("FROM Lot l")
                    .getResultList();
            session.getTransaction().commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lots;
    }

    private static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            users = session.createQuery("FROM User u")
                    .getResultList();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return users;
    }
}
