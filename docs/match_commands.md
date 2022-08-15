# Match Commands
SkyWars has by default a system for creating commands that can only be executed within SkyWars games and are synced to game data. These commands start with the prefix <b>!</b> like <b>!exit</b> or <b>!help</b>

## Create command
To create a command, you must extend the class to GameCommand and integrate the abstract functions:
```java
public class TestCommand extends GameCommand {

    public TestCommand() {
        super("test", "TEST_COMMAND_DESCRIPTION", "TEST_COMMAND_USAGE");
    }

    @Override
    public void execute(GameSession session, String[] args) {
        session.getPlayer().sendMessage("Hello World!");
    }
}
```
As we can see, the constructor requires three arguments: Command Name, Translation Key Description and Translation Key Usage. The translation keys must be messages must be declared in the lang files.<br>
Finally, everything inside execute will be called when the player executes the command inside a Match.

## Register
```java
public class ExampleExtension extends Extension {

    @Override
    public void install() {
        getCommandManager().register(new TestCommand());
    }

    @Override
    public void uninstall() {
        
    }
}
```

## Args
Commands can also include arguments like !test [arg1] [arg2] etc. You can use the information that comes in String[] args!

