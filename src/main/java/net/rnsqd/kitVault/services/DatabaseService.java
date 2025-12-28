package net.rnsqd.kitVault.services;

public interface DatabaseService {
    void saveAll();
    void cacheAll();

    void cache(final String player);
    void save(final String player);
}
