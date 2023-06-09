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
package org.neo4j.cypher.internal.compiler.v3_5.ast.convert.plannerQuery

import org.neo4j.cypher.internal.compiler.v3_5.ast.convert.plannerQuery.ClauseConverters._
import org.neo4j.cypher.internal.ir.v3_5.{ExceptQuery, IntersectQuery, PeriodicCommit, UnionQuery, UnionTQuery}
import org.neo4j.cypher.internal.v3_5.ast
import org.neo4j.cypher.internal.v3_5.ast._
import org.neo4j.cypher.internal.v3_5.ast.semantics.SemanticTable
import org.neo4j.cypher.internal.v3_5.expressions.And
import org.neo4j.cypher.internal.v3_5.expressions.Or
import org.neo4j.cypher.internal.v3_5.expressions.Pattern
import org.neo4j.cypher.internal.v3_5.expressions.PatternPart
import org.neo4j.cypher.internal.v3_5.util.ASTNode
import org.neo4j.cypher.internal.v3_5.util.InputPosition
import org.neo4j.cypher.internal.v3_5.util.InternalException

import scala.collection.mutable.ArrayBuffer

object StatementConverters {
  import org.neo4j.cypher.internal.v3_5.util.Foldable._

  def toPlannerQueryBuilder(q: SingleQuery, semanticTable: SemanticTable): PlannerQueryBuilder =
    flattenCreates(q.clauses).foldLeft(PlannerQueryBuilder(semanticTable)) {
      case (acc, clause) => addToLogicalPlanInput(acc, clause)
    }

  private val NODE_BLACKLIST: Set[Class[_ <: ASTNode]] = Set(
    classOf[And],
    classOf[Or],
    // classOf[ReturnAll],
    classOf[UnaliasedReturnItem],
    classOf[Start]
  )


  private def findBlacklistedNodes(node: AnyRef): Seq[ASTNode] = {
    node.treeFold(Seq.empty[ASTNode]) {
      case node: ASTNode if NODE_BLACKLIST.contains(node.getClass) =>
        acc => (acc :+ node, Some(identity))
    }
  }

  def toUnionQuery(query: Query, semanticTable: SemanticTable): UnionQuery = {
    val nodes = findBlacklistedNodes(query)
    require(nodes.isEmpty, "Found a blacklisted AST node: " + nodes.head.toString)

    query match {
      case Query(periodicCommitHint, queryPart: SingleQuery) =>
        val builder = toPlannerQueryBuilder(queryPart, semanticTable)
        UnionTQuery(Seq(builder.build()), distinct = false, builder.returns, PeriodicCommit(periodicCommitHint))

      case Query(periodicCommitHint, u: ast.Union) =>
        val queries: Seq[SingleQuery] = u.unionedQueries
        val distinct = u match {
          case _: UnionAll => false
          case _: UnionDistinct => true
        }
        val plannedQueries: Seq[PlannerQueryBuilder] = queries.reverseMap(x => toPlannerQueryBuilder(x, semanticTable))
        //UNION requires all queries to return the same variables
        assert(plannedQueries.nonEmpty)
        val returns = plannedQueries.head.returns

        UnionTQuery(plannedQueries.map(_.build()), distinct, returns, PeriodicCommit(periodicCommitHint))

      case Query(periodicCommitHint, u: ast.Exception) =>
        val queries: Seq[SingleQuery] = u.unionedQueries
        val distinct = u match {
          case _: Exception => false
          case _ => true
        }
        val plannedQueries: Seq[PlannerQueryBuilder] = queries.reverseMap(x => toPlannerQueryBuilder(x, semanticTable))
        //UNION requires all queries to return the same variables
        assert(plannedQueries.nonEmpty)
        val returns = plannedQueries.head.returns

        ExceptQuery(plannedQueries.map(_.build()), distinct, returns, PeriodicCommit(periodicCommitHint))

      case Query(periodicCommitHint, u: ast.Intersect) =>
        val queries: Seq[SingleQuery] = u.unionedQueries
        val distinct = u match {
          case _: Intersect => false
          case _=> true
        }
        val plannedQueries: Seq[PlannerQueryBuilder] = queries.reverseMap(x => toPlannerQueryBuilder(x, semanticTable))
        //UNION requires all queries to return the same variables
        assert(plannedQueries.nonEmpty)
        val returns = plannedQueries.head.returns

        IntersectQuery(plannedQueries.map(_.build()), distinct, returns, PeriodicCommit(periodicCommitHint))

      case _ =>
        throw new InternalException(s"Received an AST-clause that has no representation the QG: $query")
    }
  }

  /**
   * Flatten consecutive CREATE clauses into one.
   *
   *   CREATE (a) CREATE (b) => CREATE (a),(b)
   */
  def flattenCreates(clauses: Seq[Clause]): Seq[Clause] = {
    val builder = ArrayBuffer.empty[Clause]
    var prevCreate: Option[(Seq[PatternPart], InputPosition)] = None
    for (clause <- clauses) {
      (clause, prevCreate) match {
        case (c: Create, None) =>
          prevCreate = Some((c.pattern.patternParts, c.position))

        case (c: Create, Some((prevParts, pos))) =>
          prevCreate = Some((prevParts ++ c.pattern.patternParts, pos))

        case (nonCreate, Some((prevParts, pos))) =>
          builder += Create(Pattern(prevParts)(pos))(pos)
          builder += nonCreate
          prevCreate = None

        case (nonCreate, None) =>
          builder += nonCreate
      }
    }
    for ((prevParts, pos) <- prevCreate)
      builder += Create(Pattern(prevParts)(pos))(pos)
    builder
  }
}
