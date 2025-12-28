package net.rnsqd.kitVault.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.services.StorageService;

@Getter @Setter
@AllArgsConstructor
public abstract class AbstractKitsStorage implements StorageService {
    private final KitVault kitVault;
    private final Type type;
}
