package net.rnsqd.kitVault.schedulers.cooldown;

import net.rnsqd.kitVault.KitVault;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Iterator;
import java.util.Map;

public final class CooldownSchedulerInstance extends BukkitRunnable {
    private final KitVault plugin;

    public CooldownSchedulerInstance(final KitVault plugin) {
        this.plugin = plugin;
    }

    public void start() {
//        this.plugin.getServer().getScheduler().runTaskTimer /* устаревшная хуйня, но меня не волнует */(plugin, this, 20L, 20L);
        this.runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void run() {
        this.plugin.getDatabase().getPlayerRecords().forEach(p -> {
            if (p.kitsCooldowns() != null && !p.kitsCooldowns().isEmpty()) {
                Iterator<Map.Entry<String, Long>> iterator = p.kitsCooldowns().entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Long> entry = iterator.next();
                    final long newCooldown = entry.getValue() - 1;
                    if (newCooldown <= 0) {
                        iterator.remove();
                    } else {
                        entry.setValue(newCooldown);
                    }
                    plugin.getDatabase().saveAll();
                }
            }
        });
    }
}
