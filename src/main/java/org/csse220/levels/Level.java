package org.csse220.levels;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;


public class Level {
    private int numEnemies;
    private String type;
    private int index;
    private String levelName;
    private int numObjects;

    private Level(String levelName, int numEnemies, int numObjects){
        this.levelName = levelName;
        this.numEnemies = numEnemies;
        this.numObjects = numObjects;

    }

    public static Level loadLevel(String filename) throws FileNotFoundException {

        Scanner sc = new Scanner(filename);
        String jsonString = "";
        while(sc.hasNextLine()){
            jsonString += sc.nextLine();
        }
        JsonReader reader = Json.createReader(new StringReader(jsonString));

        JsonObject jsonObject = reader.readObject();

        String tempName = jsonObject.getString("name");
        int tempNumEnemies = jsonObject.getInt("numEnemies");

        int tempNumObjects = jsonObject.getInt("numObjects");

        return new Level(tempName,tempNumEnemies,tempNumObjects);
    }

    public int getNumObjects() {
        return numObjects;
    }

}
