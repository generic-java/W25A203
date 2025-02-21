package org.csse220.levels;

import org.csse220.game_engine.characters.*;
import org.csse220.game_engine.game_objects.*;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.math.GamePose;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Level {

    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<CuboidTerrain> platforms = new ArrayList<>();
    private final GamePose playerStartPose;
    private final Bonfire bonfire;
    private final ArrayList<BonfireFuel> bonfireFuels = new ArrayList<>();
    private final ArrayList<Trampoline> trampolines = new ArrayList<>();
    private final ArrayList<GrassBlock> grassBlocks = new ArrayList<>();

    private Level(List<CuboidTerrain> platforms, List<Enemy> enemies, GamePose playerStartPose, List<BonfireFuel> bonfireFuels, Bonfire bonfire, List<Trampoline> trampolines, List<GrassBlock> grassBlocks) {
        this.enemies.addAll(enemies);
        this.platforms.addAll(platforms);
        this.playerStartPose = playerStartPose;
        this.bonfireFuels.addAll(bonfireFuels);
        this.bonfire = bonfire;
        this.trampolines.addAll(trampolines);
        this.grassBlocks.addAll(grassBlocks);
    }

    public static ArrayList<Level> loadAll() {
        ArrayList<Level> levels = new ArrayList<>();
        File dir = new File(FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/main/java/org/csse220/levels/");
        List<File> files = getFiles(dir);

        try {
            for (File file : files) {
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

    private static List<File> getFiles(File dir) {
        List<File> files = new ArrayList<>(Arrays.asList(Objects.requireNonNull(dir.listFiles())));
        files.removeIf((file) -> !file.getName().contains(".json"));
        files.sort((first, second) -> {
            int firstLevelNumber = 1;
            int secondLevelNumber = 0;
            Pattern pattern = Pattern.compile(".*_([0-9])\\.json", Pattern.CASE_INSENSITIVE);
            Matcher firstMatcher = pattern.matcher(first.getName());
            if (firstMatcher.find()) {
                firstLevelNumber = Integer.parseInt(firstMatcher.group(1));
            }
            Matcher secondMatcher = pattern.matcher(second.getName());
            if (secondMatcher.find()) {
                secondLevelNumber = Integer.parseInt(secondMatcher.group(1));
            }
            return firstLevelNumber - secondLevelNumber;
        });
        return files;
    }

    public static Level loadLevel(File file) throws IOException, MissingDataException {
        Scanner sc = new Scanner(new FileReader(file));

        StringBuilder jsonString = new StringBuilder();
        while (sc.hasNextLine()) {
            jsonString.append(sc.nextLine());
        }
        JsonReader reader = Json.createReader(new StringReader(jsonString.toString()));

        JsonObject jsonObject = reader.readObject();

        double bonfireX = Double.parseDouble(jsonObject.getString("bonfireX"));
        double bonfireY = Double.parseDouble(jsonObject.getString("bonfireY"));
        double bonfireZ = Double.parseDouble(jsonObject.getString("bonfireZ"));
        Bonfire fire = new Bonfire(new GamePose(bonfireX, bonfireY, bonfireZ, 0));

        JsonArray enemies = jsonObject.getJsonArray("enemies");
        Enemy[] tempEnemies = new Enemy[enemies.size()];
        for (int i = 0; i < enemies.size(); i++) {
            JsonObject currentJsonObj = enemies.getJsonObject(i);
            String type = currentJsonObj.getString("type");
            if (type.equals("drone")) {

                double poseX = Double.parseDouble(currentJsonObj.getString("poseX"));
                double poseY = Double.parseDouble(currentJsonObj.getString("poseY"));
                double poseZ = Double.parseDouble(currentJsonObj.getString("poseZ"));
                
                tempEnemies[i] = new PaperAirplane(new GamePose(poseX, poseY, poseZ, 0));
            }
            if (type.equals("pathEnemy")) {

                double startX = Double.parseDouble(currentJsonObj.getString("startX"));
                double startY = Double.parseDouble(currentJsonObj.getString("startY"));
                double startZ = Double.parseDouble(currentJsonObj.getString("startZ"));

                double endX = Double.parseDouble(currentJsonObj.getString("endX"));
                double endY = Double.parseDouble(currentJsonObj.getString("endY"));
                double endZ = Double.parseDouble(currentJsonObj.getString("endZ"));

                tempEnemies[i] = new PathEnemy(new GamePose(startX, startY, startZ, 0), new GamePose(endX, endY, endZ, 0));
            }
            if (type.equals("lava")) {
                double poseX = Double.parseDouble(currentJsonObj.getString("poseX"));
                double poseY = Double.parseDouble(currentJsonObj.getString("poseY"));
                double poseZ = Double.parseDouble(currentJsonObj.getString("poseZ"));

                double width = Double.parseDouble(currentJsonObj.getString("width"));
                double height = Double.parseDouble(currentJsonObj.getString("height"));
                double depth = Double.parseDouble(currentJsonObj.getString("depth"));

                tempEnemies[i] = new Lava(new GamePose(poseX, poseY, poseZ, 0), width, height, depth);
            }

            if (type.equals("spike")) {

                double poseX = Double.parseDouble(currentJsonObj.getString("poseX"));
                double poseY = Double.parseDouble(currentJsonObj.getString("poseY"));
                double poseZ = Double.parseDouble(currentJsonObj.getString("poseZ"));

                tempEnemies[i] = new Spike(new GamePose(poseX, poseY, poseZ, 0));
            }

        }

        JsonArray touchGrass = jsonObject.getJsonArray("grassBlocks");
        GrassBlock[] grassBlocks = new GrassBlock[touchGrass.size()];
        for (int i = 0; i < touchGrass.size(); i++) {
            JsonObject currentJsonObj = touchGrass.getJsonObject(i);
            double poseX = Double.parseDouble(currentJsonObj.getString("poseX"));
            double poseY = Double.parseDouble(currentJsonObj.getString("poseY"));
            double poseZ = Double.parseDouble(currentJsonObj.getString("poseZ"));

            double width = Double.parseDouble(currentJsonObj.getString("width"));
            double height = Double.parseDouble(currentJsonObj.getString("height"));
            double depth = Double.parseDouble(currentJsonObj.getString("depth"));

            grassBlocks[i] = new GrassBlock(new GamePose(poseX, poseY, poseZ, 0), width, height, depth);
        }

        JsonArray tramps = jsonObject.getJsonArray("trampolines");
        Trampoline[] trampolines = new Trampoline[tramps.size()];
        for (int i = 0; i < tramps.size(); i++) {
            JsonObject currentJsonObj = tramps.getJsonObject(i);


            double width = Double.parseDouble(currentJsonObj.getString("width"));
            double height = Double.parseDouble(currentJsonObj.getString("height"));
            double depth = Double.parseDouble(currentJsonObj.getString("depth"));

            double poseX = Double.parseDouble(currentJsonObj.getString("poseX"));
            double poseY = Double.parseDouble(currentJsonObj.getString("poseY"));
            double poseZ = Double.parseDouble(currentJsonObj.getString("poseZ"));

            trampolines[i] = new Trampoline(new GamePose(poseX, poseY, poseZ, 0), width, height, depth);
        }


        JsonArray platforms = jsonObject.getJsonArray("platforms");
        CuboidTerrain[] tempPlatforms = new CuboidTerrain[0];
        if (platforms != null) {
            tempPlatforms = new CuboidTerrain[platforms.size()];
            for (int i = 0; i < platforms.size(); i++) {
                JsonObject currentJsonObj = platforms.getJsonObject(i);


                double width = Double.parseDouble(currentJsonObj.getString("width"));
                double height = Double.parseDouble(currentJsonObj.getString("height"));
                double depth = Double.parseDouble(currentJsonObj.getString("depth"));

                double poseX = Double.parseDouble(currentJsonObj.getString("poseX"));
                double poseY = Double.parseDouble(currentJsonObj.getString("poseY"));
                double poseZ = Double.parseDouble(currentJsonObj.getString("poseZ"));

                GamePose platformPose = new GamePose(poseX, poseY, poseZ, 0);

                tempPlatforms[i] = new CuboidTerrain(new Cuboid(platformPose, width, height, depth, new Color(90, 250, 90)));
            }
        }


        double playerPoseX = Double.parseDouble(jsonObject.getString("playerPoseX"));
        double playerPoseY = Double.parseDouble(jsonObject.getString("playerPoseY"));
        double playerPoseZ = Double.parseDouble(jsonObject.getString("playerPoseZ"));
        GamePose playerStartPose = new GamePose(playerPoseX, playerPoseY, playerPoseZ, 0);


        JsonArray bonfireFuelPoses = jsonObject.getJsonArray("bonfireFuelPoses");
        BonfireFuel[] fuel = new BonfireFuel[bonfireFuelPoses.size()];
        for (int i = 0; i < bonfireFuelPoses.size(); i++) {
            JsonObject currentJsonObj = bonfireFuelPoses.getJsonObject(i);
            double poseX = Double.parseDouble(currentJsonObj.getString("poseX"));
            double poseY = Double.parseDouble(currentJsonObj.getString("poseY"));
            double poseZ = Double.parseDouble(currentJsonObj.getString("poseZ"));

            fuel[i] = new BonfireFuel(new GamePose(poseX, poseY, poseZ, 0));
        }


        return new Level(Arrays.asList(tempPlatforms), Arrays.asList(tempEnemies), playerStartPose, Arrays.asList(fuel), fire, Arrays.asList(trampolines), Arrays.asList(grassBlocks));
    }

    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    public ArrayList<CuboidTerrain> getPlatforms() {
        return this.platforms;
    }

    public ArrayList<BonfireFuel> getBonfireFuels() {
        return this.bonfireFuels;
    }

    public ArrayList<Trampoline> getTrampolines() {
        return this.trampolines;
    }

    public Bonfire getBonfire() {
        return this.bonfire;
    }

    public ArrayList<GrassBlock> getGrassBlocks() {
        return grassBlocks;
    }

    public int getNumBonfireFuels() {
        return this.bonfireFuels.size();
    }

    public GamePose getPlayerStartPose() {
        return this.playerStartPose;
    }


    public static class MissingDataException extends Exception {
        MissingDataException() {
            super("MISSING DATA!");
        }
    }


}