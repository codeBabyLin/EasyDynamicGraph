import cn.DynamicGraph.BootStrap.DynamicGraphStarter

import java.io.File

object ServerStart {
  def delfile(file: File): Unit ={
    if(file.isDirectory){
      val files = file.listFiles()
      files.foreach(delfile)
    }
    file.delete()
  }

  def main(args: Array[String]): Unit = {
    val NEO4J_HOME = "F:\\EasyDynamicGraphStore\\Server"
    val NEO4J_CONF = "F:\\ideaCode\\EasyDynamicGraph\\"

    delfile(new File(NEO4J_HOME))
    DynamicGraphStarter.main(Array(s"--home-dir=${NEO4J_HOME}", s"--config-dir=${NEO4J_CONF}"))
  }
}
