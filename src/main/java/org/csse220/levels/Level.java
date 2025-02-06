package org.csse220.levels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;


public class Level {
    private int numObjects;

    private Level(int numObjects){

        this.numObjects = numObjects;

    }

    public static Level loadLevel(String filename) throws FileNotFoundException,MissingDataException {

        Scanner sc = new Scanner(filename);
        String jsonString = "";
        while(sc.hasNextLine()){
            jsonString += sc.nextLine();
        }
        JsonReader reader = Json.createReader(new StringReader(jsonString));

        JsonObject jsonObject = reader.readObject();
        if(!jsonObject.containsKey("numObjects")){
            throw new MissingDataException();
        }
        int tempNumObjects = jsonObject.getInt("numObjects");

        return new Level(tempNumObjects);
    }

    public int getNumObjects() {
        return numObjects;
    }

    @Override
    public String toString() {
        return "Level[numObjects:" + numObjects+"]";
    }

    static class MissingDataException extends Exception {
        MissingDataException() {
            super("MISSING DATA!");
        }
    }

}
