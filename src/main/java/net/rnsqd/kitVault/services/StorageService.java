package net.rnsqd.kitVault.services;

import net.rnsqd.kitVault.kit.KitRecord;
import net.rnsqd.kitVault.storage.AbstractKitsStorage;

public interface StorageService {
    AbstractKitsStorage.SaveResult saveKit(KitRecord kitRecord);
    AbstractKitsStorage.RemoveResult removeKit(String kitName);

    void cacheAll();
    void saveAll();
}
