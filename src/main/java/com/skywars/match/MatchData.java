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
     * mapDirName: The name of the dir of the world map this dir must be in SkyWarsGame/maps/
     */
    private String name;
    private String author;
    private String mapDirName;

    /*
     * Match Positions Data
     * islandSpawn: Island positions
     */
    private List<Integer[]> islandSpawn;

    /*
     * Basic Configuration
     * countDownSeconds: Once the necessary players are gathered, the countdown will begin, choose how many seconds before starting the game
     * countDownMinPlayers: Minimum number of players to start the countdown. if it is 0 >>> 2 in automatic
     * countDownEnd: Choose the wait time when the match ends for players to be removed from the match
     * maxTimerRepetitions: After the time ends the chests are refilled and the time restarted, it indicates how many maximum times you can do these repetitions, once the repetitions are finished the match will end.
     * countDownTimerSeconds: The seconds between each repetition of refill chest
     */
    private Integer countDownSeconds;
    private Integer countDownMinPlayers;
    private Integer countDownEnd;
    private Integer maxTimerRepetitions;
    private Integer countDownTimerSeconds;
}
