# Session Events

### SessionExecuteCommandEvent
Executed when a session wants to execute a GameCommand, if it is canceled command will not be executed.
```java
public class EventListener implements Listener {

    @EventHandler
    public void onExecuteGameCommand(SessionExecuteCommandEvent event) {
       
    }
}
```