package org.neo4j.cypher.internal.v3_5.logical.plans

import org.neo4j.cypher.internal.ir.v3_5.Predicate
import org.neo4j.cypher.internal.v3_5.ast.CatalogName
import org.neo4j.cypher.internal.v3_5.util.attribution.IdGen

//case class CreateGraphPlan()
/*
case class CreateGraphPlan(graphName: CatalogName,
                              source: LogicalPlan
                             )(implicit idGen: IdGen) extends LogicalPlan(idGen) with LazyLogicalPlan {
  //assert(Expression, "A selection plan should never be created without predicates")

  val lhs = Some(source)
  def rhs = None

  //def numPredicates: Int = predicate.exprs.size

  val availableSymbols: Set[String] = source.availableSymbols
}*/


case class CreateGraphPlan(graphName: CatalogName,source: LogicalPlan)(implicit idGen: IdGen) extends LogicalPlan(idGen) with LazyLogicalPlan {
  //assert(Expression, "A selection plan should never be created without predicates")

  val lhs = Some(source)
  def rhs = None

  //def numPredicates: Int = predicate.exprs.size

  val availableSymbols: Set[String] = Set.empty
}
