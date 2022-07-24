package com.skywars.lang;

import com.google.gson.JsonObject;
import com.skywars.GameLoader;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Getter
public class LangFile {

    private final String id;
    private final JsonObject messages;

    public LangFile(String id) {
        this.id = id;
        messages = read();
    }

    private JsonObject read() {
        GameLoader loader = GameLoader.getInstance();
        File file = new File(loader.getDataFolder() + "/language/" + id + ".json");
        if (!file.exists()) {
            loader.getLogger().error("LangFile not found: " + file.getAbsoluteFile());

            return new JsonObject();
        }

        try {
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            JsonObject json = GameLoader.JSON.fromJson(content, JsonObject.class);
            if (json.isJsonNull()) {
                loader.getLogger().error("Error decoding [" + file.getAbsoluteFile() + "] using empty LangFile...");

                return new JsonObject();
            }

            return json;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage(String id) {
        if (!messages.has(id)) {
            return id;
        }

        return messages.get(id).getAsString();
    }

    public void appendMessage(String id, String message) {
        if (messages.has(id)) {
            GameLoader.getInstance().getLogger().warning("Skipping message[" + id + "] because it already exists");

            return;
        }

        messages.addProperty(id, message);
    }

    public void appendMessage(Map<String, String> content) {
        content.forEach(this::appendMessage);
    }
}
