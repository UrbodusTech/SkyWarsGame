package com.skywars.command.defaults;

import cn.nukkit.utils.TextFormat;
import com.skywars.GameLoader;
import com.skywars.command.GameCommand;
import com.skywars.session.GameSession;
import com.skywars.utils.LangUtils;

public class HelpCommand extends GameCommand {

    public HelpCommand() {
        super("help", "HELP_COMMAND_DESCRIPTION", "");
    }

    @Override
    public void execute(GameSession session, String[] args) {
        for (GameCommand command : GameLoader.getInstance().getCommandManager().getEntries()) {
            session.getPlayer().sendMessage(TextFormat.colorize(
                    "&e!" + command.getName() + "&7 - " + LangUtils.translate(session.getPlayer(), command.getDescriptionTranslateId())
            ));
        }
    }
}
