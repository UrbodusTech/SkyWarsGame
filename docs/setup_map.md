# Set up a map
Configuring the maps is an easy task since you only have to create the configuration json file and add the world map inside the data. SkyWars automatically takes care of managing the maps.

## Configure Map
To configure a map add the world in plugins/SkyWarsGame/maps/ and create the configuration json in plugins/SkyWarsGame/configs/
<img src="https://i.imgur.com/8WiKDyk.png" width="100%">

testMap.json
```json
{
  "name": "Test",
  "author": "UrodusTech",
  "mapDirName": "TestMap",
  "islandSpawn": [
    [x, y, z],
    [x, y, z]
  ],
  "islandChests": [
    [x, y, z],
    [x, y, z],
    [x, y, z],
    [x, y, z]
  ],
  "otherChests": [
    [x, y, z],
    [x, y, z],
    [x, y, z],
    [x, y, z]
  ],
  "minLayer": 2,
  "countDownSeconds": 30,
  "countDownMinPlayers": 2,
  "countDownEnd": 10,
  "maxTimerRepetitions": 3,
  "countDownTimerSeconds": 300
}
```

- <b>name</b>: The name of the map that will be shown to the players
- <b>author</b>: The name of the author(s) of the map or build team.
- <b>mapDirName</b>: The name of the dir of the world map this dir must be in SkyWarsGame/maps/
- <b>islandSpawn</b>: Island positions.
- <b>minLayer</b>: Indicates until which layer Y the map is limited, once the player is in this layer or minor will be considered death by void, if layer <= 0 then layer = 2.
- <b>islandChests</b>: Exact positions of the island chests.
- <b>otherChests</b>: Exact positions of the remaining chests.
- <b>countDownSeconds</b>: Once the necessary players are gathered, the countdown will begin, choose how many seconds before starting the game.
- <b>countDownMinPlayers</b>: Minimum number of players to start the countdown. if it is 0 >>> 2 in automatic.
- <b>countDownEnd</b>: Choose the wait time when the match ends for players to be removed from the match.
- <b>maxTimerRepetitions</b>: After the time ends the chests are refilled and the time restarted, it indicates how many maximum times you can do these repetitions, once the repetitions are finished the match will end.
- <b>countDownTimerSeconds</b>: The seconds between each repetition of refill chest.

If you want to see an example, you can see the map used for development <a href="https://github.com/UrbodusTech/SkyWars/tree/d1fe80875c9fb400718ef4d496f0c1f412056527/example/test-map">here</a>.