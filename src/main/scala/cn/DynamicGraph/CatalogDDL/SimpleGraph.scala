package cn.DynamicGraph.CatalogDDL

import scala.collection.mutable

class SimpleGraph {
  private val nodesSet:mutable.HashSet[Long] = new mutable.HashSet()
  private val relationsSet: mutable.HashSet[Long] = new mutable.HashSet()
  //private val
  def addNode(node: Long): Unit ={
    this.nodesSet.add(node)
  }
  def delNode(node: Long): Unit ={
    this.nodesSet.remove(node)
  }
  def addNodes(nodes: Set[Long]): Unit ={
    nodes.foreach(addNode)
  }
  def delNodes(nodes: Set[Long]): Unit ={
    nodes.foreach(delNode)
  }

  def addRelation(rel: Long): Unit ={
    this.relationsSet.add(rel)
  }
  def delRelation(rel: Long): Unit ={
    this.relationsSet.remove(rel)
  }
  def addRelations(rels: Set[Long]): Unit ={
    rels.foreach(addRelation)
  }
  def delRelations(rels: Set[Long]): Unit ={
    rels.foreach(delRelation)
  }

  def AllNodes: mutable.HashSet[Long] ={
    this.nodesSet
  }
  def AllRelations:mutable.HashSet[Long] = {
    this.relationsSet
  }

}
