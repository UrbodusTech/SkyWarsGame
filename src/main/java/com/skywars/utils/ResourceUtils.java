package com.skywars.utils;

import cn.nukkit.Server;
import com.skywars.GameLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public static void saveDefaultLangFile() {
        File langFile = new File(GameLoader.getInstance().getDataFolder() + "/language/en_US.json");
        if (!langFile.exists()) {
            try {
                FileUtils.copyURLToFile(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("en_US.json")), langFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
