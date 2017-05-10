package com.ade.exp.cayenne.base.query;

import java.util.List;

import com.ade.exp.cayenne.base.persistent.User;
import org.apache.cayenne.*;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.Query;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提供了使用Cayenne的几种查询方法
 * Created by LiYang on 2016/7/13.
 */
public class BasicQuery {

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
    ObjectContext context = cayenneRuntime.getContext();

    /**
     * 主键查询(优先从缓存中获取)
     * @param pk
     */
    public void getUserByPk1(Integer pk) {
        try {
            User user = Cayenne.objectForPK(context, User.class, pk);
            logger.info("User:" + user);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 主键查询(通过参数决定是否优先从缓存中获取)
     * @param pk 主键
     */
    public void getUser1(Integer pk) {
        try {
            ObjectId id = new ObjectId(User.class.getSimpleName(), User.ID_PK_COLUMN, pk);
            // 表示刷新缓存，从表中从新获取数据
            ObjectIdQuery query = new ObjectIdQuery(id, false, ObjectIdQuery.CACHE_REFRESH);
            User user = (User) Cayenne.objectForQuery(context, query);
            logger.info("User:" + user);
        } catch (Exception e) {
            logger.error("select error:" + e.getLocalizedMessage(), e);
        }
    }

    /**
     * 条件查询
     * @param name 条件
     */
    public void getUser2(String name) {
        Expression qualifier = ExpressionFactory.matchExp(User.NAME_PROPERTY, name);
        SelectQuery select = new SelectQuery(User.class, qualifier);
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
     * 多条件查询
     * @param name 条件1
     * @param age  条件2
     */
    public void getUser3(String name, Integer age) {
        Expression qualifier = ExpressionFactory.matchExp(User.NAME_PROPERTY, name);
        qualifier = qualifier.andExp(ExpressionFactory.matchExp(User.AGE_PROPERTY, age));
        SelectQuery select = new SelectQuery(User.class, qualifier);
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
     * 查询集合排序
     * @param name 条件
     */
    public void getUser4(String name) {
        Expression qualifier = ExpressionFactory.matchExp(User.NAME_PROPERTY, name);
        SelectQuery select = new SelectQuery(User.class, qualifier);
        // 顺序
        select.addOrdering(User.AGE_PROPERTY, SortOrder.ASCENDING);
        // 倒序
        select.addOrdering(User.ADDERSS_PROPERTY, SortOrder.DESCENDING);
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
     * 查询集合去重
     * @param name 条件
     */
    public void getUser5(String name) {
        Expression qualifier = ExpressionFactory.matchExp(User.NAME_PROPERTY, name);
        SelectQuery select = new SelectQuery(User.class, qualifier);
        select.setDistinct(true);
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
        ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
        ObjectContext context = cayenneRuntime.getContext();
        Expression expression = ExpressionFactory.greaterOrEqualDbExp(User.EMAIL_PROPERTY, "12");
        SelectQuery selectQuery = new SelectQuery(User.class, expression);
        System.out.println(context.performQuery(selectQuery));
    }

}
