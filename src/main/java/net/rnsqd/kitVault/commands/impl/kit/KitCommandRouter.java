package net.rnsqd.kitVault.commands.impl.kit;

import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.commands.CommandInformation;
import net.rnsqd.kitVault.commands.CommandRouter;
import net.rnsqd.kitVault.commands.impl.kit.subs.ClaimCommand;
import net.rnsqd.kitVault.commands.impl.kit.subs.HelpCommand;

import java.util.Map;

public final class KitCommandRouter extends CommandRouter {
    public KitCommandRouter(KitVault kitVault) {
        super(kitVault);
        this.register(
                Map.of(
                        // Help command
                        HelpCommand.class.getAnnotation(CommandInformation.class),
                        new HelpCommand(this),

                        // Claim kit command
                        ClaimCommand.class.getAnnotation(CommandInformation.class),
                        new ClaimCommand(this)
                )
        );
    }
}
