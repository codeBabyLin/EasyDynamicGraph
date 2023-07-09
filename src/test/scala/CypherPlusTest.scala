import common.BaseTest
import org.junit.Test
import org.neo4j.graphdb.factory.GraphDatabaseFactory

import java.io.File

class CypherPlusTest extends BaseTest{


  @Test
  def testCreate(): Unit ={
    //val path = "F:\\DynamicGraphStore\\DynamicGraphPlus"
    val dataBaseDir = new File(path,"data")
    graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dataBaseDir)
    registerShutdownHook(graphDb)
    //var tx = graphDb.beginTx()//1
    // val version = DGVersion.toString(tx.getVersion)
    val cy1 = "createv(n:student{name:'JoeJoe',age:23}) at v1=5 and v2=5 return n"
    val cy2 = "createv(n:student{name:'Baby',age:23}) at v1=4 and v2=4 return n"
    val cy3 = "createv(n:student{name:'loli',age:23}) at v1=4 and v2=4 return n"
    val cy4 = "matchTime(n) timerange v=5 or v=4 return n intersection matchTime(n) timerange v=4 return n"

    var tx = graphDb.beginTx()
    graphDb.execute(cy1)
    graphDb.execute(cy2)
    graphDb.execute(cy3)
    tx.success()
    tx.close()


    val t = graphDb.execute(cy4)
    while(t.hasNext){
      val res = t.next()
      println(res)
    }

  }

}
