package org.example;

import org.example.Data.Lot;
import org.example.Data.User;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;

import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Random;

public class GameThread extends Thread {
    public static final int NUM_LOOPS = 1000;
    public static final int BET_AMOUNT = 100;
    private final SessionFactory factory;
    private final List<Lot> lots;
    private final User user;

    public GameThread(SessionFactory factory, List<Lot> lots, User user) {
        this.factory = factory;
        this.lots = lots;
        this.user = user;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < NUM_LOOPS; i++) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                Lot lot = lots.get(random.nextInt(lots.size()));

                updateLotWithPessimisticLock(session, lot, user);

                session.getTransaction().commit();
            } catch (StaleObjectStateException | OptimisticLockException e) {
                System.err.println("Conflict detected: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void updateLotWithSynchronization(Session session, Lot lot, User user) {
        lot.setCurrentBet(lot.getCurrentBet() + BET_AMOUNT);
        lot.setCurrentOwner(user);
        session.update(lot);
    }

    private void updateLotWithPessimisticLock(Session session, Lot lot, User user) {
        session.lock(lot, LockMode.PESSIMISTIC_WRITE);
        lot.setCurrentBet(lot.getCurrentBet() + BET_AMOUNT);
        lot.setCurrentOwner(user);
        session.update(lot);
    }

    private void updateLotWithOptimisticLock(Session session, Lot lot, User user) {
        lot.setCurrentBet(lot.getCurrentBet() + BET_AMOUNT);
        lot.setCurrentOwner(user);
        session.update(lot);
    }
}
