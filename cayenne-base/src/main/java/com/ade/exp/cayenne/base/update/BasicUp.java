package com.ade.exp.cayenne.base.update;

import com.ade.exp.cayenne.base.persistent.User;
import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.DataObject;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by LiYang on 2016/7/14.
 */
public class BasicUp {

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
    ObjectContext context        = cayenneRuntime.getContext();

    /**
     * 最基本新建数据方法，会自动生成pk，需要再modeler配置主键生成规则
     */
    public void insertObject() {
        User user = context.newObject(User.class);
        user.setName("User99");
        user.setAge(33);
        user.setAdderss("user99 address");
        try {
            // 提交变化
            context.commitChanges();
        } catch (Exception e) {
            logger.error("insert error:" + e.getLocalizedMessage(), e);
        }
    }

    /**
     * 指定pk插入，需要插入特定主键的数据时使用，很少用到
     */
    public void insertPkObject() {
        try {
            DataObject author = new CayenneDataObject();
            author.setObjectId(new ObjectId(User.class.getSimpleName(), User.ID_PK_COLUMN, 888));
            author.writeProperty(User.NAME_PROPERTY, "User888");
            author.writeProperty(User.AGE_PROPERTY, 88);
            context.registerNewObject(author);
            context.commitChanges();
        } catch (Exception e) {
            logger.error("insert error:" + e.getLocalizedMessage(), e);
        }
    }

    public static void main(String[] args) {
        new BasicUp().insertObject();
    }

}
