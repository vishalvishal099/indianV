package com.oracle.babylon.Utils.setup.dataStore;

import com.google.gson.*;
import io.restassured.mapper.ObjectMapper;
import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Class to handle JSON operations while reading from JSON files
 * Author : susgopal
 */
public class DataSetup {
    /**
     * Converts JSON file data to a Map of Map
     *
     * @param filePath path of the JSON file present in the main/resources package
     * @return Map of Map containing the JSON data
     * @throws IOException
     * @throws ParseException
     */
    public Map<String, Map<String, String>> loadJsonDataToMap(String filePath) {
        /** 1.Read the file and store it in an Object
         * 2. Convert Object to JSON Object
         * 3. Retrieve the keys in the JSON Object
         * 4. If JSON key's value is a JSON, then store the map as the value
         * 5. If JSON key's value is a String, then store the key and the value under the key firstlevel. Can be changed accordingly.
         */
        try {
            Object obj = new JSONParser().parse(new FileReader(filePath));
            Map<String, Map<String, String>> mapOfMap = new HashMap<>();

            JSONObject completeJson = (JSONObject) obj;
            Set<String> keySet = new HashSet<String>();
            keySet = completeJson.keySet();

            for (String key : keySet) {
                if (completeJson.get(key).getClass().equals(JSONObject.class)) {
                    mapOfMap.put(key, (HashMap) completeJson.get(key));
                } else if (completeJson.get(key).getClass().equals(String.class)) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(key, completeJson.get(key).toString());
                    mapOfMap.put("firstLevel", map);
                }
            }
            return mapOfMap;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * Method to change the json file when it is a map of map
     *
     * @param parentKey
     * @param generatedMapOfMap
     * @param filePath
     */
    public void convertMapOfMapAndWrite(String parentKey, Map<String, Map<String, String>> generatedMapOfMap, String filePath) {
        try {
            /**1.Read the json from the file
             * 2.Convert the data to jsonObject
             * 3.Fetch the json data to modify them by using map methods
             * 4.Replace the values
             * 5.Write the values to the file
             *
             */
            Object obj = new JSONParser().parse(new FileReader(filePath));
            JSONObject fileJson = (JSONObject) obj;
            Set<String> fakerKeys = generatedMapOfMap.keySet();
            Map<String, String> fakerMap = new Hashtable<>();
            Iterator<String> fakerKeysItr = fakerKeys.iterator();

            while (fakerKeysItr.hasNext()) {
                if (fakerKeysItr.next().equals(parentKey)) {
                    JSONObject originalJsonObj = (JSONObject) fileJson.get(parentKey);
                    fakerMap = generatedMapOfMap.get(parentKey);
                    Set<String> childKeys = fakerMap.keySet();

                    Iterator<String> childKeysItr = childKeys.iterator();
                    while (childKeysItr.hasNext()) {
                        String key = childKeysItr.next();
                        originalJsonObj.put(key, fakerMap.get(key));

                    }
                    fileJson.put(parentKey, originalJsonObj);

                }
                //Create the json in readable pretty print format
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(fileJson.toJSONString());
                String prettyJsonString = gson.toJson(je);

                //Write the complete string to the file
                PrintWriter pw = new PrintWriter(filePath);
                pw.write(prettyJsonString);
                pw.flush();
                pw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void updateOrWriteTofile(String parentKey, Map<String, Map<String, String>> generatedMapOfMap, String filePath) {
        try {
            /**1.Read the json from the file
             * 2.Convert the data to jsonObject
             * 3.Fetch the json data to modify them by using map methods
             * 4.Replace the values
             * 5.Write the values to the file
             *
             */
            Object obj = new JSONParser().parse(new FileReader(filePath));
            JSONObject fileJson = (JSONObject) obj;

            Iterator<String> fileExistingDocuments = fileJson.keySet().iterator();
            Set<String> fakerKeys = generatedMapOfMap.keySet();
            Map<String, String> fakerMap = new Hashtable<>();
            Iterator<String> fakerKeysItr = fakerKeys.iterator();
            boolean isExisting = false;
            while (fileExistingDocuments.hasNext()) {
                if (fileExistingDocuments.next().equals(parentKey)) {
                    JSONObject originalJsonObj = (JSONObject) fileJson.get(parentKey);
                    fakerMap = generatedMapOfMap.get(parentKey);
                    Set<String> childKeys = fakerMap.keySet();

                    Iterator<String> childKeysItr = childKeys.iterator();
                    while (childKeysItr.hasNext()) {
                        String key = childKeysItr.next();
                        originalJsonObj.put(key, fakerMap.get(key));
                    }
                    fileJson.put(parentKey, originalJsonObj);
                    isExisting =true;
                }
            }
            if(isExisting ==false){
                fileJson.put(parentKey, generatedMapOfMap.get(parentKey));
            }
            //Create the json in readable pretty print format
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(fileJson.toJSONString());
            String prettyJsonString = gson.toJson(je);

            //Write the complete string to the file
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyJsonString);
            pw.flush();
            pw.close();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

