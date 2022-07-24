package com.skywars.extension;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString(exclude = { "bootClass" })
public class ExtensionInfo {

    private final String name;
    private final String version;
    private final String author;
    private final Double api;
    private final String bootClass;

}
