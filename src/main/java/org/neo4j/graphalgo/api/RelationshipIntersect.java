/*
 * Copyright (c) 2017 "Neo4j, Inc." <http://neo4j.com>
 *
 * This file is part of Neo4j Graph Algorithms <http://github.com/neo4j-contrib/neo4j-graph-algorithms>.
 *
 * Neo4j Graph Algorithms is free software: you can redistribute it and/or modify
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
package org.neo4j.graphalgo.api;

import org.neo4j.graphdb.Direction;

/**
 * Intersect adjacency lists of two nodes.
 * Only {@link Direction#OUTGOING} is used for intersection.
 * If you want to intersect on {@link Direction#BOTH}, you have to
 * load the graph with {@link org.neo4j.graphalgo.core.GraphLoader#asUndirected(boolean)}
 * set to {@code true}.
 */
public interface RelationshipIntersect {

    /**
     * @see HugeDegrees#degree(long, Direction)
     */
//    int degree(long nodeId);

    /**
     * @see HugeRelationshipIterator#forEachOutgoing(long, HugeRelationshipConsumer)
     */
//    void forEachRelationship(long nodeId, HugeRelationshipConsumer consumer);

    void intersectAll(long nodeIdA, IntersectionConsumer consumer);
}
