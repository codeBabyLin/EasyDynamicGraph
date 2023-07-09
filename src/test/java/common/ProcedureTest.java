package common;

import org.junit.Test;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

public class ProcedureTest extends BaseTest{

    @Test
    public void testUserFunction(){
        File dataBaseDir = new File(path,"data");
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dataBaseDir);
        registerShutdownHook(graphDb);
    }

    @Test
    public void testUserCall(){
        //Jetty9WebServer js = new Jetty9WebServer();
    }
}
