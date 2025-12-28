package net.rnsqd.kitVault.schedulers.cooldown;

import net.rnsqd.kitVault.KitVault;
import org.bukkit.scheduler.BukkitScheduler;

public final class CooldownSchedulerInstance implements Runnable {
    private final KitVault plugin;

    public CooldownSchedulerInstance(final KitVault plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
    }

    @Override
    public void run() {
        this.plugin
    }
}
