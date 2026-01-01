package net.rnsqd.kitVault.services;

import net.rnsqd.kitVault.kit.KitRecord;

public interface DatabaseService {
    void saveAll();
    void cacheAll();

    void cache(final String player);
    void save(final String player);

    boolean isCooldown(final String player, final KitRecord kitRecord);
    long getCooldown(final String player, final KitRecord kitRecord);
    void addCooldown(final String player, final KitRecord kitRecord);
}
