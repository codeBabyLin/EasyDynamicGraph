package org.neo4j.cypher.internal.runtime.interpreted.pipes

import cn.DynamicGraph.Version.FilterVersion
//import org.neo4j.cypher.internal.ir.v3_5.Predicate
import org.neo4j.cypher.internal.runtime.interpreted.ExecutionContext
//import org.neo4j.cypher.internal.v3_5.expressions.SignedDecimalIntegerLiteral
//import org.neo4j.cypher.internal.v3_4.expressions.SignedDecimalIntegerLiteral
//import org.neo4j.cypher.internal.v3_5.expressions.{Expression, Parameter}
import org.neo4j.cypher.internal.v3_5.util.attribution.Id
//import org.neo4j.values.storable.{LongValue, Values}

//case class GraphVersionSelectPipe()

case class GraphVersionSelectPipe(source: Pipe, filter: FilterVersion)
                     (val id: Id = Id.INVALID_ID) extends PipeWithSource(source) {

  //predicate.registerOwningPipe(this)

  override def createResults(state: QueryState): Iterator[ExecutionContext] = {

    //state.query.setVersion(filter)
    state.query.entityAccessor.getGraphDatabase.getDyGraph.setFilterVersion(filter)

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
