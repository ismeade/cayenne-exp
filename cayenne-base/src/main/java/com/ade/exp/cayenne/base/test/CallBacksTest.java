package com.ade.exp.cayenne.base.test;

import com.ade.exp.cayenne.base.persistent.Company;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;

/**
 *
 * Created by liyang on 2016/8/30.
 */
public class CallBacksTest {

    public static void main(String[] args) {
        ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
        ObjectContext context = cayenneRuntime.getContext();
        System.out.println(1);
        Company company = context.newObject(Company.class);
        System.out.println(2);
        company.setName("test0830");
        System.out.println(3);
        context.commitChanges();
        System.out.println(4);
    }

}
