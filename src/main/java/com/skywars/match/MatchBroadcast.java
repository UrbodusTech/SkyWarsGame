package com.skywars.match;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.OnScreenTextureAnimationPacket;
import cn.nukkit.network.protocol.PlaySoundPacket;
import com.skywars.event.player.PlayerLostMatchEvent;
import com.skywars.event.player.PlayerWinMachEvent;
import com.skywars.utils.AttributeUtils;
import com.skywars.utils.EventUtils;
import com.skywars.utils.LangUtils;
import com.skywars.session.GameSession;
import com.skywars.utils.LevelUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchBroadcast {

    private final Match match;

    @SuppressWarnings("all")
    private boolean checkConditions(GameSession session) {
        if (session == null) {
            return false;
        }

        if (session.getPlayer() == null) {
            return false;
        }

        return true;
    }

    public void publishMessage(String messageId, String[] args) {
        for (GameSession session : match.getPlayers()) {
            if (!checkConditions(session)) {
                continue;
            }

            session.getPlayer().sendMessage(LangUtils.translate(session.getPlayer(), messageId, args));
        }
    }

    public void publishMessage(String messageId) {
        publishMessage(messageId, new String[0]);
    }

    public void publishPopup(String messageId, String[] args) {
        for (GameSession session : match.getPlayers()) {
            if (!checkConditions(session)) {
                continue;
            }

            session.getPlayer().sendPopup(LangUtils.translate(session.getPlayer(), messageId, args));
        }
    }

    public void publishPopup(String messageId) {
        publishPopup(messageId, new String[0]);
    }

    public void publishTitle(String titleId, String[] titleArgs, String subTitleId, String[] subtitleArgs) {
        for (GameSession session : match.getPlayers()) {
            if (!checkConditions(session)) {
                continue;
            }

            Player player = session.getPlayer();
            player.sendTitle(
                    LangUtils.translate(player, titleId, titleArgs),
                    LangUtils.translate(player, subTitleId, subtitleArgs)
            );
        }
    }

    public void publishTitle(String titleId, String[] titleArgs) {
        for (GameSession session : match.getPlayers()) {
            if (!checkConditions(session)) {
                continue;
            }

            session.getPlayer().sendTitle(
                    LangUtils.translate(session.getPlayer(), titleId, titleArgs),
                    " "
            );
        }
    }

    public void publishSound(String sound) {
        for (GameSession session : match.getPlayers()) {
            if (!checkConditions(session)) {
                continue;
            }

            Vector3 vector = session.getPlayer().getPosition();
            PlaySoundPacket packet = new PlaySoundPacket();
            packet.name = sound;
            packet.volume = 1;
            packet.pitch = 1;
            packet.x = (int)vector.getX();
            packet.y = (int)vector.getY();
            packet.z = (int)vector.getZ();

            session.getPlayer().dataPacket(packet);
        }
    }

    public void publishStartAttributes() {
        for (GameSession session : match.getPlayers()) {
            if (!checkConditions(session)) {
                continue;
            }

            AttributeUtils.sendStart(session.getPlayer());
            session.createBossBar();
        }
    }

    public void publishBossBar(String messageId, String[] args) {
        for (GameSession session : match.getPlayers()) {
            if (!checkConditions(session)) {
                continue;
            }

            if (session.getBossBar() == null) {
                continue;
            }

            session.getBossBar().setText(LangUtils.translate(session.getPlayer(), messageId, args));
        }
    }

    public void publishResults() {
        for (GameSession session : match.getPlayers()) {
            if (!checkConditions(session)) {
                continue;
            }

            Player player = session.getPlayer();
            if (match.getWinnerName() == null) {
                player.sendTitle(
                        LangUtils.translate(player, "NO_WINNERS_SCREEN"),
                        LangUtils.translate(player, "BEST_LUCK_SUB_SCREEN")
                );
                OnScreenTextureAnimationPacket packet = new OnScreenTextureAnimationPacket();
                packet.effectId = 3;
                player.dataPacket(packet);

                continue;
            }

            if (session.getPlayer().getName().equals(match.getWinnerName())) {
                player.sendTitle(
                        LangUtils.translate(player, "WINNER_SCREEN"),
                        " "
                );
                OnScreenTextureAnimationPacket packet = new OnScreenTextureAnimationPacket();
                packet.effectId = 22;
                player.dataPacket(packet);
                EventUtils.callEvent(new PlayerWinMachEvent(player, match));

                continue;
            }

            player.sendTitle(
                    LangUtils.translate(player, "LOST_SCREEN"),
                    LangUtils.translate(player, "BEST_LUCK_SUB_SCREEN")
            );
            OnScreenTextureAnimationPacket packet = new OnScreenTextureAnimationPacket();
            packet.effectId = 18;
            player.dataPacket(packet);
            EventUtils.callEvent(new PlayerLostMatchEvent(player, match));
        }
    }

    public void publishRemove() {
        Level level = LevelUtils.getSkyWarsLevel(match.getUuid());
        if (level == null) {
            return;
        }

        for (Player player : level.getPlayers().values()) {
            match.removePlayer(player);
        }
    }
}
