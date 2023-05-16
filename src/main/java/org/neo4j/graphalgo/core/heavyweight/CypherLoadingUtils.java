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
package org.neo4j.graphalgo.core.heavyweight;

import org.neo4j.graphalgo.core.WeightMap;
import org.neo4j.graphdb.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.neo4j.graphalgo.core.heavyweight.HeavyCypherGraphFactory.LIMIT;
import static org.neo4j.graphalgo.core.heavyweight.HeavyCypherGraphFactory.SKIP;

public class CypherLoadingUtils {
    public static  boolean canBatchLoad(boolean loadConcurrent, int batchSize, String statement) {
        return loadConcurrent && batchSize > 0 &&
                (statement.contains("{" + LIMIT + "}") || statement.contains("$" + LIMIT)) &&
                (statement.contains("{" + SKIP + "}") || statement.contains("$" + SKIP));
    }

    public static WeightMap newWeightMapping(boolean needWeights, double defaultValue, int capacity) {
        return needWeights ? new WeightMap(capacity, defaultValue, -2) : null;
    }

    public static Map<String, Object> params(Map<String, Object> baseParams, long offset, int batchSize) {
        Map<String, Object> params = new HashMap<>(baseParams);
        params.put(SKIP, offset);
        if (batchSize > 0) {
            params.put(LIMIT, batchSize);
        }
        return params;
    }

    public static <T> T get(String message, Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted: " + message, e);
        } catch (ExecutionException e) {
            throw new RuntimeException(message, e);
        }
    }

    public static Object getProperty(Result.ResultRow row, String propertyName) {
        try {
            return row.get(propertyName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
