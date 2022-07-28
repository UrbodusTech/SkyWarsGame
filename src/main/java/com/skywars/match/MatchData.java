package com.skywars.match;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchData {

    /*
     * General Match Data
     * name: The name of the map that will be shown to the players
     * author: The name of the author(s) of the map or build team.
     * mapZipId: The name of the .zip of the world map this zip must be in SkyWarsGame/maps/
     */
    private String name;
    private String author;
    private String mapDirName;

    /*
     * Match Positions Data
     * islandSpawn: Island positions
     * spectatorSpawn: The Spectator Spawn
     */
    private List<Integer[]> islandSpawn;
    private Integer[] spectatorSpawn;

    /*
     * Basic Configuration
     * countDownSeconds: Once the necessary players are gathered, the countdown will begin, choose how many seconds before starting the game
     * countDownMinPlayers: Minimum number of players to start the countdown. if it is 0 >>> 2 in automatic
     * countDownEnd: Choose the wait time when the match ends for players to be removed from the match
     */
    private Integer countDownSeconds;
    private Integer countDownMinPlayers;
    private Integer countDownEnd;
}
