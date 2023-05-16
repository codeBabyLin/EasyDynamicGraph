package org.neo4j.cypher.internal.runtime.interpreted.pipes

import cn.DynamicGraph.Version.FilterVersion
import cn.DynamicGraph.graphdb.kernel.UserGraph
import org.neo4j.cypher.internal.runtime.interpreted.ExecutionContext
import org.neo4j.cypher.internal.v3_5.util.attribution.Id
import org.neo4j.kernel.impl.util.RelationshipProxyWrappingValue
import org.neo4j.values.virtual.{NodeValue, RelationshipValue}

import scala.collection.mutable

/*class CreateGraphPipe {

}*/
case class CreateGraphPipe(source: Pipe, graphName: String)
                                 (val id: Id = Id.INVALID_ID) extends PipeWithSource(source) {

  //predicate.registerOwningPipe(this)

  override def createResults(state: QueryState): Iterator[ExecutionContext] = {

    //state.query.setVersion(filter)

    val sourceResult = source.createResults(state)

    val graphDDl:UserGraph[java.lang.Long,java.lang.Long,java.lang.String] = state.query.entityAccessor.getGraphDatabase.getDyGraph.getUserGraph
    val nodeIds: mutable.HashSet[Long] = new mutable.HashSet[Long]()
    //val res:Iterator[ExecutionContext] = sourceResult.clone()

    sourceResult.foreach(f => {
      val res = f.result()
      res.foreach(u =>{
        u._2 match {
          case x:NodeValue => nodeIds.add(x.id())
          case y: RelationshipValue =>
            nodeIds.add(y.startNode().id())
            nodeIds.add(y.endNode().id())

        }
        //val id = u._2.asInstanceOf[NodeValue].id()
        //nodeIds.add(id)
      })
    })
    graphDDl.addGraph(this.graphName)
    nodeIds.foreach(f =>graphDDl.addNodeToGraph(this.graphName,f))
    //graphDDl.addNodesToGraph(this.graphName,)

    val decoratedState = state.decorator.decorate(this, state)
    decoratedState.setExecutionContextFactory(executionContextFactory)
    val result = internalCreateResults(sourceResult, decoratedState)
    state.decorator.decorate(this, result)
  }

  protected def internalCreateResults(input: Iterator[ExecutionContext], state: QueryState): Iterator[ExecutionContext] ={
    //val version = state.params.get(predicate.asInstanceOf[Parameter].name).asInstanceOf[LongValue].asObjectCopy()

    // state.query.setVersion(version)


    input
  }

  //input.filter(ctx => predicate(ctx, state) eq Values.TRUE)
}