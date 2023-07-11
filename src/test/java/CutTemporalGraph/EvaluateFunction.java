package CutTemporalGraph;

import CutTemporalGraph.backup.TreeState;

import java.util.ArrayList;

public interface EvaluateFunction {
    long evaluate(ArrayList<SecGraphState> state);
}
