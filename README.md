# 🌐 SkyWars 🌐
<img src="https://i.imgur.com/qePJvtU.png">

## 🧪 Goal
Sometimes using a public game plugin is somewhat complicated for different reasons such as: the method of entering the game is not the same as that of the other games, no compatibility with the main permission's handler, using other plugins to add in-game cosmetics can sometimes be chaos, etc.</br>

The goal of this plugin is not focused on offering a plugin with all the features that a server/network wants, this plugin offers the systems and gameplay of a SkyWars, yes, but it also offers an extension system that allows servers/networks create own features and integrate them directly into the game while maintaining compatibility and synchronization. Cool! Imagine creating an extension that adds Custom Cages to the plugin or even better create an extension that adds Cages, Kits, Death Effects, etc.

## 👥 Authors
- JoseLuisHD
- Brayan Roman

## 🌎 Official Extensions
| name |   author    | link                                                                                          |
|:-----|:-----------:|:----------------------------------------------------------------------------------------------|
| Kits | UrbodusTech | <a href="https://github.com/UrbodusTech/SkyWars/tree/release/example/KitsExtension">Click</a> |

NOTE: If you want to add your extension to the list remember to open a pull request with the changes in README.md adding your extension to the table.

## 🛡 Build
```
mvn clean package
```

## 🛠 Contributions
We will continue to maintain the game adding what the community asks for and fixing bugs. <br>

All contributions are welcome, to facilitate the review process 3 things will be checked:<br>
- Don't use the main (release) branch, use another branch, so we can test the changes.
- Add a description of the changes/bug fixes.
- If there are api changes remember to increase the version in GameLoader.java.

NOTE: Remember to review the pull request comments, we may add comments if necessary.

# 🏹 Wiki
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/config_file.md">Explanation of the config.yml file</a>
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/setup_map.md">Set up a map</a>
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/install_lang_file.md">Install language support</a>
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/extensions.md">Explanation of extensions</a>
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/setup_extension.md">Create an extension</a>
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/match_commands.md">Match Commands<a href="">
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/match_events.md">Match Events</a>
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/player_events.md">Player Events</a>
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/session_events.md">Session Events</a>
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/message_lang.md">Add messages to a lang file from an extension</a>
- <a href="https://github.com/UrbodusTech/SkyWars/blob/release/docs/chest_loot.md">Chest Loot</a>