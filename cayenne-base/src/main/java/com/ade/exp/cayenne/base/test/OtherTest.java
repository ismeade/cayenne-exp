package com.ade.exp.cayenne.base.test;

import com.ade.exp.cayenne.base.persistent.User;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.map.EntityResolver;

/**
 *
 * Created by liyang on 2017/6/13.
 */
public class OtherTest {

    private ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");

    private void test1() {
        ObjectContext context1 = cayenneRuntime.getContext();
        User user1 = Cayenne.objectForPK(context1, User.class, 220);
        System.out.println(user1);

        ObjectContext context2 = cayenneRuntime.getContext();
        User user2 = context2.localObject(user1);
        user2.setName("user8888");
        context2.commitChanges();
    }

    public static void main(String[] args) {
        new OtherTest().test1();
    }

}
