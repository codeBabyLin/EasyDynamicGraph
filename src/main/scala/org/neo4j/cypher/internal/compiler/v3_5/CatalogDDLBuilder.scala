package org.neo4j.cypher.internal.compiler.v3_5

import org.neo4j.cypher.internal.compiler.v3_5.phases.{LogicalPlanState, PlannerContext}
import org.neo4j.cypher.internal.planner.v3_5.spi.ProcedurePlannerName
import org.neo4j.cypher.internal.v3_5.ast.semantics.{SemanticCheckResult, SemanticState}
import org.neo4j.cypher.internal.v3_5.ast.{CreateGraph, CreateIndex, CreateNodeKeyConstraint, CreateNodePropertyExistenceConstraint, CreateRelationshipPropertyExistenceConstraint, CreateUniquePropertyConstraint, DropIndex, DropNodeKeyConstraint, DropNodePropertyExistenceConstraint, DropRelationshipPropertyExistenceConstraint, DropUniquePropertyConstraint, Query, SingleQuery}
import org.neo4j.cypher.internal.v3_5.frontend.phases.CompilationPhaseTracer.CompilationPhase.PIPE_BUILDING
import org.neo4j.cypher.internal.v3_5.frontend.phases.{BaseState, CompilationPhaseTracer, Condition, InitialState, Phase}
import org.neo4j.cypher.internal.v3_5.logical.plans
import org.neo4j.cypher.internal.v3_5.logical.plans.{CreateGraphPlan, LogicalPlan, ResolvedCall}
import org.neo4j.cypher.internal.v3_5.util.attribution.SequentialIdGen

case object CatalogDDLBuilder extends Phase[PlannerContext, BaseState, LogicalPlanState] {
  override def phase: CompilationPhaseTracer.CompilationPhase = PIPE_BUILDING

  override def description: String = "process catalogddl "

  override def process(from: BaseState, context: PlannerContext): LogicalPlanState = {
    implicit val idGen = new SequentialIdGen()
    var newFrom = from
    val maybeLogicalPlan: Option[LogicalPlan] = from.statement() match {

      case CreateGraph(graphName,query) =>
        //SingleQuery

        // val planState = LogicalPlanState(from)
        //val newState = planState.withStatement(Query(None,query)(query.position))
        newFrom = InitialState(from.queryText,Some(query.position),
          from.plannerName,from.initialFields,Some(Query(None,query)(query.position)))
        //val newState = planState.copy(maybeStatement = Some(Query(None,query)(query.position)))
        //val planState = LogicalPlanState(newState)
        //val logicalPlan = this.process(newState,context).maybeLogicalPlan
        //val pstate = LogicalPlanState(query)
        //this.process()
        // val source = planPart.apply(Planer)
        Some(plans.CreateGraphPlan(graphName,null))


      case _ => None
    }

    val planState = LogicalPlanState(newFrom)
    //val planStatePlus = new LogicalPlanStatePlus(planState,maybeLogicalPlan)
    //planStatePlus
    if (maybeLogicalPlan.isDefined)
      //planState.withMaybeCataloglPlan(maybeLogicalPlan)
      planState.copy(maybeLogicalPlan = maybeLogicalPlan, plannerName = ProcedurePlannerName)
    else planState
  }
  def process2(l1: LogicalPlanState, l2: LogicalPlanState): LogicalPlanState = {
    implicit val idGen = new SequentialIdGen()
    val p1: LogicalPlan = l1.maybeLogicalPlan.orNull
    val p2: LogicalPlan = l2.maybeLogicalPlan.orNull
    val newPlan = {
      if (p1 == null)  p2
      else {
        p1 match {
          case x: CreateGraphPlan => CreateGraphPlan(x.graphName,p2)
          case _ => p2
        }
      }
    }
    l2.copy(maybeLogicalPlan = Some(newPlan))
  }

  override def postConditions: Set[Condition] = Set.empty
}
