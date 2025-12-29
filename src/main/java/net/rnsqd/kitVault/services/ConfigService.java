package net.rnsqd.kitVault.services;

import net.rnsqd.kitVault.reflect.GoodForReflection;

public interface ConfigService {
    void reload(GoodForReflection goodForReflection);
    void save(GoodForReflection goodForReflection);
}
