# Install language support

SkyWars was designed to execute most of the texts involved in the game in a translation system using lang files by default the plugin only brings English but more languages can be added.

## Create lang file
The lang files are json files that contain the messages and formats identified by a key, the file name must be the code of the language that Minecraft recognizes like en_US or es_MX. The json files must use the same keys as the en_US.json file as that way the plugin can recognize the message.<br>

Example: <a href="https://github.com/UrbodusTech/SkyWars/blob/release/src/main/resources/en_US.json">en_US.json</a>
```json
{
  "EMPTY_MATCH_LIST": "&cThere are no games available.",
  "CONNECTING_MATCH": "&eConnecting to the match: &b{0}"
}
```

## Formats
- <b>Colors</b>: In the language files you can indicate the colors using the symbol & example &1 or &a
- <b>Args</b>: The args are replaced by the plugin at the time the message is sent to the player, to set an arg use {(arg number)} eg {0} or {1}

## Enable
The plugin has the ability to enable the lang files that meet the requirements, so that the plugin can enable the new language it is only necessary to place the file in: <b>plugins/SkyWarsGame/language/</b>