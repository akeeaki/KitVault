package net.rnsqd.kitVault;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.rnsqd.kitVault.commands.CommandRouter;
import net.rnsqd.kitVault.commands.impl.kit.KitCommandRouter;
import net.rnsqd.kitVault.commands.impl.kitvault.KitVaultCommandRouter;
import net.rnsqd.kitVault.config.impl.MainConfiguration;
import net.rnsqd.kitVault.converter.json.JsonConvertImpl;
import net.rnsqd.kitVault.database.AbstractDatabase;
import net.rnsqd.kitVault.database.impl.SqliteDatabase;
import net.rnsqd.kitVault.reflect.GoodForReflection;
import net.rnsqd.kitVault.reload.ReloadResultInstance;
import net.rnsqd.kitVault.schedulers.cooldown.CooldownSchedulerInstance;
import net.rnsqd.kitVault.storage.AbstractKitsStorage;
import net.rnsqd.kitVault.storage.impl.YamlKitsStorage;
import net.rnsqd.kitVault.updatechecker.UpdateChecker;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;

import java.io.File;
import java.io.FileReader;

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
public final class KitVault extends JavaPlugin implements GoodForReflection {

    private MainConfiguration mainConfiguration;

    private KitVaultCommandRouter kitVaultCommandRouter;
    private KitCommandRouter kitCommandRouter;

    private AbstractKitsStorage kitsStorage;
    private AbstractDatabase database;

    private CooldownSchedulerInstance cooldownSchedulerInstance;

    private final JsonConvertImpl jsonConvert = new JsonConvertImpl();
    private UpdateChecker.CheckUpdateResultInstance checkUpdateResultInstance;

    private boolean successEnabled = false, successLoaded = false;

    @SneakyThrows
    @Override
    public void onLoad() {
        this.saveResource("config.json", false);
        this.saveResource("locale.json", false);

        this.mainConfiguration = new MainConfiguration(this);
        this.mainConfiguration = this.getJsonConvert().gson.fromJson(new FileReader(new File(getDataFolder(), "config.json")), this.mainConfiguration.getClass());

        if (this.getMainConfiguration().isCheckUpdates())
            this.checkUpdateResultInstance = UpdateChecker.checkUpdate(this);

        this.kitVaultCommandRouter = new KitVaultCommandRouter(this);
        this.kitCommandRouter = new KitCommandRouter(this);

        this.database = new SqliteDatabase(this);
        this.kitsStorage = new YamlKitsStorage(this);

        this.cooldownSchedulerInstance = new CooldownSchedulerInstance(this);
        this.successLoaded = true;
    }

    @Override
    public void onEnable() {
        if (!successLoaded) {
            this.getSLF4JLogger().error("KitVault did not load correctly.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
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

        this.cooldownSchedulerInstance.start();
        this.getSLF4JLogger().info("Cooldown scheduler started successfully as timer with timings : 20,20 !");

        this.getSLF4JLogger().info("KitVault enabled in {} millis", System.currentTimeMillis() - startTime);
        this.successEnabled = true;

        if (this.checkUpdateResultInstance != null) {
            if (this.checkUpdateResultInstance.result() == UpdateChecker.CheckUpdateResult.LATEST) {
                this.getSLF4JLogger().info("You're up to date to the latest version of KitVault!");
            } else if (this.checkUpdateResultInstance.result() == UpdateChecker.CheckUpdateResult.LAGGING_VERSION) {
                this.getSLF4JLogger().warn("You're using a lagging version of KitVault! Please, update KitVault to the latest version ({})!", this.checkUpdateResultInstance.latestVersion());
            }
        }
    }

    @Override
    public void onDisable() {
        if (this.successEnabled) {
            this.getMainConfiguration().setCheckUpdates(false);
            this.getMainConfiguration().save(this);
        }

    }

    @Override
    public Logger goodLogger() {
        return this.getSLF4JLogger();
    }

    public ReloadResultInstance reload() {
        final ReloadResultInstance reloadResult = new ReloadResultInstance();
        reloadResult.start();

        this.reloadConfig();

        reloadResult.finish();
        return reloadResult;
    }
}
