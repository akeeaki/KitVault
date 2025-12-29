package net.rnsqd.kitVault.commands.impl.kitvault.subs;

import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.commands.AbstractCommandInstance;
import net.rnsqd.kitVault.commands.CommandInformation;
import net.rnsqd.kitVault.commands.CommandRouter;
import org.bukkit.command.CommandSender;

import java.util.List;

@Getter
@Setter
@CommandInformation(name = "save")
public final class SaveCommand extends AbstractCommandInstance {
    public SaveCommand(CommandRouter router) {
        super(router);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args, String label) {


        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String label) {
        return List.of();
    }
}
