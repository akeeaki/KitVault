package net.rnsqd.kitVault.applicators;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.rnsqd.kitVault.services.ApplicatorService;

public final class MiniMessageApplicator implements ApplicatorService {
    private final MiniMessage mini = MiniMessage.miniMessage();
    private final LegacyComponentSerializer legacy =
            LegacyComponentSerializer.builder()
                    .character('§')
                    .hexColors()
                    .build();

    @Override
    public Object apply(Object data) {
        if (!(data instanceof String string))
            throw new IllegalArgumentException("data must be a string");
        return mini.deserialize(string);
    }

    public String toLegacy(String text) {
        if (!(apply(text) instanceof Component component))
            throw new IllegalArgumentException("text must be a component");
        return legacy.serialize(component);
    }
}
