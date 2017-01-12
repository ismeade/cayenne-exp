package com.ade.exp.cayenne.mult.test;

import com.ade.exp.cayenne.mult.persistent.db.Test;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.ObjectIdQuery;

/**
 *
 * Created by liyang on 2016/8/30.
 */
public class TableLockTest {

    public static void main(String[] args) {
        ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
        ObjectContext context = cayenneRuntime.getContext();

//        Test test = context.newObject(Test.class);
//        test.setInfo("Test3");
//        try {
//            context.commitChanges();
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//        }

        ObjectId id = new ObjectId(Test.class.getSimpleName(), Test.TEST_ID_PK_COLUMN, 280);
        ObjectIdQuery query = new ObjectIdQuery(id);
        try {
            Test test = (Test) Cayenne.objectForQuery(context, query);
            test.setInfo("test update 2");
            context.commitChanges();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

}
