/*
 * Copyright (c) 2002-2019 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.cypher.internal.v3_5.frontend.phases

import org.neo4j.cypher.internal.v3_5.ast.UnaliasedReturnItem
import org.neo4j.cypher.internal.v3_5.ast.semantics.{SemanticCheckResult, SemanticChecker, SemanticFeature, SemanticState, SemanticTable}
import org.neo4j.cypher.internal.v3_5.frontend.phases.CompilationPhaseTracer.CompilationPhase.SEMANTIC_CHECK
import org.neo4j.cypher.internal.v3_5.rewriting.conditions.containsNoNodesOfType

case class SemanticAnalysis(warn: Boolean, features: SemanticFeature*)
  extends Phase[BaseContext, BaseState, BaseState] {

  override def process(from: BaseState, context: BaseContext): BaseState = {
    val startState = {
      if (from.initialFields.nonEmpty)
        SemanticState.withStartingVariables(from.initialFields.toSeq: _*)
      else
        SemanticState.clean
    }.withFeatures(features: _*).withFeatures(SemanticFeature.MultipleGraphs)

    val SemanticCheckResult(state, errors) = SemanticChecker.check(from.statement(), startState)
    if (warn) state.notifications.foreach(context.notificationLogger.log)

    context.errorHandler(errors)

    val table = SemanticTable(types = state.typeTable, recordedScopes = state.recordedScopes)
    from.withSemanticState(state).withSemanticTable(table)
  }

  override def phase: CompilationPhaseTracer.CompilationPhase = SEMANTIC_CHECK

  override def description = "do variable binding, typing, type checking and other semantic checks"

  override def postConditions = Set(BaseContains[SemanticState], StatementCondition(containsNoNodesOfType[UnaliasedReturnItem]))
}
