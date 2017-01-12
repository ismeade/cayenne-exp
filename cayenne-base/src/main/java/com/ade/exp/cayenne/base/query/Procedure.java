package com.ade.exp.cayenne.base.query;

import java.util.List;
import java.util.Map;

import com.ade.exp.cayenne.base.persistent.User;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.QueryResponse;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.ProcedureQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 存储过程
 * Created by LiYang on 2016/7/14.
 */
public class Procedure {

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-base.xml");
    ObjectContext context = cayenneRuntime.getContext();

    /**
     * 没进行测试
     */
    public void procedureQuery() {
        ProcedureQuery query = new ProcedureQuery("my_procedure", User.class);
        // Set "IN" parameter values
        query.addParameter("p1", "abc");
        query.addParameter("p2", 3000);
        List result = context.performQuery(query);

        // 另一种方法
        QueryResponse response = context.performGenericQuery(query);
        // read OUT parameters
        List out = response.firstList();
        if(!out.isEmpty()) {
            Map outParameterValues = (Map) out.get(0);
        }
        
    }

}
