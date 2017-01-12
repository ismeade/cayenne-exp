package com.ade.exp.cayenne.share.test;

import com.ade.exp.cayenne.share.persistent.User;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;

import java.util.concurrent.TimeUnit;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public class ReadMain {

    public static void main(String[] args) {
        ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-share.xml");
        ObjectContext context = cayenneRuntime.getContext();

        User user = Cayenne.objectForPK(context, User.class, 200);
        System.out.println(user);
        User localUser = context.localObject(user);
        System.out.println(localUser);
        localUser.setUserAddress("address");
        System.out.println(user);
        System.out.println(localUser);
//        context.commitChanges();

//        while (true) {
//            User user = Cayenne.objectForPK(context, User.class, 200);
//            System.out.println(user.getUserAddress());
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (Exception e) {
//                System.out.println(e.getLocalizedMessage());
//            }
//        }

    }

}
