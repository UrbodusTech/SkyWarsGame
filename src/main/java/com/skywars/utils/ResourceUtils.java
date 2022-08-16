package com.skywars.utils;

import cn.nukkit.Server;
import com.skywars.GameLoader;
import com.skywars.lang.LangManager;
import com.skywars.match.Match;
import com.skywars.match.MatchData;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ResourceUtils {

    public static boolean isJarFile(Path path) {
        return Files.isRegularFile(path) && path.toString().endsWith(".jar");
    }

    public static void createDefaultDirectories() {
        GameLoader loader = GameLoader.getInstance();
        Path dataPath = loader.getDataFolder().toPath();
        File queueDirectory = new File(Server.getInstance().getDataPath() + "/worlds/sw_queue");

        try {
            Files.createDirectories(dataPath.resolve("extensions"));
            Files.createDirectories(dataPath.resolve("language"));
            Files.createDirectories(dataPath.resolve("maps"));
            Files.createDirectories(dataPath.resolve("configs"));
            Files.createDirectories(dataPath.resolve("ext-dat"));

            if (queueDirectory.exists()) {
                FileUtils.deleteDirectory(queueDirectory);
            }

            boolean created = queueDirectory.mkdir();
            if (created) {
                loader.getLogger().info("Queue world directory is ready!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveDefaultLangFiles() {
        for (String langFileName : LangManager.SUPPORTED_LANGUAGES) {
            File langFile = new File(GameLoader.getInstance().getDataFolder() + "/language/" + langFileName + ".json");
            if (!langFile.exists()) {
                try {
                    FileUtils.copyURLToFile(
                            Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("lang/" + langFileName + ".json")),
                            langFile
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public static MatchData[] readValidMatchData() {
        List<MatchData> entries = new ArrayList<>();
        File configFiles = new File(GameLoader.getInstance().getDataFolder() + "/configs");
        for (String file : Objects.requireNonNull(configFiles.list())) {
            if (file.equals("..") || file.equals(".")) {
                continue;
            }

            if (!file.endsWith(".json")) {
                continue;
            }

            try {
                String content = FileUtils.readFileToString(configFiles.toPath().resolve(file).toFile(), StandardCharsets.UTF_8);
                MatchData data = GameLoader.JSON.fromJson(content, MatchData.class);

                File map = new File(GameLoader.getInstance().getDataFolder() + "/maps/" + data.getMapDirName());
                if (!map.exists() || !map.isDirectory()) {
                    continue;
                }

                entries.add(data);
            } catch (Exception exception) {
                //ignore
            }
        }

        return entries.toArray(new MatchData[0]);
    }

    public static void releaseMatchMap(@NonNull Match match) {
        GameLoader loader = GameLoader.getInstance();
        File map = new File(loader.getDataFolder() + "/maps/" + match.getData().getMapDirName());

        try {
            File dest = new File(Server.getInstance().getDataPath() + "/worlds/sw_queue/" + match.getUuid().toString());
            if (dest.exists()) {
                loader.getLogger().error("The map [sw_queue/" + match.getUuid().toString() + "] already exits!");

                return;
            }

            FileUtils.copyDirectory(map, dest);
        } catch (IOException exception) {
            loader.getLogger().error("Could not setup match[" + match.getUuid() + "]: " + exception.getMessage());
            loader.getMatchManager().deleteMatch(match.getUuid());
        }
    }

    public static void deleteMatchMap(@NonNull Match match) {
        File map = new File(Server.getInstance().getDataPath() + "/worlds/sw_queue/" + match.getUuid().toString());
        try {
            FileUtils.deleteDirectory(map);
        } catch (IOException e) {
            GameLoader.getInstance().getLogger().error("Error removing match map [" + match.getUuid() + "]: " + e.getMessage());
        }
    }
}
