package net.rnsqd.kitVault.environment;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class EnvironmentFetcher {
    @SneakyThrows
    public static <T extends EnvironmentClass> Environment<T> fetchEnv(Class<T> clazz) {
        return new Environment<>(new ServerEnvironment<>(), new SystemEnvironment<>(
                System.getenv("os.name"), System.getenv("os.arch"), System.getenv("os.version")
        ), clazz.getDeclaredConstructor().newInstance());
    }
}
