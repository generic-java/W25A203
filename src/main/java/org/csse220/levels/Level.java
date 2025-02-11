package org.csse220.levels;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.characters.Drone;
import org.csse220.game_engine.characters.Enemy;
import org.csse220.game_engine.characters.PathEnemy;
import org.csse220.game_engine.game_objects.CuboidTerrain;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.math_utils.GamePose;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Scanner;


public class Level {
    private final int numObjects;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<CuboidTerrain> platforms = new ArrayList<>();

    private Level(int numObjects) {
        this.numObjects = numObjects;
        for (int i = 0; i < numObjects; i++) {
            gameObjects.add(new CuboidTerrain(new GamePose(), new Cuboid(new Point3d(100 * (Math.random() - 0.5), 100 * (Math.random() - 0.5), 0), 5, 5, 5, Math.random() > 0.5 ? Color.GREEN : Color.ORANGE)));
            //gameObjects.add(new GameObject(new GamePose(), null, new Cuboid(new Point3d(100 * (Math.random() - 0.5), 100 * (Math.random() - 0.5), 0), 5, 5, 5, Math.random() > 0.5 ? Color.GREEN : Color.ORANGE)));
        }
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public static ArrayList<Level> loadAll() {

        System.out.println("Please enter the name of the level file you would like to load.  Afterwards, enter another name or press / to quit the level loader.  Suggested level names are 'level_1.json' and 'level_2.json'.  'level_3.json' is a demonstration of how the program handles reading a file with invalid data.  Press the U and I keys to switch levels.\n");

        ArrayList<Level> levels = new ArrayList<>();

        String levelName;
        while (!(levelName = getLevelName()).equals("/")) {
            try {
                levels.add(loadLevel(levelName));
            } catch (MissingDataException e) {
                System.out.println("Level file isn't valid.  Please enter another file name.");
                return loadAll();
            } catch (IOException e) {
                System.out.println("Level file does not exist.  Please enter another file name.");
                return loadAll();
            }
        }

        return levels;
    }

    private static String getLevelName() {
        System.out.println("Enter the file name of the level:\n");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static Level loadLevel(String filename) throws IOException, MissingDataException {

        Scanner sc = new Scanner(new FileReader(FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/main/java/org/csse220/levels/" + filename));


        StringBuilder jsonString = new StringBuilder();
        while (sc.hasNextLine()) {
            jsonString.append(sc.nextLine());
        }
        JsonReader reader = Json.createReader(new StringReader(jsonString.toString()));

        JsonObject jsonObject = reader.readObject();
        if (!jsonObject.containsKey("numObjects") || !jsonObject.containsKey("enemies") || !jsonObject.containsKey("Name")) {
            throw new MissingDataException();
        }


        int tempNumObjects = jsonObject.getInt("numObjects");
        String tempName = jsonObject.getString("Name");
        JsonArray enemies = jsonObject.getJsonArray("enemies");
        Enemy[] tempEnemies = new Enemy[enemies.size()];
        for (int i = 0; i < enemies.size(); i++) {
            JsonObject currentJsonObj = enemies.getJsonObject(i);
            String type = currentJsonObj.getString("type");
            if (type.equals("drone")) {

                double poseX = Double.parseDouble(currentJsonObj.getString("poseX"));
                double poseY = Double.parseDouble(currentJsonObj.getString("poseY"));
                double poseZ = Double.parseDouble(currentJsonObj.getString("poseZ"));

                //REPLACE WITH CONSTRUCTOR
                tempEnemies[i] = new Drone(new GamePose(poseX, poseY, poseZ, 0));
            }
            if (type.equals("pathEnemy")) {

                double startX = Double.parseDouble(currentJsonObj.getString("startX"));
                double startY = Double.parseDouble(currentJsonObj.getString("startY"));
                double startZ = Double.parseDouble(currentJsonObj.getString("startZ"));

                double endX = Double.parseDouble(currentJsonObj.getString("endX"));
                double endY = Double.parseDouble(currentJsonObj.getString("endY"));
                double endZ = Double.parseDouble(currentJsonObj.getString("endZ"));

                //REPLACE WITH CONSTRUCTOR
                tempEnemies[i] = new PathEnemy(new GamePose(startX, startY, startZ, 0), new GamePose(endX, endY, endZ, 0));
            }

        }

        double portalX = Double.parseDouble(jsonObject.getString("portalPoseX"));
        double portalY = Double.parseDouble(jsonObject.getString("portalPoseY"));
        double portalZ = Double.parseDouble(jsonObject.getString("portalPoseZ"));

        //REPLACE WITH CONSTRUCTOR AFTER IMPLEMENTATION
        Portal p = new Portal();

        /*

        UNCOMMENT AFTER INPLEMENTING BONFIRE CLASS

        JsonArray bonfireFuel = jsonObject.getJsonArray("bonfireFuelPose");
        BonfireFuel[] fuel = new BonfireFuel[bonfireFuel.size()];
        for(int i = 0; i < bonfireFuelPose.size(); i++ ){
            JsonObject currentJsonObj = bonfireFuel.getJsonObject(i);
            double poseX = Double.parseDouble(currentJsonObj.getString("poseX"));
            double poseY = Double.parseDouble(currentJsonObj.getString("poseY"));
            double poseZ = Double.parseDouble(currentJsonObj.getString("poseZ"));

            //REPLACE WITH CONSTRUCTOR
            fuel[i] = new BonfireFuel();
        }
        */

        return new Level(tempNumObjects);
    }

    public int getNumObjects() {
        return numObjects;
    }

    @Override
    public String toString() {
        return "Level[numObjects:" + numObjects + "]";
    }

    public static class MissingDataException extends Exception {
        MissingDataException() {
            super("MISSING DATA!");
        }
    }
}