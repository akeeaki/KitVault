package net.rnsqd.kitVault.environment;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public final class EnvironmentFetcher {
    @SneakyThrows
    public static <T extends EnvironmentClass> Environment<T> fetchEnv(Class<T> clazz) {
        return new Environment<>(new ServerEnvironment<>(
                Bukkit.getBukkitVersion(), Bukkit.getVersion(), Bukkit.getMinecraftVersion()
        ), new SystemEnvironment<>(
                System.getProperty("os.name"), System.getProperty("os.arch"), System.getProperty("os.version")
        ), clazz.getDeclaredConstructor().newInstance());
    }
}
