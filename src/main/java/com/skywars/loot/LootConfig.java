package com.skywars.loot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LootConfig {

    String name() default "loot:unknown";

    LootType type() default LootType.ISLAND;
}
