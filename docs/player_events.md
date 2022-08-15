# Player Events

### PlayerConvertSpectatorEvent
Executed when a player dies and becomes a spectator.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onPlayerToSpectator(PlayerConvertSpectatorEvent event) {
       
    }
}
```

### PlayerJoinMatchEvent
Executed when a player enters a match.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoinMatch(PlayerJoinMatchEvent event) {
       
    }
}
```

### PlayerLostMatchEvent
Executing when the results are displayed and the player lost the game.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onPlayerLost(PlayerLostMatchEvent event) {
       
    }
}
```

### PlayerQuitMatchEvent
Executed when a player leaves a match.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onPlayerQuitMatch(PlayerQuitMatchEvent event) {
       
    }
}
```

### PlayerWinMachEvent
Executing when the results are displayed and the player wins the game.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onPlayerWin(PlayerWinMachEvent event) {
       
    }
}
```

### PlayerBlockCommandEvent
By default, the game blocks normal commands to avoid cheating and alterations as well as having its own system of commands, but if you need to control the blocking of commands you can cancel this event and the commands will work normally.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onBlockCommand(PlayerBlockCommandEvent event) {
       
    }
}
```