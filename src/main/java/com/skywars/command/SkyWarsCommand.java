package com.skywars.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import com.skywars.GameLoader;

public class SkyWarsCommand extends VanillaCommand {

    public SkyWarsCommand() {
        super("skywars", "SkyWars Match Queue", "/skywars", new String[] { "sw" });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            return false;
        }

        GameLoader.getInstance().getMatchManager().queue(((Player) commandSender));

        return false;
    }
}
