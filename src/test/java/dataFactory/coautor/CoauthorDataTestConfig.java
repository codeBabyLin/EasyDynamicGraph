package dataFactory.coautor;

import dataFactory.dataConfig.QueryGeneratorConfig;

import java.io.File;

public class CoauthorDataTestConfig implements QueryGeneratorConfig {
    private int dataVersion = 27;
    private int startVersion = 1986;
    private int endVersion = 2012;

    private int singVersionQuerySize = 10;

    private int multiVersionQuerySize = 10;

    private int sameVersionQuerySize = 10;

    private int diffVersionQuerySize = 10;

    private int deltaVersionQuerySize = 10;

    private String fileName = "coauthorQuery.rtt";
    private String logFileName = "coauthorQueryLog.txt";

    public String getQueryFileName(){
        String pathDir = System.getProperty("user.dir")+"\\Query";
        new File(pathDir).mkdir();
        String queryFileName = new File(pathDir,this.fileName).getAbsolutePath();
        return queryFileName;
    }
    public String getLogFileName(){
        String pathDir = System.getProperty("user.dir")+"\\Query";
        new File(pathDir).mkdir();
        String logFileName = new File(pathDir,this.logFileName).getAbsolutePath();
        return logFileName;
    }

    @Override
    public int getSingleVersionQuerySize() {
        return this.singVersionQuerySize;
    }

    @Override
    public int getMultiVersionQuerySize() {
        return this.multiVersionQuerySize;
    }

    @Override
    public int getSameVersionQuerySize() {
        return this.sameVersionQuerySize;
    }

    @Override
    public int getDiffVersionQuerySize() {
        return this.diffVersionQuerySize;
    }

    @Override
    public int getDeltaVersionQuerySize() {
        return this.deltaVersionQuerySize;
    }

    @Override
    public int getDataVersionSize() {
        return this.dataVersion;
    }

    @Override
    public int getDataStartVersion() {
        return this.startVersion;
    }

    @Override
    public int getDataEndVersion() {
        return this.endVersion;
    }
}
