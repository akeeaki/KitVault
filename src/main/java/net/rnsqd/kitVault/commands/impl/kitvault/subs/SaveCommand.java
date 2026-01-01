package net.rnsqd.kitVault.commands.impl.kitvault.subs;

import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.commands.AbstractCommandInstance;
import net.rnsqd.kitVault.commands.CommandInformation;
import net.rnsqd.kitVault.commands.CommandRouter;
import net.rnsqd.kitVault.kit.KitRecord;
import net.rnsqd.kitVault.storage.AbstractKitsStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;

@Getter @Setter
@CommandInformation(name = "save")
public final class SaveCommand extends AbstractCommandInstance {
    public SaveCommand(CommandRouter router) {
        super(router);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args, String label) {
        final KitVault vault = this.getRouter().getKitVault();
        final KitRecord record = vault.getKitsStorage().getRecord(args[0]);

        if (record != null) {
            sender.sendMessage(vault.getMiniMessageApplicator().apply(vault.getLocaleConfiguration().save__Already_Exists));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(vault.getMiniMessageApplicator().apply(vault.getLocaleConfiguration().save__Not_Player));
            return true;
        }

        final HashMap<Integer, ItemStack> items = new HashMap<>();

        final PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            final ItemStack item = inventory.getItem(i);
            if (item != null)
                items.put(i, item.clone());
        }

        final KitRecord newRecord = new KitRecord(args[0], items, vault.getMainConfiguration().defaultCooldown);
        vault.getKitsStorage().saveKit(newRecord);

        player.sendMessage(vault.getMiniMessageApplicator().apply(vault.getLocaleConfiguration().save__Saved.replaceAll("#kit#", newRecord.name())));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String label) {
        return List.of();
    }
}
