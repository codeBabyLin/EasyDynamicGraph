package cn.DynamicGraph.CatalogDDL

//import cn.DynamicGraph.SampleGraph.SampleGraph

import scala.collection.mutable


class GraphDDL {
  private val graphPools:mutable.HashMap[String,SimpleGraph] = new mutable.HashMap()

  def getGraph(name: String):SimpleGraph = {
    graphPools.get(name).orNull
  }
  def addGraph(name: String): SimpleGraph ={
    val graph = new SimpleGraph
    graphPools.update(name,graph)
    println(s"create Graph ${name}")
    graph
  }
  def delGraph(name: String): Unit ={
    graphPools.remove(name)
  }


  def allnodesOfGraph(name: String):Iterator[Long] = {
    getGraph(name).AllNodes.iterator
  }
  def allRelationsOfGraph(name: String):Iterator[Long] = {
    getGraph(name).AllRelations.iterator
  }

  def addNodeToGraph(name: String, node: Long): Unit ={

    getGraph(name).addNode(node)
  }
  def addNodesToGraph(name: String, nodes: Set[Long]): Unit ={
    getGraph(name).addNodes(nodes)
    println(s" Graph nodes add: ${nodes.mkString(",")}")
  }

  def addRelationToGraph(name: String, rel: Long): Unit ={
    getGraph(name).addRelation(rel)
  }
  def addRelationsToGraph(name: String, rel: Set[Long]): Unit ={
    getGraph(name).addRelations(rel)
  }
}
