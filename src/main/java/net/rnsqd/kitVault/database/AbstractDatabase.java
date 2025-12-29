package net.rnsqd.kitVault.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.services.DatabaseService;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Getter @Setter
@AllArgsConstructor
public abstract class AbstractDatabase implements DatabaseService {
    private final KitVault kitVault;
    private final Type type;

    private final Set<PlayerRecord> playerRecords = new HashSet<>();

    public PlayerRecord getRecord(final String playerName) {
        AtomicReference<PlayerRecord> record = new AtomicReference<>();
        this.playerRecords.forEach(playerRecord -> {
            if (playerRecord.name().equalsIgnoreCase(playerName))
                record.set(playerRecord);
        });
        return record.get();
    }

    public PlayerRecord getRecord(final Player player) {
        return this.getRecord(player.getName());
    }
}
