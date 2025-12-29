package net.rnsqd.kitVault.commands.impl.kitvault;

import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.commands.CommandInformation;
import net.rnsqd.kitVault.commands.CommandRouter;
import net.rnsqd.kitVault.commands.impl.kitvault.subs.HelpCommand;
import net.rnsqd.kitVault.commands.impl.kitvault.subs.SaveCommand;

import java.util.Map;

public final class KitVaultCommandRouter extends CommandRouter {
    public KitVaultCommandRouter(KitVault kitVault) {
        super(kitVault);
        this.register(
                Map.of(
                        // Help command
                        HelpCommand.class.getAnnotation(CommandInformation.class),
                        new HelpCommand(this),

                        // Save command
                        SaveCommand.class.getAnnotation(CommandInformation.class),
                        new SaveCommand(this)
                )
        );
    }
}
