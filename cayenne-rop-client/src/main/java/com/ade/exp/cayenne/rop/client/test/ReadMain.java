package com.ade.exp.cayenne.rop.client.test;

import com.ade.exp.cayenne.rop.client.persistent.User;
import org.apache.cayenne.CayenneContext;
import org.apache.cayenne.DataChannel;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.event.DefaultEventManager;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.remote.ClientChannel;
import org.apache.cayenne.remote.ClientConnection;
import org.apache.cayenne.remote.hessian.HessianConnection;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by liyang on 2017/1/3.
 */
public class ReadMain {


    public static void main(String[] args) {
        ClientConnection connection = new HessianConnection("http://127.0.0.1:8080/cayenne-service");
        DataChannel channel = new ClientChannel(connection, false, new DefaultEventManager(), false);
        ObjectContext context = new CayenneContext(channel);


        while (true) {
            Expression expression = ExpressionFactory.matchExp(User.USER_NAME_PROPERTY, "tom1");
            SelectQuery selectQuery = new SelectQuery(User.class, expression);
            List list = context.performQuery(selectQuery);
            for (Object obj : list) {
                User user = (User) obj;
                System.out.println("name " + user.getUserName());
                System.out.println("address " + user.getUserAddress());
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
