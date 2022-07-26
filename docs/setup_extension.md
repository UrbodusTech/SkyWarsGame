# Create an extension
Official example <a href="https://github.com/UrbodusTech/SkyWars/tree/release/example/KitsExtension">here</a>.

## Maven
Add SkyWars and Nukkit as dependencies
```xml
 <repositories>
    <repository>
        <id>opencollab-repo-snapshot</id>
        <url>https://repo.opencollab.dev/maven-snapshots/</url>
    </repository>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>


<dependencies>
<dependency>
    <groupId>com.github.UrbodusTech</groupId>
    <artifactId>SkyWars</artifactId>
    <version>release-a8ab750858-1</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>cn.nukkit</groupId>
    <artifactId>nukkit</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
</dependencies>
```

Extensions must contain extension.json inside resources
```json
{
  "name": "NAME",
  "version": "VERSION",
  "author": "AUTHOR",
  "api": 1.0,
  "bootClass": "MAIN CLASS"
}
```

<a href="https://github.com/UrbodusTech/SkyWars/blob/release/src/main/java/com/skywars/GameLoader.java#L24">API Current Version</a>

The main class must extend Extension and add the abstract functions.
```java
public class ExampleExtension extends Extension {

    @Override
    public void install() {
       
    }

    @Override
    public void uninstall() {

    }
}
```