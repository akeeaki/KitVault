package net.rnsqd.kitVault.storage.impl;

import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.kit.KitRecord;
import net.rnsqd.kitVault.storage.AbstractKitsStorage;
import net.rnsqd.kitVault.storage.Type;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;

@Getter @Setter
public final class YamlKitsStorage extends AbstractKitsStorage {
    private final File file = new File(this.getKitVault().getDataFolder(), "kits.yml");
    private FileConfiguration cfg;

    public YamlKitsStorage(KitVault kitVault) {
        super(kitVault, Type.YAML);

        this.setCfg(YamlConfiguration.loadConfiguration(getFile()));
        this.cacheAll();
    }


    @Override
    public SaveResult saveKit(KitRecord kitRecord) {
        if (this.getRecord(kitRecord.name()) != null) {
            this.getKitRecords().remove(kitRecord);
            this.getKitRecords().add(kitRecord);
            return SaveResult.UPDATED_EXISTS;
        } else  {
            this.getKitRecords().add(kitRecord);
            return SaveResult.CREATED_NEW;
        }
    }

    @Override
    public RemoveResult removeKit(String kitName) {
        final KitRecord record = getRecord(kitName);
        if (record != null) {
            this.getKitRecords().remove(record);
            this.saveAll();
            return RemoveResult.SUCCESS;
        }
        return RemoveResult.NOT_FOUND;
    }

    @Override
    public void cacheAll() {
        final long start = System.currentTimeMillis();

        this.getKitRecords().clear();
        final ConfigurationSection section = this.getCfg().getConfigurationSection("kits");
        if (section == null || section.getKeys(false).isEmpty())
            return;

        section.getKeys(false).forEach((kitName) -> {
            final ConfigurationSection kitSection = section.getConfigurationSection(kitName);
            if (kitSection == null ||  kitSection.getKeys(false).isEmpty()) return;

            final long cooldown = kitSection.getLong("cooldown");
            final ConfigurationSection itemsSection = kitSection.getConfigurationSection("items");
            if (itemsSection == null || itemsSection.getKeys(false).isEmpty()) return;

            final HashMap<Integer, ItemStack> items = new HashMap<>();
            itemsSection.getKeys(false).forEach((slotStr) -> {
                final int slot = Integer.parseInt(slotStr);
                final ItemStack itemStack = itemsSection.getItemStack(slotStr);
                items.put(slot, itemStack);
            });

            final KitRecord record = new KitRecord(kitName, items, cooldown);
            this.getKitRecords().add(record);
        });

        this.getKitVault().getSLF4JLogger().info("Successfully cached kits in {} millis, loaded {} kits", System.currentTimeMillis() - start, this.getKitRecords().size());
    }

    @Override
    public void saveAll() {
        final long start = System.currentTimeMillis();

        this.getKitRecords().forEach(record -> {
            this.getCfg().set("kits." + record.name() + ".cooldown", record.cooldown());
            record.items().forEach((slot, stack) -> this.getCfg().set("kits." + record.name() + ".items." + slot, stack));
        });

        this.getKitVault().getSLF4JLogger().info("Saved all kits in {} millis", System.currentTimeMillis() - start);
    }
}
