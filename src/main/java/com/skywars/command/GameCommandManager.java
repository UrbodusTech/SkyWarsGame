package com.skywars.command;

import com.skywars.command.defaults.ExitCommand;
import com.skywars.command.defaults.HelpCommand;
import com.skywars.session.GameSession;
import com.skywars.utils.LangUtils;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GameCommandManager {

    private final Map<String, GameCommand> commands;

    public GameCommandManager() {
        commands = new HashMap<>();
    }

    public void register(GameCommand command) {
        if (commands.containsKey(command.getName())) {
            return;
        }

        commands.put(command.getName(), command);
    }

    public void unregister(String name) {
        commands.remove(name);
    }

    public GameCommand getCommand(String name) {
        return commands.getOrDefault(name, null);
    }

    public void processCommand(GameSession session, String message) {
        message = message.substring(1);
        String[] split = message.split(" ");
        if (split.length == 0) {
            session.getPlayer().sendMessage(LangUtils.translate(session.getPlayer(), "INVALID_COMMAND"));

            return;
        }

        String prefix = split[0];
        GameCommand command = getCommand(prefix);
        if (command == null) {
            session.getPlayer().sendMessage(LangUtils.translate(session.getPlayer(), "INVALID_COMMAND"));

            return;
        }

        int argsLen = (split.length - 1);
        String[] args = new String[argsLen];
        System.arraycopy(split, 1, args, 0, argsLen);

        command.execute(session, args);
    }

    public void init() {
        register(new HelpCommand());
        register(new ExitCommand());
    }
}
