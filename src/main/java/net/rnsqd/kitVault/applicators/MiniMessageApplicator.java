package net.rnsqd.kitVault.applicators;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.rnsqd.kitVault.services.ApplicatorService;
import org.bukkit.ChatColor;

public final class MiniMessageApplicator implements ApplicatorService<Component> {
    private final MiniMessage mini = MiniMessage.miniMessage();
    private final LegacyComponentSerializer legacy =
            LegacyComponentSerializer.builder()
                    .character('§')
                    .hexColors()
                    .build();

    @Override
    public Component apply(Object data) {
        if (!(data instanceof String string))
            throw new IllegalArgumentException("data must be a string");
        return mini.deserialize(string);
    }

    public String toLegacy(String text) {
        return legacy.serialize(apply(text));
    }

    // Not good because this method using a deprecated class(-es)
    public String fromLegacy(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
