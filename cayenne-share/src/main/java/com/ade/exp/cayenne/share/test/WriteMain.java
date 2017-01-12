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
public class WriteMain {

    public static void main(String[] args) {
        ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-share.xml");
        ObjectContext context = cayenneRuntime.getContext();

        while (true) {
            User user = Cayenne.objectForPK(context, User.class, 200);
            user.setUserAddress("" + System.currentTimeMillis());
            try {
                context.commitChanges();
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

    }

}
