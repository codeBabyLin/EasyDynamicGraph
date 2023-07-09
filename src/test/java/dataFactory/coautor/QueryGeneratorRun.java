package dataFactory.coautor;

import dataFactory.dataConfig.QueryGenerator;
import dataFactory.dataConfig.QueryGeneratorConfig;

public class QueryGeneratorRun {

    public static void main(String[]args){
        QueryGeneratorConfig qgc = new CoauthorDataTestConfig();
        QueryGenerator qg = new QueryGenerator(qgc);
        qg.generate();
    }
}
