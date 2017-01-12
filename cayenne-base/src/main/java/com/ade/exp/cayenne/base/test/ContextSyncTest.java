package com.ade.exp.cayenne.base.test;

import com.ade.exp.cayenne.base.persistent.Company;
import com.ade.exp.cayenne.base.persistent.User;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.DataChannel;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.ObjectIdQuery;

import java.util.List;

/**
 * 同一个父类的的所有子context，其中过一个调用commitChangesToParent方法，所有子类都
 * 变化，与父类平级的context不会变化，直到改层context继续向上提交变化
 *
 * 在提交时同步对象：
 * 经常有不止一种ObjectContext工作在同一个Cayenne栈之上。例如，在一个web应用程序中，
 * 每个用户会话可能有它自己的ObjectContext.如果有一个context提交它的变化，Cayenne都
 * 会在所有context中自动更新每个修改过的对象的的复件（"复件"的意思是相同对象ID的对象的
 * 特定context实例）。因此所有被提交对象的变化会在所有context中立即可见。如果有不止一
 * 个的Cayenne栈正在运行（例如，应用程序是建立在多个JVM上的集群），有很多方法去通知其它
 * 栈关于这个对象的改变。这可以在Modeler中设置。然而每一次改变的完全同步经常会造成昂贵的
 * 网络流量和CPU负荷，并且通常被避免繁忙的查询缓存的方法在本章其它部分被描述。
 *
 * 内在管理：
 * Cayenne确保被分配给缓存的内存不会无限制的增长。一个缓存被共享在ObjectContexts有一个
 * 固定的上限，10000是这个条目 的默认最大值，可以在Modeler中改变它。一个缓存附加到每个
 * ObjectContext（在本章的其他地方也被称为 "本地缓存 "），仅储存通过这个context访问的
 * 对象，并没有上限。然而它使用弱引用去缓存被提交的对象，所以当应用程序运行在内存很少的情
 * 况下时，它们从缓存中被自动清洗掉。
 *
 * 刷新：
 * 对象的缓存发生在幕后，唯一需要用户去操心的情况是当一个对象变动了而且需要去刷新时。
 *
 * Created by LiYang on 2016/7/13.
 */
public class ContextSyncTest {

    /**
     * 同一个ServerRuntime生成的所有ObjectContext，其中一个调用commitChanges方法，变
     * 化会同步到同一层其他ObjectContext
     */
    public void test1() {
        ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
        ObjectContext context1 = cayenneRuntime.getContext();
        ObjectContext context2 = cayenneRuntime.getContext();

        Company company1 = getCompany(200, context1);
        if (null != company1) {
            List<User> userList = company1.getUser();
            if (null != userList) {
                for (User user : userList) {
                    System.out.println("context1:" + user);
                }
            }
        }
        System.out.println("------------------------------------------");
        Company company2 = getCompany(200, context2);
        if (null != company2) {
            List<User> userList = company2.getUser();
            if (null != userList) {
                for (User user : userList) {
                    System.out.println("context2:" + user);
                }
            }
        }
        System.out.println("------------------------------------------");
        if (null != company1) {
            List<User> userList = company1.getUser();
            if (null != userList) {
                for (User user : userList) {
                    user.setAge(12);
                }
            }
            context1.commitChanges();
        }
        System.out.println("------------------------------------------");
        if (null != company2) {
            List<User> userList = company2.getUser();
            if (null != userList) {
                for (User user : userList) {
                    System.out.println("context2:" + user);
                }
            }
        }
    }

    /**
     * 父类调用commitChanges方法或commitChangesToParent时，只影响与他同层的context缓存中得变化。不会影响到他的子类
     */
    public void test2() {
        ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
        ObjectContext context1 = cayenneRuntime.getContext();
        ObjectContext context2 = cayenneRuntime.getContext((DataChannel) context1);

        Company company1 = getCompany(200, context1);
        if (null != company1) {
            List<User> userList = company1.getUser();
            if (null != userList) {
                for (User user : userList) {
                    System.out.println("context1:" + user);
                }
            }
        }
        System.out.println("------------------------------------------");
        Company company2 = getCompany(200, context2);
        if (null != company2) {
            List<User> userList = company2.getUser();
            if (null != userList) {
                for (User user : userList) {
                    System.out.println("context2:" + user);
                }
            }
        }
        System.out.println("------------------------------------------");
        if (null != company1) {
            List<User> userList = company1.getUser();
            if (null != userList) {
                for (User user : userList) {
                    user.setAge(13);
                }
            }
            context1.commitChanges();
        }
        System.out.println("------------------------------------------");
        if (null != company2) {
            List<User> userList = company2.getUser();
            if (null != userList) {
                for (User user : userList) {
                    System.out.println("context2:" + user);
                }
            }
        }
    }

    /**
     * 同一个父类的的所有子context，其中过一个调用commitChangesToParent方法，所有子类都
     * 变化，与父类平级的context不会变化，直到改层context继续向上提交变化
     * 类似test1
     */
    public void test3() {
        ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
        ObjectContext context = cayenneRuntime.getContext();
        ObjectContext context1 = cayenneRuntime.getContext((DataChannel) context);
        ObjectContext context2 = cayenneRuntime.getContext((DataChannel) context);

        Company company1 = getCompany(200, context1);
        if (null != company1) {
            List<User> userList = company1.getUser();
            if (null != userList) {
                for (User user : userList) {
                    System.out.println("context1:" + user);
                }
            }
        }
        System.out.println("------------------------------------------");
        Company company2 = getCompany(200, context2);
        if (null != company2) {
            List<User> userList = company2.getUser();
            if (null != userList) {
                for (User user : userList) {
                    System.out.println("context2:" + user);
                }
            }
        }
        System.out.println("------------------------------------------");
        if (null != company1) {
            List<User> userList = company1.getUser();
            if (null != userList) {
                for (User user : userList) {
                    user.setAge(14);
                }
            }
            context1.commitChangesToParent();
        }
        System.out.println("------------------------------------------");
        if (null != company2) {
            List<User> userList = company2.getUser();
            if (null != userList) {
                for (User user : userList) {
                    System.out.println("context2:" + user);
                }
            }
        }
    }

    private Company getCompany(Integer id, ObjectContext context) {
        try {
            ObjectId objectId = new ObjectId(Company.class.getSimpleName(), Company.ID_PK_COLUMN, id);
            ObjectIdQuery query = new ObjectIdQuery(objectId);
            return (Company) Cayenne.objectForQuery(context, query);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }


    public static void main(String[] args) {
        new ContextSyncTest().test2();
    }

}
