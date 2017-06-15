package com.ade.exp.cayenne.base.test;

import com.ade.exp.cayenne.base.persistent.Company;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;

/**
 * Company 增加了一个 onPostAdd 回调事件，当运行玩newObject时(1 2 之间)时 将会调用 onPostAdd 方法
 * 4.0以后可是使用Listener类+标签的方式如：
 * @PostRemove({ MyEntity1.class, MyEntity2.class })
 *   void postRemove(Persistent object) {
 *       // do something
 *   }
 *
 *   增加监听类
 *   runtime.getDataDomain().addListener(new Listener1());
 * Created by liyang on 2016/8/30.
 */
public class CallBacksTest {

    public static void main(String[] args) {
        ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
        ObjectContext context = cayenneRuntime.getContext();
        System.out.println(1);
        Company company = context.newObject(Company.class);
        System.out.println(2);
        company.setName("test0615-2");
        System.out.println(3);
        context.commitChanges();
        System.out.println(4);
    }

}
