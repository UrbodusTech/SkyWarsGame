package com.skywars.extension;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

@Getter
@NonNull
@AllArgsConstructor
public class ExtensionLoader {

    private final Class<? extends Extension> bootClass;
    private final ExtensionInfo extensionInfo;
    private final File file;

    public void load(ExtensionManager manager) {
        try {
            Extension extension = bootClass.getConstructor().newInstance();
            extension.init(file, extensionInfo, manager);
            manager.addExtension(extension);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return extensionInfo.getName();
    }
}
