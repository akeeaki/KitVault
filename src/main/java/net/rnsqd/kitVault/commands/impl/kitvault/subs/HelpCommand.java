package net.rnsqd.kitVault.commands.impl.kitvault.subs;

import net.rnsqd.kitVault.commands.AbstractCommandInstance;
import net.rnsqd.kitVault.commands.CommandInformation;
import net.rnsqd.kitVault.commands.CommandRouter;
import org.bukkit.command.CommandSender;

import java.util.List;

@CommandInformation(name = "help")
public final class HelpCommand extends AbstractCommandInstance {
    public HelpCommand(CommandRouter router) {
        super(router);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args, String label) {
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String label) {
        return List.of();
    }
}
