package net.rnsqd.kitVault.environment;

import org.bukkit.Bukkit;

public record ServerEnvironment<T extends EnvironmentClass>(String bukkitVersion, String version, String minecraftVersion) {

}