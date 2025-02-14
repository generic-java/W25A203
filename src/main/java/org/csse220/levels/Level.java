package org.csse220.levels;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.characters.Drone;
import org.csse220.game_engine.characters.Enemy;
import org.csse220.game_engine.characters.PathEnemy;
import org.csse220.game_engine.game_objects.CuboidTerrain;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.math_utils.GamePose;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.*;


public class Level {

    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<CuboidTerrain> platforms = new ArrayList<>();

    private Level(List<CuboidTerrain> platforms, List<Enemy> enemies) {
        this.enemies.addAll(enemies);
        this.platforms.addAll(platforms);
    }

    public static ArrayList<Level> loadAll() {
        ArrayList<Level> levels = new ArrayList<>();
        File dir = new File(FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/main/java/org/csse220/levels/");

        try {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.getName().contains(".json")) {
                    try {
                        levels.add(loadLevel(file));
                    } catch (IOException e) {
                        System.err.println(e);
                    } catch (MissingDataException e) {
                        System.err.println(e);
                    }
                }
            }
        } catch (IllegalAccessError e) {
            System.out.println("Couldn't load levels");
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return levels;
    }

    private static String getLevelName() {
        System.out.println("Enter the file name of the level:\n");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static Level loadLevel(File file) throws IOException, MissingDataException {
        Scanner sc = new Scanner(new FileReader(file));

        StringBuilder jsonString = new StringBuilder();
        while (sc.hasNextLine()) {
            jsonString.append(sc.nextLine());
        }
        JsonReader reader = Json.createReader(new StringReader(jsonString.toString()));

        JsonObject jsonObject = reader.readObject();
        if (!jsonObject.containsKey("enemies") || !jsonObject.containsKey("name")) {
            throw new MissingDataException();
        }

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
        JsonArray platforms = jsonObject.getJsonArray("platforms");
        CuboidTerrain[] tempPlatforms = new CuboidTerrain[platforms.size()];
        for (int i = 0; i < platforms.size(); i++) {
            JsonObject currentJsonObj = platforms.getJsonObject(i);


            double width = Double.parseDouble(currentJsonObj.getString("width"));
            double height = Double.parseDouble(currentJsonObj.getString("height"));
            double depth = Double.parseDouble(currentJsonObj.getString("depth"));

            double poseX = Double.parseDouble(currentJsonObj.getString("poseX"));
            double poseY = Double.parseDouble(currentJsonObj.getString("poseY"));
            double poseZ = Double.parseDouble(currentJsonObj.getString("poseZ"));

         /*   int red;
            int green;
            int blue;
            JsonArray color = currentJsonObj.getJsonArray("color");
            for (JsonValue value : color) {

            }
*/
            GamePose temp = new GamePose(poseX, poseY, poseZ, 0);

            //REPLACE WITH CONSTRUCTOR
            tempPlatforms[i] = new CuboidTerrain(new Cuboid(temp.toPoint3d(), width, height, depth, new Color(163, 52, 255)));
        }
        //tempPlatforms[0] = new CuboidTerrain(new Cuboid(new Point3d(0, 0, -15), 100, 10, 100, Color.PINK));
        double portalX = Double.parseDouble(jsonObject.getString("portalPoseX"));
        double portalY = Double.parseDouble(jsonObject.getString("portalPoseY"));
        double portalZ = Double.parseDouble(jsonObject.getString("portalPoseZ"));

        //REPLACE WITH CONSTRUCTOR AFTER IMPLEMENTATION
        Portal p = new Portal();

        /*

        UNCOMMENT AFTER IMPLEMENTING BONFIRE CLASS

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

        return new Level(Arrays.asList(tempPlatforms), Arrays.asList(tempEnemies));
    }

    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    public ArrayList<CuboidTerrain> getPlatforms() {
        return this.platforms;
    }


    public static class MissingDataException extends Exception {
        MissingDataException() {
            super("MISSING DATA!");
        }
    }
}