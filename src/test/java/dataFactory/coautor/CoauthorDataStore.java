package dataFactory.coautor;


import dataFactory.dataConfig.DataStore;
import dataFactory.sampleGraph.SampleGraph;
import dataFactory.operation.VersionGraphStore;
import org.apache.commons.lang3.tuple.Pair;


import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;


public class CoauthorDataStore implements DataStore {
    //String basepath = System.getProperty("user.dir")+"\\Coauthor";
    //F:\ideaCode\SciDG\Coauthor
    String basepath = "F:\\ideaCode\\SciDG\\Coauthor";
    String fileName = "filelist.txt";
    String label = "writer";
    String rtype = "coauthor";

    private String[] readfileList() {
        File file = new File(basepath, fileName);
        String [] names = null;
        try {
            RandomAccessFile fp = new RandomAccessFile(file, "r");
            ArrayList<String> fileNames = new ArrayList<>();
            String str;
            while((str = fp.readLine())!=null){
                fileNames.add(str);
            }
            names = new String[fileNames.size()];
            fileNames.toArray(names);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return names;
    }

    public void storeVersionGraph(VersionGraphStore graphDb){
        String []names = readfileList();
        graphDb.begin();
        for(int i =0;i<names.length;i++) {
            String fname = names[i];
            int version = new Integer(fname.substring(0, 4));
            SampleGraph tempGraph = new SampleGraph();
            File file = new File(basepath, fname);
            try {
                RandomAccessFile fp = new RandomAccessFile(file, "r");
                String str;
                while ((str = fp.readLine()) != null) {
                    String[] pairs = str.split("\t");
                    int id1 = new Integer(pairs[0]);
                    int id2 = new Integer(pairs[1]);
                    tempGraph.addNode(id1, this.label, new HashMap<>());
                    tempGraph.addNode(id2, this.label, new HashMap<>());
                    if (id1 > id2) {
                        tempGraph.addRel(Pair.of(id2, id1), rtype, new HashMap<>());
                    }
                    if (id1 == id2) {

                    }
                    if (id1 < id2) {
                        tempGraph.addRel(Pair.of(id1, id2), rtype, new HashMap<>());
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            graphDb.storeGraph(tempGraph,version);
        }
        graphDb.finish();
    }

}
