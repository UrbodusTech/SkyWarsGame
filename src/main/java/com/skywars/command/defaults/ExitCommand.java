package com.skywars.command.defaults;

import com.skywars.command.GameCommand;
import com.skywars.match.Match;
import com.skywars.session.GameSession;

public class ExitCommand extends GameCommand {

    public ExitCommand() {
        super("exit", "EXIT_COMMAND_DESCRIPTION", "");
    }

    @Override
    public void execute(GameSession session, String[] args) {
        Match match = session.getCurrentMatch();
        if (match == null) {
            return;
        }

        match.removePlayer(session.getPlayer());
    }
}
