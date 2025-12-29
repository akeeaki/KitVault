package net.rnsqd.kitVault.schedulers.cooldown;

import net.rnsqd.kitVault.KitVault;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

public final class CooldownSchedulerInstance implements Runnable {
    private final KitVault plugin;

    public CooldownSchedulerInstance(final KitVault plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
    }

    @Override
    public void run() {
        this.plugin.getDatabase().getPlayerRecords().forEach(p -> {
            if (p.kitsCooldowns() != null) {
                p.kitsCooldowns().forEach((key, value) -> {
                    final long newCooldown = value - 1;
                    if (newCooldown <= 0)
                        p.kitsCooldowns().remove(key);
                    else
                        p.kitsCooldowns().put(key, newCooldown);
                });
            }
        });
    }
}
