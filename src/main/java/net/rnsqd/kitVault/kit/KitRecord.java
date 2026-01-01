package net.rnsqd.kitVault.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;

public record KitRecord(String name, HashMap<Integer, ItemStack> items, long cooldown) {
    public void give(final Player player) {
        final PlayerInventory playerInventory = player.getInventory();
        this.items().forEach((slot, stack) -> {
            final ItemStack item = playerInventory.getItem(slot);
            if (item != null) playerInventory.addItem(item).forEach((slot1, stack1) -> player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item));
            else playerInventory.setItem(slot, stack);
        });
    }
}
