package com.skywars.lang;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.skywars.GameLoader;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Getter
public class LangFile {

    private final String id;
    private final Map<String, String> messages;

    public LangFile(String id) {
        this.id = id;
        messages = read();
    }

    private Map<String, String> read() {
        GameLoader loader = GameLoader.getInstance();
        File file = new File(loader.getDataFolder() + "/language/" + id + ".json");
        if (!file.exists()) {
            loader.getLogger().error("LangFile not found: " + file.getAbsoluteFile());

            return new HashMap<>();
        }

        try {
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            JsonObject json = GameLoader.JSON.fromJson(content, JsonObject.class);
            if (json.isJsonNull()) {
                loader.getLogger().error("Error decoding [" + file.getAbsoluteFile() + "] using empty LangFile...");

                return new HashMap<>();
            }

            Map<String, String> messages = new HashMap<>();
            for (Map.Entry<String, JsonElement> message : json.entrySet()) {
                messages.put(message.getKey(), message.getValue().getAsString());
            }

            return messages;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage(String id) {
       return messages.getOrDefault(id, id);
    }

    /*
     * With this function you can add messages even if the lang file is already loaded, use it to add messages from the extensions.
     */
    public void appendMessage(String id, String message) {
        if (messages.containsKey(id)) {
            GameLoader.getInstance().getLogger().warning("Skipping message[" + id + "] because it already exists");

            return;
        }

        messages.put(id, message);
    }

    public void appendMessage(Map<String, String> content) {
        content.forEach(this::appendMessage);
    }

    public void appendMessage(JsonObject json) {
        for (Map.Entry<String, JsonElement> message : json.entrySet()) {
            messages.put(message.getKey(), message.getValue().getAsString());
        }
    }
}
