package net.rnsqd.kitVault.environment;

public record SystemEnvironment<T extends EnvironmentClass>(String osName, String osArch, String osVersion) {
}
