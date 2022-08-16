package com.skywars.command;

import com.skywars.generic.Identifiable;
import com.skywars.session.GameSession;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class GameCommand implements Identifiable<String> {

    @NonNull
    private final String name;
    @NonNull
    private final String descriptionTranslateId;
    @NonNull
    private final String usageTranslateId;

    @Override
    public String getId() {
        return name;
    }

    public abstract void execute(GameSession session, String[] args);
}
