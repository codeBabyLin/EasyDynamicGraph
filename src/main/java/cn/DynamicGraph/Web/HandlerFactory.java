package cn.DynamicGraph.Web;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.neo4j.kernel.impl.factory.GraphDatabaseFacade;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class HandlerFactory {

    public ResourceHandler getResourceHandler() throws URISyntaxException {
        ResourceHandler rh = new ResourceHandler();
        rh.setDirectoriesListed(true);
        //URI uri = HandlerFactory.class.getResource("Web/BaiduMap").toURI();
        //String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        //File p = new File(path).getParentFile().getParentFile();
        String uri = Objects.requireNonNull(this.getClass().getClassLoader().getResource("Web/BaiduMap")).getPath();
       // String uri = new File(p,"conf/").getAbsolutePath();
        System.out.println(uri);
        rh.setResourceBase(uri);
        rh.setWelcomeFiles(new String[]{"map.html"});
        return rh;
    }

    public  ContextHandler getContextHandler() throws URISyntaxException {
        ContextHandler ch = new ContextHandler();
        ch.setContextPath("/browser/map");
        //ch.setContextPath("/map");
        ch.setHandler(getResourceHandler());
        return ch;

    }

}
