package com.ade.exp.cayenne.mult.query;

import com.ade.exp.cayenne.mult.persistent.db.User;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.Transaction;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事物
 * Created by LiYang on 2016/7/14.
 */
public class Transactions {

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");

    ObjectContext context1 = cayenneRuntime.getContext();
    ObjectContext context2 = cayenneRuntime.getContext();

    public void test1() {
//        cayenneRuntime.getDataDomain().setUsingExternalTransactions(true);
        Transaction tx = cayenneRuntime.getDataDomain().createTransaction();
        Transaction.bindThreadTransaction(tx);

        try {
            System.out.println("------------------------------");
            // commit one or more contexts
            User user1 = context1.newObject(User.class);
            user1.setName("user1001");
            user1.setAge(11);
            user1.setAdderss("user1001 address");
            context1.commitChanges();

            Thread.sleep(3000);
            System.out.println("------------------------------");
            User user2 = context2.newObject(User.class);
            user2.setName("user1002");
            user2.setAge(11);
            user2.setAdderss("user1002 address");
            context2.commitChanges();

            Thread.sleep(3000);
            System.out.println("------------------------------");
            // after changing some objects in context1, commit again
            user1.setAge(15);
            context1.commitChanges();

            Thread.sleep(3000);
            // if no failures, commit
            System.out.println("---------Transaction----------");
            tx.commit();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);
            tx.setRollbackOnly();
        } finally {
            Transaction.bindThreadTransaction(null);
            if (tx.getStatus() == Transaction.STATUS_MARKED_ROLLEDBACK) {
                try {
                    System.out.println("000");
                    tx.rollback();
                } catch (Exception rollbackEx) {
                    //
                }
            }
        }
    }

    public static void main(String[] args) {
        new Transactions().test1();
        
    }

}
