package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Converter {
    
    /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    private static final String COL_HEADER_NAME = "ColHeadings";
    private static final String ROW_HEADER_NAME = "ProdNums";
    private static final String DATA_ROWS_NAME = "Data";
    
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String result = "{}"; // default return value; replace later!
        
        try {
        
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            String[] currCSV_Row = iterator.next();
            
            JsonArray JA_DataArrays = new JsonArray();
            JsonArray JA_RowHeaders = new JsonArray();
            
            JsonArray colHeadings = stringArrayToJsonArray(currCSV_Row);
            
            jsonOutputObject.put(COL_HEADER_NAME, colHeadings);
            
        while(iterator.hasNext()) {
            currCSV_Row = iterator.next();
            JA_RowHeaders.add(currCSV_Row[0]);
            JsonArray JA_DataRow = new JsonArray();
            
            for(int i = 1; i < currCSV_Row.length; ++i){
                if (i == colHeadings.indexOf("Episode") || i == colHeadings.indexOf("Season")){
                    JA_DataRow.add(Integer.parseInt(currCSV_Row[i]));
                } 
                else{
                    JA_DataRow.add(currCSV_Row[i]);
                }
            }
            JA_DataArrays.add(JA_DataRow);
        }   
            
            jsonOutputObject.put(ROW_HEADER_NAME, JA_RowHeaders);
            jsonOutputObject.put(DATA_ROWS_NAME, JA_DataArrays);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return jsonOutputObject.toJson().trim();
        
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        
        String result = ""; // default return value; replace later!
        
        try {
            
            JsonObject originJsonObject = (JsonObject)Jsoner.deserialize(jsonString);
            List<String[]> parsedRowArrays = new ArrayList<>();
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
            List<String> colHeadings = (List)originJsonObject.get("ColHeadings");
            csvWriter.writeNext( colHeadings.toArray(new String[0]));
            
            JsonArray JA_DataArrays = new JsonArray();
            JsonArray JA_RowHeaders = new JsonArray();
            
            for (int i = 0; i < JA_RowHeaders.size(); ++i){
                if (j == episodeIndex){
                    rowdata.add(df.format(Integer.parseInt(data.get(j).toString())));
                }
                else{
                    rowdata.add(data.get(j).toString());
                }
            }
            
            csvWriter.writeAll(parasedRowArrays);
            result = writer.toString().trim();
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
        
    }
    private static JsonArray stringArrayToJsonArray(String[] SA){
        JsonArray JA = new JsonArray();
        for(int i = 0; i < SA.length; ++i){
            JA.add(SA[i]);
        }
        return JA;
    }
    
}
