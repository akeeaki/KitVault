package net.rnsqd.kitVault.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandInstance {
    boolean execute(final CommandSender sender, String[] args, String label);
    List<String> tabComplete(CommandSender sender, String[] args, String label);
}
