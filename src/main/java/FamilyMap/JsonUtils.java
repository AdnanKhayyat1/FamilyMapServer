package FamilyMap;

import Server.Serializer;
import com.google.gson.*;
import netscape.javascript.JSObject;


import java.io.*;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class JsonUtils {
    private List<String> fNames;
    private List<Location> locations;
    private List<String> mNames;
    private List<String> sNames;
    private Random randomGen;
    public JsonUtils() throws IOException {
        fNames = new ArrayList<String>();
        locations = new ArrayList<Location>();
        mNames = new ArrayList<String>();
        sNames = new ArrayList<String>();
        randomGen = new Random();
        readJSONs();
    }
    public void readJSONs() throws IOException {
        String fNamesPath = "json/fnames.json";
        String locationsPath = "json/locations.json";
        String mNamesPath = "json/mnames.json";
        String sNamesPath = "json/snames.json";
        fNames = readFile(fNamesPath);
        locations = readLocationFile(locationsPath);
        mNames = readFile(mNamesPath);
        sNames = readFile(sNamesPath);
    }
    public static List<String> readFile(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            out.append(line);
        }

        br.close();
        JsonObject obj = Serializer.deserialize(out.toString(), JsonObject.class);
        JsonArray jArray = (JsonArray)obj.get("data");
        ArrayList<String> list = new ArrayList<String>();
        if (jArray != null) {
            int len = jArray.size();
            for (int i=0;i<len;i++){
                list.add(jArray.get(i).toString().replaceAll("\"", ""));
            }
        }
        return list;
    }
    public List<Location> readLocationFile(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            out.append(line);
        }
        br.close();
        JsonObject obj = Serializer.deserialize(out.toString(), JsonObject.class);
        JsonArray jArray = (JsonArray)obj.get("data");
        ArrayList<Location> locations = new ArrayList<Location>();
        if (jArray != null) {
            int len = jArray.size();
            for (int i=0;i<len;i++){
                Location curr = Serializer.deserializeJsonElement(jArray.get(i), Location.class);
                locations.add(curr);
            }
        }
        return locations;
    }
    public String getRandomFName(){
        int index = randomGen.nextInt(fNames.size());
        return fNames.get(index);
    }
    public String getRandomMNames(){
        int index = randomGen.nextInt(mNames.size());
        return mNames.get(index);
    }
    public String getRandomSNames(){
        int index = randomGen.nextInt(sNames.size());
        return sNames.get(index);
    }
    public Location getRandomLocation(){
        int index = randomGen.nextInt(locations.size());
        System.out.println("");
        return locations.get(index);
    }



}
