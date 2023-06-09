/*
 * Copyright (c) 2002-2019 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v3_5.phases

import org.neo4j.cypher.internal.ir.v3_5.{PeriodicCommit, UnionQuery}
import org.neo4j.cypher.internal.planner.v3_5.spi.PlanningAttributes
import org.neo4j.cypher.internal.planner.v3_5.spi.PlanningAttributes.{Cardinalities, ProvidedOrders, Solveds}
import org.neo4j.cypher.internal.v3_5.logical.plans.LogicalPlan
import org.neo4j.cypher.internal.v3_5.ast.semantics.{SemanticState, SemanticTable}
import org.neo4j.cypher.internal.v3_5.ast.{Query, Statement}
import org.neo4j.cypher.internal.v3_5.frontend.PlannerName
import org.neo4j.cypher.internal.v3_5.frontend.phases.{BaseState, Condition}
import org.neo4j.cypher.internal.v3_5.util.InputPosition
import org.neo4j.cypher.internal.v3_5.util.symbols.CypherType

/*
This is the state that is used during query compilation. It accumulates more and more values as it passes through
the compiler pipe line, finally ending up containing a logical plan.

Normally, it is created with only the first three params as given, and the rest is built up while passing through
the pipe line
 */
case class LogicalPlanState(queryText: String,
                            startPosition: Option[InputPosition],
                            plannerName: PlannerName,
                            planningAttributes: PlanningAttributes,
                            maybeStatement: Option[Statement] = None,
                            maybeSemantics: Option[SemanticState] = None,
                            maybeExtractedParams: Option[Map[String, Any]] = None,
                            maybeSemanticTable: Option[SemanticTable] = None,
                            maybeUnionQuery: Option[UnionQuery] = None,
                            maybeLogicalPlan: Option[LogicalPlan] = None,
                            //maybeCatalogPlan: Option[LogicalPlan] = None,
                            maybePeriodicCommit: Option[Option[PeriodicCommit]] = None,
                            accumulatedConditions: Set[Condition] = Set.empty,
                            initialFields: Map[String, CypherType] = Map.empty) extends BaseState {

  var maybeCatalogPlan: Option[LogicalPlan] = None
  def unionQuery: UnionQuery = maybeUnionQuery getOrElse fail("Union query")
  def logicalPlan: LogicalPlan = maybeLogicalPlan getOrElse fail("Logical plan")
  def periodicCommit: Option[PeriodicCommit] = maybePeriodicCommit getOrElse fail("Periodic commit")
  def astAsQuery: Query = statement().asInstanceOf[Query]
  //def catalogPlan:LogicalPlan = maybeCatalogPlan getOrElse fail("Catalog plan")

  override def withStatement(s: Statement): LogicalPlanState = copy(maybeStatement = Some(s))
  override def withSemanticTable(s: SemanticTable): LogicalPlanState = copy(maybeSemanticTable = Some(s))
  override def withSemanticState(s: SemanticState): LogicalPlanState = copy(maybeSemantics = Some(s))
  override def withParams(p: Map[String, Any]): LogicalPlanState = copy(maybeExtractedParams = Some(p))

  def withMaybeLogicalPlan(p: Option[LogicalPlan]): LogicalPlanState = copy(maybeLogicalPlan = p)
  //def withMaybeCataloglPlan(p: Option[LogicalPlan]): LogicalPlanState = copy(maybeCatalogPlan = p)//{maybeCatalogPlan = p;this}
  def withMaybeCataloglPlan(p: Option[LogicalPlan]): LogicalPlanState = {maybeCatalogPlan = p;this}

  def withMaybeUnionQuery(p: Option[UnionQuery]): LogicalPlanState = copy(maybeUnionQuery = p)

  //def copy(p: Option[UnionQuery]):LogicalPlanState = copy(maybeUnionQuery = p)

}

object LogicalPlanState {
  def apply(state: BaseState): LogicalPlanState =
    LogicalPlanState(queryText = state.queryText,
      startPosition = state.startPosition,
      plannerName = state.plannerName,
      initialFields = state.initialFields,
      planningAttributes = PlanningAttributes(new Solveds, new Cardinalities, new ProvidedOrders),
      maybeStatement = state.maybeStatement,
      maybeSemantics = state.maybeSemantics,
      maybeExtractedParams = state.maybeExtractedParams,
      maybeSemanticTable = state.maybeSemanticTable,
      accumulatedConditions = state.accumulatedConditions)
}


