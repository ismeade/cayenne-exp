package com.ade.exp.cayenne.mult.query;

import com.ade.exp.cayenne.mult.persistent.db.User;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 提供了使用Cayenne的几种查询方法
 * Created by LiYang on 2016/7/13.
 */
public class BasicQuery {

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
    ObjectContext context = cayenneRuntime.getContext();

    /**
     * 主键查询
     * @param pk 主键
     */
    public void getUser1(Integer pk) {
        ObjectId id = new ObjectId(User.class.getSimpleName(), User.ID_PK_COLUMN, pk);
        ObjectIdQuery query = new ObjectIdQuery(id);
        User user = null;
        try {
            user = (User) Cayenne.objectForQuery(context, query);
        } catch (Exception e) {
            logger.error("select error:" + e.getLocalizedMessage(), e);
        }
        logger.info("User:" + user);
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
        BasicQuery basicQuery = new BasicQuery();
    }

}
