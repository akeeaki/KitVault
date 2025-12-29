package net.rnsqd.kitVault.kit;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public record KitRecord(String name, HashMap<Integer, ItemStack> items, long cooldown) {
}
