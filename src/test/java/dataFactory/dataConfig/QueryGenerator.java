package dataFactory.dataConfig;

import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Random;

public class QueryGenerator {

    private QueryGeneratorConfig qgc;

    public QueryGenerator(QueryGeneratorConfig qgc){
        this.qgc = qgc;
    }

    public QueryGeneratorConfig getQueryConfig(){
        return this.qgc;
    }


    public void generate(){
        Random random = new Random();

        int dataVersion = qgc.getDataVersionSize();
        int startVersion = qgc.getDataStartVersion();
        int endVersion = qgc.getDataEndVersion();

        int singVersionQuerySize = qgc.getSingleVersionQuerySize();

        int multiVersionQuerySize = qgc.getMultiVersionQuerySize();
        int sameVersionQuerySize = qgc.getSameVersionQuerySize();

        int diffVersionQuerySize = qgc.getDiffVersionQuerySize();

        int deltaVersionQuerySize = qgc.getDeltaVersionQuerySize();

        String queryFileName = qgc.getQueryFileName();
        String logFileName = qgc.getLogFileName();



        //CoauthorQueryGenerator cqg = new CoauthorQueryGenerator();
        int [] singleVersions = new int [singVersionQuerySize];
        for(int i =0;i<singVersionQuerySize;i++){
            int version = random.nextInt(dataVersion)+startVersion;
            singleVersions[i] = version;
        }
        HashSet<Pair<Integer,Integer>> deltaVersions = new HashSet<>();
        for(int i = 0;i<deltaVersionQuerySize;i++){
            int version1 = random.nextInt(dataVersion)+startVersion;
            int version2 = random.nextInt(dataVersion)+startVersion;
            deltaVersions.add(Pair.of(version1,version2));
        }

        HashSet<int[]> multiVersions = new HashSet<>();
        for(int i = 0;i<multiVersionQuerySize;i++){
            int versionSize = random.nextInt(dataVersion-1)+2;
            int []verions = new int[versionSize];
            for(int j =0;j<versionSize;j++){
                int version = random.nextInt(dataVersion)+startVersion;
                verions[j] = version;
            }
            multiVersions.add(verions);
        }

        HashSet<Pair<Integer,Integer>> diffVersions = new HashSet<>();
        for(int i = 0;i<diffVersionQuerySize;i++){
            int version1 = random.nextInt(dataVersion)+startVersion;
            int version2 = random.nextInt(dataVersion)+startVersion;
            diffVersions.add(Pair.of(version1,version2));
        }

        HashSet<int[]> sameVersions = new HashSet<>();
        for(int i = 0;i<sameVersionQuerySize;i++){
            int versionSize = random.nextInt(dataVersion-1)+2;
            int []verions = new int[versionSize];
            for(int j =0;j<versionSize;j++){
                int version = random.nextInt(dataVersion)+startVersion;
                verions[j] = version;
            }
            sameVersions.add(verions);
        }



        writeToFile(singleVersions,deltaVersions,multiVersions,diffVersions,sameVersions,queryFileName);
        PrintStream logPrint = null;
        try {
            logPrint = new PrintStream(new FileOutputStream(new File(logFileName)));

        }catch (Exception e){
            e.printStackTrace();
        }
        String log;
        String res;


        log = String.format("generate singQuery : %d",singleVersions.length);
        res = sameVersions.toString();
        System.out.println(log);
        logPrint.println(log);


        log = String.format("generate multiQuery : %d",multiVersions.size());
        System.out.println(log);
        logPrint.println(log);

        log = String.format("generate deltaQuery : %d",deltaVersions.size());
        System.out.println(log);
        logPrint.println(log);

        log = String.format("generate diffQuery : %d",diffVersions.size());
        System.out.println(log);
        logPrint.println(log);

        log = String.format("generate sameQuery : %d",sameVersions.size());
        System.out.println(log);
        logPrint.println(log);

    }


    public void writeToFile(int [] singleVersions,
                            HashSet<Pair<Integer,Integer>> deltaVersions,
                            HashSet<int[]> multiVersions,
                            HashSet<Pair<Integer,Integer>> diffVersions,
                            HashSet<int[]> sameVersions,String queryFileName){



        //System.out.println(fileNameDelta);
        //String fileNameDel = new File(path,"del.del").getName();
        try {
            RandomAccessFile fp = new RandomAccessFile(queryFileName, "rw");
            //write singleVersions
            int size = singleVersions.length;
            fp.writeInt(size);
            for(int i = 0;i<size;i++){
                fp.writeInt(singleVersions[i]);
            }
            //write deltaVersions
            size = deltaVersions.size();
            fp.writeInt(size);
            for(Pair<Integer,Integer> pair: deltaVersions){
                fp.writeInt(pair.getLeft());
                fp.writeInt(pair.getRight());
            }
            //write multiVersions
            size = multiVersions.size();
            fp.writeInt(size);
            for(int [] versions: multiVersions){
                int ksize = versions.length;
                fp.writeInt(ksize);
                for(int j = 0 ;j<ksize;j++){
                    fp.writeInt(versions[j]);
                }
            }
            //write diffversions
            size = diffVersions.size();
            fp.writeInt(size);
            for(Pair<Integer,Integer> pair: diffVersions){
                fp.writeInt(pair.getLeft());
                fp.writeInt(pair.getRight());
            }

            //write sameVersions
            size = sameVersions.size();
            fp.writeInt(size);
            for(int [] versions: sameVersions){
                int ksize = versions.length;
                fp.writeInt(ksize);
                for(int j = 0 ;j<ksize;j++){
                    fp.writeInt(versions[j]);
                }
            }
            fp.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void readFromFile(int [] singleVersions,
                             HashSet<Pair<Integer,Integer>> deltaVersions,
                             HashSet<int[]> multiVersions,
                             HashSet<Pair<Integer,Integer>> diffVersions,
                             HashSet<int[]> sameVersions,String queryFileName){

        /*String pathDir = System.getProperty("user.dir")+"\\Query";
        new File(pathDir).mkdir();
        String fileName = new File(pathDir,this.fileName).getAbsolutePath();*/

        //System.out.println(fileNameDelta);
        //String fileNameDel = new File(path,"del.del").getName();
        try {
            RandomAccessFile fp = new RandomAccessFile(queryFileName, "r");
            //read singleVersions
            int size = fp.readInt();

            for(int i = 0;i<size;i++){
                singleVersions[i] = fp.readInt();
            }
            //read deltaVersions
            size = fp.readInt();
            for(int i = 0;i<size;i++){
                int left = fp.readInt();
                int right = fp.readInt();
                deltaVersions.add(Pair.of(left,right));
            }

            //read multiVersions
            size = fp.readInt();
            for(int i = 0;i<size;i++){
                int ksize = fp.readInt();
                int []versions = new int[ksize];
                for(int j = 0 ;j<ksize;j++){
                    versions[j] = fp.readInt();
                }
                multiVersions.add(versions);
            }

            size = fp.readInt();

            for(int i = 0;i<size;i++){
                int left = fp.readInt();
                int right = fp.readInt();
                diffVersions.add(Pair.of(left,right));
            }

            //read sameVersions
            size = fp.readInt();
            for(int i = 0;i<size;i++){
                int ksize = fp.readInt();
                int []versions = new int[ksize];
                for(int j = 0 ;j<ksize;j++){
                    versions[j] = fp.readInt();
                }
                sameVersions.add(versions);
            }
            fp.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
