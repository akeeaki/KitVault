package net.rnsqd.kitVault.commands.impl.kit.subs;

import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.commands.AbstractCommandInstance;
import net.rnsqd.kitVault.commands.CommandInformation;
import net.rnsqd.kitVault.commands.CommandRouter;
import net.rnsqd.kitVault.database.PlayerRecord;
import net.rnsqd.kitVault.kit.KitRecord;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandInformation(name = "claim")
public final class ClaimCommand extends AbstractCommandInstance {
    public ClaimCommand(CommandRouter router) {
        super(router);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args, String label) {
        final KitVault kitVault = this.getRouter().getKitVault();

        if (!(sender instanceof Player player)) {
            sender.sendMessage(kitVault.getMiniMessageApplicator().apply(kitVault.getLocaleConfiguration().claim__Not_Player));
            return true;
        }

        final KitRecord kitRecord = kitVault.getKitsStorage().getRecord(args[0]);

        if (kitRecord == null) {
            sender.sendMessage(kitVault.getMiniMessageApplicator().apply(kitVault.getLocaleConfiguration().claim__Not_Found.replaceAll("#kit#", args[0])));
            return true;
        }

        final PlayerRecord playerRecord = kitVault.getDatabase().getRecord(player);

        boolean isBypass = player.hasPermission("kitvault.bypass.cooldown") || player.hasPermission("kitvault.bypass.all");
        boolean isAvailable = !playerRecord.kitsCooldowns().containsKey(args[0]);

        if (isBypass || isAvailable) {
            kitRecord.give(player);
            playerRecord.kitsCooldowns().put(args[0], kitRecord.cooldown());
            sender.sendMessage(kitVault.getMiniMessageApplicator().apply(kitVault.getLocaleConfiguration().claim__Claimed.replaceAll("#kit#", args[0])));
        } else {
            sender.sendMessage(
                    kitVault.getMiniMessageApplicator().apply(kitVault.getLocaleConfiguration().claim__Cooldown
                            .replaceAll("#kit#", args[0])
                            .replaceAll("#formatted_cooldown#", kitVault.getTimeFormatApplicator().apply(playerRecord.kitsCooldowns().get(args[0])))
                    )
            );

        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String label) {
        return List.of();
    }
}
