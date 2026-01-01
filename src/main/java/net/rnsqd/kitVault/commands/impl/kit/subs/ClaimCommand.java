package net.rnsqd.kitVault.commands.impl.kit.subs;

import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.commands.AbstractCommandInstance;
import net.rnsqd.kitVault.commands.CommandInformation;
import net.rnsqd.kitVault.commands.CommandRouter;
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

        boolean isBypass = player.hasPermission("kitvault.bypass.cooldown") || player.hasPermission("kitvault.bypass.all");
        boolean isAvailable = !kitVault.getDatabase().isCooldown(player.getName(), kitRecord);

        if (isBypass || isAvailable) {
            kitRecord.give(player);
            kitVault.getDatabase().addCooldown(player.getName(), kitRecord);
            sender.sendMessage(kitVault.getMiniMessageApplicator().apply(kitVault.getLocaleConfiguration().claim__Claimed.replaceAll("#kit#", args[0])));
        } else {
            sender.sendMessage(
                    kitVault.getMiniMessageApplicator().apply(kitVault.getLocaleConfiguration().claim__Cooldown
                            .replaceAll("#kit#", args[0])
                            .replaceAll("#formatted_cooldown#", kitVault.getTimeFormatApplicator().apply(kitVault.getDatabase().getCooldown(player.getName(), kitRecord)))
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
