package net.rnsqd.kitVault.config.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.config.AbstractConfiguration;
import net.rnsqd.kitVault.reflect.GoodForReflection;

import java.io.*;

@Getter @Setter
public final class LocaleConfiguration extends AbstractConfiguration {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .serializeNulls()
            .create();

    // Start serializing

    // Main
    @SerializedName(value = "prefix")
    public String prefix = "<gold>KitVault <gray>» <white>";

    @SerializedName(value = "no-perms")
    public String noPerms = "<red>Недостаточно прав на использование данной команды!";

    // Save
    @SerializedName(value = "save.already-exists")
    public String save__Already_Exists = "<red>Кит с таким названием уже существует! Для редактирования существующего набора используйте: <white>/kitvault edit <название>";

    @SerializedName(value = "save.not-player")
    public String save__Not_Player = "<red>Эта команда доступна только игрокам!";

    @SerializedName(value = "save.saved")
    public String save__Saved = "<green>Вы успешно сохранили новый кит #kit#";

    // Claim
    @SerializedName(value = "claim.not-player")
    public String claim__Not_Player = "<red>Эта команда доступна только игрокам!";

    @SerializedName(value = "claim.claimed")
    public String claim__Claimed = "<green>Вы успешно взяли кит #kit#!";

    @SerializedName(value = "claim.not-found")
    public String claim__Not_Found = "<red>Кит #kit# не найден!";

    @SerializedName(value = "claim.cooldown")
    public String claim__Cooldown = "<red>Кит #kit# на перезарядке! Подождите ещё <gold>#formatted_cooldown#";

    // End serializing

    private transient String currentFileContent = null;

    public LocaleConfiguration(KitVault kitVault) {
        super(kitVault, new File(kitVault.getDataFolder(), "locale.json"));
        this.goodForReflection = kitVault;
        this.goodForReflection.goodLogger().info("Good for Reflection is initialized for locale class {}.", this.getClass().getName());
    }

    @Override
    public void reload(GoodForReflection goodForReflection) {
        try (Reader reader = new FileReader(new File(goodForReflection.getDataFolder(), "locale.json"))) {
            LocaleConfiguration loaded = GSON.fromJson(reader, LocaleConfiguration.class);
            currentFileContent = readFileContent(new File(goodForReflection.getDataFolder(), "locale.json"));
        } catch (Exception e) {
            goodForReflection.goodLogger().warn("Error while loading locale.json: {}", e.getMessage());
            save(goodForReflection);
        }
    }

    @Override
    public void save(GoodForReflection goodForReflection) {
        String newJson = GSON.toJson(this);

        if (newJson.equals(currentFileContent)) {
            return;
        }

        try (Writer writer = new FileWriter(new File(goodForReflection.getDataFolder(), "locale.json"))) {
            writer.write(newJson);
            currentFileContent = newJson;
        } catch (IOException e) {
            goodForReflection.goodLogger().error("Error while saving locale.json: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private String readFileContent(File file) {
        if (!file.exists()) return null;
        try (Reader reader = new FileReader(file)) {
            char[] buffer = new char[(int) file.length()];
            int read = reader.read(buffer);
            return read > 0 ? new String(buffer, 0, read) : "";
        } catch (IOException e) {
            return null;
        }
    }
}