package com.skywars.extension;

import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.TextFormat;
import com.skywars.GameLoader;
import com.skywars.utils.ResourceUtils;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;

@Getter
public class ExtensionManager {

    private final Map<String, Extension> extensions;
    private final Map<String, ExtensionLoader> loaders;

    public ExtensionManager() {
        extensions = new ConcurrentHashMap<>();
        loaders = new ConcurrentHashMap<>();
    }

    public void init() {
        try {
            Files.walk(GameLoader.getInstance().getDataFolder().toPath().resolve("extensions"))
                    .filter(ResourceUtils::isJarFile)
                    .forEach(this::loadExtension);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private void loadExtension(Path path) {
        try {
            ExtensionLoader loader = getLoader(path.toFile());
            if (loader != null && addExtensionLoader(loader)) {
                loader.load(this);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private ExtensionLoader getLoader(File file) throws IOException, ClassNotFoundException {
        PluginLogger logger = GameLoader.getInstance().getLogger();

        ExtensionInfo info;

        try(JarFile jar = new JarFile(file); InputStream stream = jar.getInputStream(jar.getEntry("extension.json"))) {
            info = GameLoader.JSON.fromJson(new InputStreamReader(stream), ExtensionInfo.class);
        }

        Class extensionClass;
        try (URLClassLoader loader = new URLClassLoader(new URL[] { file.toURI().toURL() }, ExtensionManager.class.getClassLoader()) ) {
            extensionClass = loader.loadClass(info.getBootClass());

            if (extensionClass == null) {
                logger.error("[SkyWars-Extension] Could not load " + info.getName() + " main class not found: " + info.getBootClass());

                return null;
            }

            if (!Extension.class.isAssignableFrom(extensionClass)) {
                logger.error("[SkyWars-Extension] " + extensionClass.getName() + " must extend to Extension.java");

                return null;
            }
        }

        if (!Objects.equals(info.getApi(), GameLoader.API_VERSION)) {
            logger.error("[SkyWars-Extension] incompatible api version: " + info.getName());

            return null;
        }

        return new ExtensionLoader(extensionClass.asSubclass(Extension.class), info, file);
    }

    public void addExtension(Extension extension) {
        if (!extension.isInitialized()) {
            return;
        }

        if (extensions.containsKey(extension.toString())) {
            return;
        }

        extensions.put(extension.toString(), extension);
        extension.setEnabled(true);
        extension.getLogger().info(TextFormat.colorize("&aInstalled!"));
    }

    private boolean addExtensionLoader(ExtensionLoader loader) {
        return loaders.putIfAbsent(loader.toString(), loader) == null;
    }

    public void close() {
        for (Extension extension : extensions.values()) {
            extension.setEnabled(false);
            extension.getLogger().info(TextFormat.colorize("&cUnInstalled!"));
        }

        extensions.clear();
        loaders.clear();
    }
}
