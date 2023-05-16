package org.neo4j.cypher.internal.runtime.interpreted.pipes

import cn.DynamicGraph.Version.FilterVersion
import org.neo4j.cypher.internal.runtime.interpreted.ExecutionContext
import org.neo4j.cypher.internal.v3_5.util.attribution.Id


case class GraphNameSelectPipe(source: Pipe, graphName: String)
                                 (val id: Id = Id.INVALID_ID) extends PipeWithSource(source) {

  //predicate.registerOwningPipe(this)

  override def createResults(state: QueryState): Iterator[ExecutionContext] = {

    //state.query.setGraphName(graphName)
    state.query.entityAccessor.getGraphDatabase.getDyGraph.setReadName(graphName)

    val sourceResult = source.createResults(state)

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
