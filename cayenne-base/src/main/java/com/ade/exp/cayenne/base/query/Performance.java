package com.ade.exp.cayenne.base.query;

import com.ade.exp.cayenne.base.persistent.Company;
import com.ade.exp.cayenne.base.persistent.User;
import org.apache.cayenne.CayenneException;
import org.apache.cayenne.DataRow;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.access.ResultIterator;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SelectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 一些提高性能的查询方法，但不一定适用于所有情况，根据实际情况选择
 * Created by LiYang on 2016/7/13.
 */
public class Performance {

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
    ObjectContext context = cayenneRuntime.getContext();

    /**
     * 预抓取，需要提前设置好映射关系，直接将关联关系的所有分支都查出来
     * 总共会执行N+1次sql查询，n=映射关系层数
     */
    public void prefetch() {
        SelectQuery query = new SelectQuery(Company.class);
        query.addPrefetch(Company.USER_PROPERTY);
        List list = null;
        try {
            // 预抓取的对应关系
            // 多层关系可以使用'.'关联
            // query.addPrefetch(Company.USER_PROPERTY + "." + 第二层关联关系);
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
     * 数据行查询
     * 转换结果集需要较大的开销，select.setFetchingDataRows(true)可以跳过这一步，把结果以DataRow形式返回。
     */
    public void dataRowQuery() {
        SelectQuery select = new SelectQuery(User.class);
        select.setFetchingDataRows(true);
        List list = null;
        try {
            list = context.performQuery(select);
        } catch (Exception e) {
            logger.error("select error:" + e.getLocalizedMessage(), e);
        }
        if (null != list) {
            // 遍历获取属性值
            for (Object obj : list) {
                DataRow dataRow = (DataRow) obj;
                logger.info("User id:"      + dataRow.get(User.ID_PK_COLUMN));
                logger.info("User name:"    + dataRow.get(User.NAME_PROPERTY));
                logger.info("User age:"     + dataRow.get(User.AGE_PROPERTY));
                logger.info("User address:" + dataRow.get(User.ADDERSS_PROPERTY));
            }

            // 还可以手工将DataRow转成对象，并且加入一些条件筛选
            DataContext dataContext = (DataContext) context;
            for(Object obj : list) {
                DataRow dataRow = (DataRow) obj;
                if(dataRow.get(User.NAME_PROPERTY) != null) {
                    User user = dataContext.objectFromDataRow(User.class, dataRow);
                    // do something with Artist...
                    logger.info("User:" + user);
                }
            }
        }
    }

    /**
     * 迭代查询
     * 当代硬件可能轻易允许应用程序获取成百上千,甚至成千上万的对象到内存中,这并不意味着是一个好主意。
     * 可以使用两种技术处理结果集：一、迭代查询。
     * 测试时只使用了少量的数据测试，只看到一次sql查询，不知道具体原理，后续进一步测试
     * 缺点：需要所有处理完成之后才会释放链接。
     *
     */
    public void iteratorQuery() {
        DataContext dataContext = (DataContext) context;
        SelectQuery select = new SelectQuery(User.class);
        ResultIterator it = null;
        try {
            it = dataContext.performIteratedQuery(select);
            System.out.println("-------------------------");
            while(it.hasNextRow()) {
                DataRow row = (DataRow) it.nextRow();
                // do something with the row...
                logger.info("User:" + row);
            }
        } catch (Exception e) {
            logger.error("select error:" + e.getLocalizedMessage(), e);
        } finally {
            if (null != it) {
                try {
                    it.close();
                } catch (CayenneException e) {
                    //
                }
            }
        }
    }

    /**
     * 分页查询
     * 当代硬件可能轻易允许应用程序获取成百上千,甚至成千上万的对象到内存中,这并不意味着是一个好主意。
     * 可以使用两种技术处理结果集：二、迭代查询。
     * 缺点：每次取区间消息行时，使用in|or。
     *
     */
    public void pageQuery() {
        SelectQuery select = new SelectQuery(User.class);
        select.setPageSize(2);
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
     * 结合分页及数据行查询
     * 最优查询方法
     */
    public void pageAndDataRowQuery() {
        SelectQuery select = new SelectQuery(User.class);
        select.setPageSize(2);
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

}
