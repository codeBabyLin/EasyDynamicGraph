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
package org.neo4j.graphalgo.impl.results;

import org.neo4j.graphalgo.core.write.Exporter;

import java.util.function.Function;

public  class NormalizedCentralityResult implements CentralityResult {
    private CentralityResult result;
    private Function<Double, Double> normalizationFunction;

    public NormalizedCentralityResult(CentralityResult result, Function<Double, Double> normalizationFunction) {
        this.result = result;
        this.normalizationFunction = normalizationFunction;
    }

    @Override
    public void export(String propertyName, Exporter exporter, Function<Double, Double> normalizationFunction) {
        result.export(propertyName, exporter, normalizationFunction);
    }

    @Override
    public double score(int nodeId) {
        return normalizationFunction.apply(result.score(nodeId));
    }

    @Override
    public double score(long nodeId) {
        return normalizationFunction.apply(result.score(nodeId));
    }

    @Override
    public void export(String propertyName, Exporter exporter) {
        export(propertyName, exporter, normalizationFunction);
    }

    @Override
    public double computeMax() {
        return result.computeMax();
    }

    @Override
    public double computeL2Norm() {
        return result.computeL2Norm();
    }

    @Override
    public double computeL1Norm() {
        return result.computeL1Norm();
    }
}
