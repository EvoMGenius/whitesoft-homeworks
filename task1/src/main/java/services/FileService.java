package services;

import models.Employee;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {

    public JSONArray readToJsonArray(File file)  {
        JSONArray jsonArray = null;
        try (FileReader fr = new FileReader(file)){
            JSONParser parser = new JSONParser();
            jsonArray = (JSONArray) parser.parse(fr);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public List<String> readToListOfString(File file){
        List<String> readedInfo = new ArrayList<>();
        try(Scanner sc = new Scanner(file)){
            sc.useDelimiter("((\\n\\r)|(\\r\\n)){2}|(\\r){2}|(\\n){2}");
            while(sc.hasNext()){
                readedInfo.add(sc.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readedInfo;
    }
}
