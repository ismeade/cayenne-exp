package com.ade.exp.cayenne.base.query;

import com.ade.exp.cayenne.base.persistent.User;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.EJBQLQuery;
import org.apache.cayenne.query.SQLTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Cayenne也支持Sql查询
 * 在大多数情况下SelectQuery、EJBQLQuery是首选,因为他们基于api,并再编译时为您提供更好的检查。
 * Created by LiYang on 2016/7/14.
 */
public class SqlQuery {

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
    ObjectContext context = cayenneRuntime.getContext();

    /**
     * JPA查询也可以直接执行delete、update语句
     */
    public void ejbqLQuery() {
        EJBQLQuery query = new EJBQLQuery("select a FROM test_db.User a");
        List list = null;
        try {
            list = context.performQuery(query);
        } catch (Exception e) {
            logger.error("select error:" + e.getLocalizedMessage(), e);
        }
        if (null != list) {
            for (Object obj : list) {
                logger.info("User:" + obj);
            }
        }
    }

    /**
     * 和basicQuery的方法类似，可以直接返回对象List
     */
    public void sqlTemplate1() {
        SQLTemplate select = new SQLTemplate(User.class, "select * from test_db.User");
        List list = null;
        try {
            list = context.performQuery(select);
        } catch (Exception e) {
            logger.error("select error:" + e.getLocalizedMessage(), e);
        }
        if (null != list) {
            for (Object obj : list) {
                logger.info("User:" + obj);
            }
        }
    }

    /**
     * 也可以返回List<DataRow></>
     */
    public void sqlTemplate2() {
        SQLTemplate select = new SQLTemplate(User.class, "select * from test_db.User");
        select.setFetchingDataRows(true);
        List list = null;
        try {
            list = context.performQuery(select);
        } catch (Exception e) {
            logger.error("select error:" + e.getLocalizedMessage(), e);
        }
        if (null != list) {
            for (Object obj : list) {
                logger.info("User:" + obj);
            }
        }
    }

    public static void main(String[] args) {
        new SqlQuery().ejbqLQuery();
    }

}
