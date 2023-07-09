package dataFactory.dataConfig;

public interface QueryGeneratorConfig {

    String getQueryFileName();
    String getLogFileName();
    int getSingleVersionQuerySize();
    int getMultiVersionQuerySize();
    int getSameVersionQuerySize();
    int getDiffVersionQuerySize();
    int getDeltaVersionQuerySize();

    int getDataVersionSize();
    int getDataStartVersion();
    int getDataEndVersion();

}
