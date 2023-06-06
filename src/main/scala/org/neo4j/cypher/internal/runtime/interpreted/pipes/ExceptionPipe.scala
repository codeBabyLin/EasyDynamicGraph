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
package org.neo4j.cypher.internal.runtime.interpreted.pipes

import org.neo4j.cypher.internal.runtime.interpreted.ExecutionContext
import org.neo4j.cypher.internal.v3_5.util.attribution.Id
import org.neo4j.values.AnyValue

import scala.collection.mutable

case class ExceptionPipe(l: Pipe, r: Pipe)
                    (val id: Id = Id.INVALID_ID) extends Pipe {
  protected def internalCreateResults(state: QueryState): Iterator[ExecutionContext] =
   // (l.createResults(state).toSet.diff(r.createResults(state).toSet)).toIterator
 {
   var seen = mutable.Set[AnyValue]()
   l.createResults(state).foreach(u=> u.result().values.foreach(seen.add))
   r.createResults(state).filterNot(p=> p.result().values.map(seen.contains).reduce(_&_))
 }
}
