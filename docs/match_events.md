# Match Events

### MatchOpenEvent
Executed when the match is ready and available.

```java
public class EventListener implements Listener {

    @EventHandler
    public void onMatchOpen(MatchOpenEvent event) {
       
    }
}
```

### MatchCloseEvent
Executing when the match is not available, usually due to the start of map reset or because it was removed.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onMatchClose(MatchCloseEvent event) {
       
    }
}
```

### MatchEndEvent
Executing when time end and all players are removed from the match.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onMatchEnd(MatchEndEvent event) {
       
    }
}
```

### MatchRefillChestsEvent
Executing when chests are refilled, if event is cancelled, chests will not be refilled.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onMatchRefillChests(MatchRefillChestsEvent event) {
       
    }
}
```

### MatchResetFinishedEvent
Executed when the reset-map process finishes.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onMatchResetFinished(MatchResetFinishedEvent event) {
       
    }
}
```

### MatchStartEvent
Executed when the countdown ends and the match starts.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onMatchStart(MatchStartEvent event) {
       
    }
}
```

### MatchStartResetEvent
Executed when the map-reset process starts.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onMatchStartReset(MatchStartResetEvent event) {
       
    }
}
```