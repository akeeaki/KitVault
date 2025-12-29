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

    @SerializedName(value = "prefix")
    public String prefix = "&6KitVault &7» &f";

    @SerializedName(value = "no-perms")
    public String noPerms = "&cНедостаточно прав на использование данной команды!";

    private transient String currentFileContent = null;

    public LocaleConfiguration(KitVault kitVault) {
        super(kitVault, new File(kitVault.getDataFolder(), "config.json"));
        this.goodForReflection = kitVault;
        this.goodForReflection.goodLogger().info("Good for Reflection is initialized for config class {}.", this.getClass().getName());
    }

    @Override
    public void reload(GoodForReflection goodForReflection) {
        try (Reader reader = new FileReader(new File(goodForReflection.getDataFolder(), "config.json"))) {
            MainConfiguration loaded = GSON.fromJson(reader, MainConfiguration.class);
            if (loaded != null) {
                this.checkUpdates = loaded.checkUpdates;
            }

            currentFileContent = readFileContent(new File(goodForReflection.getDataFolder(), "config.json"));
        } catch (Exception e) {
            goodForReflection.goodLogger().warn("Error while loading config.json: {}", e.getMessage());
            save(goodForReflection);
        }
    }

    @Override
    public void save(GoodForReflection goodForReflection) {
        String newJson = GSON.toJson(this);

        if (newJson.equals(currentFileContent)) {
            return;
        }

        try (Writer writer = new FileWriter(new File(goodForReflection.getDataFolder(), "config.json"))) {
            writer.write(newJson);
            currentFileContent = newJson;
        } catch (IOException e) {
            goodForReflection.goodLogger().error("Error while saving config.json: {}", e.getMessage());
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