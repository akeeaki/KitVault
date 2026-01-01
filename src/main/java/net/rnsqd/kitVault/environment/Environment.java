package net.rnsqd.kitVault.environment;

public record Environment<T extends EnvironmentClass>(ServerEnvironment<T> serverEnvironment,
                                                      SystemEnvironment<T> systemEnvironment,
                                                      T environment) {
}
