package com.skywars.match;

import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlaySoundPacket;
import com.skywars.utils.AttributeUtils;
import com.skywars.utils.LangUtils;
import com.skywars.session.GameSession;
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

            session.getPlayer().sendTitle(
                    LangUtils.translate(session.getPlayer(), titleId, titleArgs),
                    LangUtils.translate(session.getPlayer(), subTitleId, subtitleArgs)
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
        }
    }
}
