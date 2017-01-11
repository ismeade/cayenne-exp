package com.ade.cayenne.http;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import java.io.File;

/**
 *
 * Created by liyang on 2017/1/3.
 */
public class TomcatServer {

    private final static TomcatServer instance = new TomcatServer();

    private TomcatServer() {
    }

    public static TomcatServer getInstance() {
        return instance;
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void startUp() throws LifecycleException {
        logger.info("tomcat server startup...");
        int port = 8080;
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        File base = new File(System.getProperty("user.dir"));
        Context rootCtx = tomcat.addContext("/", base.getAbsolutePath());

        rootCtx.setDocBase(base.getPath());
        addServlet(rootCtx, "/", new DefaultServlet());
        Tomcat.addServlet(rootCtx, "cayenne-rop", "org.apache.cayenne.configuration.rop.server.ROPHessianServlet");
        rootCtx.addServletMapping("/cayenne-service", "cayenne-rop");


        tomcat.start();
        tomcat.getServer().await();
    }

    private void addServlet(Context rootCtx, String page, HttpServlet httpServlet) {
        Tomcat.addServlet(rootCtx, httpServlet.getClass().getName(), httpServlet);
        rootCtx.addServletMapping(page, httpServlet.getClass().getName());
        logger.info("add Mapping : " + page + " -> " + httpServlet.getClass().getName());
    }

    public static void main(String[] args) {
        try {
            getInstance().startUp();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

}
