package com.skywars.command;

import com.skywars.session.GameSession;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class GameCommand {

    @NonNull
    private final String name;
    @NonNull
    private final String descriptionTranslateId;
    @NonNull
    private final String usageTranslateId;

    public abstract void execute(GameSession session, String[] args);
}
