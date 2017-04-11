package com.ade.exp.cayenne.base.test;

import com.ade.exp.cayenne.base.persistent.User;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;

/**
 *
 * Created by liyang on 2017/4/7.
 */
public class LockDataTest {


    public static void main(String[] args) {
        ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
        ObjectContext context1 = cayenneRuntime.getContext();
        ObjectContext context2 = cayenneRuntime.getContext();

        User user1 = Cayenne.objectForPK(context1, User.class, 200);
        user1.setEmail("test1111111");
        System.out.println("user " + user1);


        User user2 = Cayenne.objectForPK(context2, User.class, 200);
        user2.setEmail("test22222222");
        context2.commitChanges();

        User user3 = Cayenne.objectForPK(context1, User.class, 200);
        System.out.println("user " + user3);

    }

}
