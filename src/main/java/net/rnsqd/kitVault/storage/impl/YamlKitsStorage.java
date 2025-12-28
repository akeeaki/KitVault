package net.rnsqd.kitVault.storage.impl;

import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.storage.AbstractKitsStorage;
import net.rnsqd.kitVault.storage.Type;

public final class YamlKitsStorage extends AbstractKitsStorage {
    public YamlKitsStorage(KitVault kitVault) {
        super(kitVault, Type.YAML);
    }


}
