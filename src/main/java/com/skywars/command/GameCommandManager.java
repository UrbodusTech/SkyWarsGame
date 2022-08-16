package com.skywars.command;

import com.skywars.command.defaults.ExitCommand;
import com.skywars.command.defaults.HelpCommand;
import com.skywars.event.session.SessionExecuteCommandEvent;
import com.skywars.generic.Manager;
import com.skywars.session.GameSession;
import com.skywars.utils.EventUtils;
import com.skywars.utils.LangUtils;

public class GameCommandManager extends Manager<String, GameCommand> {

    @Override
    public void init() {
        register(new HelpCommand());
        register(new ExitCommand());
    }

    public void processCommand(GameSession session, String message) {
        message = message.substring(1);
        String[] split = message.split(" ");
        if (split.length == 0) {
            session.getPlayer().sendMessage(LangUtils.translate(session.getPlayer(), "INVALID_COMMAND"));

            return;
        }

        String prefix = split[0];
        GameCommand command = get(prefix);
        if (command == null) {
            session.getPlayer().sendMessage(LangUtils.translate(session.getPlayer(), "INVALID_COMMAND"));

            return;
        }

        int argsLen = (split.length - 1);
        String[] args = new String[argsLen];
        System.arraycopy(split, 1, args, 0, argsLen);

        SessionExecuteCommandEvent event = new SessionExecuteCommandEvent(session, session.getCurrentMatch(), prefix, args);
        EventUtils.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        command.execute(session, args);
    }

}
