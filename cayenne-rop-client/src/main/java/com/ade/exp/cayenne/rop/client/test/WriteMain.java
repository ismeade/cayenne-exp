package com.ade.exp.cayenne.rop.client.test;

import com.ade.exp.cayenne.rop.client.persistent.User;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.CayenneContext;
import org.apache.cayenne.DataChannel;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.event.DefaultEventManager;
import org.apache.cayenne.remote.ClientChannel;
import org.apache.cayenne.remote.ClientConnection;
import org.apache.cayenne.remote.hessian.HessianConnection;

/**
 *
 * Created by liyang on 2017/1/3.
 */
public class WriteMain {

    public static void main(String[] args) {
        ClientConnection connection = new HessianConnection("http://127.0.0.1:8080/cayenne-service");
        DataChannel channel = new ClientChannel(connection, false, new DefaultEventManager(), false);
        ObjectContext context = new CayenneContext(channel);

        User user = Cayenne.objectForPK(context, User.class, 220);
        System.out.println(user.getUserName());
        System.out.println(user.getUserAddress());
        user.setUserAddress("testAddress");
        System.out.println(user.getUserAddress());
        try {
            context.commitChanges();
            System.out.println(user.getUserAddress());

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
//        System.out.println(Cayenne.objectForPK(context, Company.class, 200));
    }

}
