package net.rnsqd.kitVault;

import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.commands.CommandRouter;
import net.rnsqd.kitVault.commands.impl.kit.KitCommandRouter;
import net.rnsqd.kitVault.commands.impl.kitvault.KitVaultCommandRouter;
import net.rnsqd.kitVault.database.AbstractDatabase;
import net.rnsqd.kitVault.database.impl.SqliteDatabase;
import net.rnsqd.kitVault.reload.ReloadResultInstance;
import net.rnsqd.kitVault.storage.AbstractKitsStorage;
import net.rnsqd.kitVault.storage.impl.YamlKitsStorage;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Get high, сука (get high, сука)
 * Летай, сука (YT!)
 * Мы едем — всё в дыме, мы едем — всё в дыме
 * Две плохие суки набирают по мобиле
 * MOB, bitch, Yung Murda get money
 * У вас нет дерьма, вы домашние парни
 * Ваших сук переебали — вы сучьи парни
 * А я в Armani пошёл по барам
 * По шалавам и по кварталам
 * Делаю темы на фоксе, как надо
 * Базарь как надо или вылетят зубы
 * Yung Treezy Crazy — я вечно в теме
 * Молодой Craig Mack, сумасшедший сленг
 * У меня есть крэк, у меня есть фен
 * Bitch, я феномен, поцелуй мой хуй
 * Trappa много плана дул, я иду по городу
 * Я кладу этот город в свою руку
 * Ты в свою руку кладёшь хуй
 * SupaPlaya (SupaPlaya), SupaPlaya (SupaPlaya)
 * SupaPlaya (SupaPlaya), call me SupaPlaya (SupaPlaya)
 * You know, how we do (пау-пау-пау-пау-пау)
 * You know, how I play
 */
@Getter @Setter
public final class KitVault extends JavaPlugin {

    private KitVaultCommandRouter kitVaultCommandRouter;
    private KitCommandRouter kitCommandRouter;

    private AbstractKitsStorage kitsStorage;
    private AbstractDatabase database;

    private boolean successEnabled = false;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();

        this.kitVaultCommandRouter = new KitVaultCommandRouter(this);
        this.kitCommandRouter = new KitCommandRouter(this);

        this.database = new SqliteDatabase(this);
        this.kitsStorage = new YamlKitsStorage(this);
    }

    @Override
    public void onEnable() {
        final long startTime = System.currentTimeMillis();

        final PluginCommand kitVaultCommand = getCommand("kitvault");
        if (kitVaultCommand != null) {
            kitVaultCommand.setExecutor(this.kitVaultCommandRouter);
            kitVaultCommand.setTabCompleter(this.kitVaultCommandRouter);
            this.getSLF4JLogger().info("Command setup successfully!");
        } else {
            this.getSLF4JLogger().error("Invalid plugin.yml, not found 'kitvault' command!'");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        final PluginCommand kitCommand = getCommand("kit");
        if (kitCommand != null) {
            kitCommand.setExecutor(this.kitCommandRouter);
            kitCommand.setTabCompleter(this.kitCommandRouter);
            this.getSLF4JLogger().info("Command setup successfully!");
        } else {
            this.getSLF4JLogger().error("Invalid plugin.yml, not found 'kit' command!'");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.getSLF4JLogger().info("KitVault enabled in {} millis", System.currentTimeMillis() - startTime);
        this.successEnabled = true;
    }

    @Override
    public void onDisable() {
        if (this.successEnabled) {

        }

    }

    public ReloadResultInstance reload() {
        final ReloadResultInstance reloadResult = new ReloadResultInstance();
        reloadResult.start();

        this.reloadConfig();

        reloadResult.finish();
        return reloadResult;
    }
}
