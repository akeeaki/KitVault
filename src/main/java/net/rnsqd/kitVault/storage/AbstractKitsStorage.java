package net.rnsqd.kitVault.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.kit.KitRecord;
import net.rnsqd.kitVault.services.StorageService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Getter @Setter
@AllArgsConstructor
public abstract class AbstractKitsStorage implements StorageService {
    private final KitVault kitVault;
    private final Type type;

    private final Set<KitRecord> kitRecords = new HashSet<>();

    public KitRecord getRecord(final String kitName) {
        AtomicReference<KitRecord> record = new AtomicReference<>();
        this.kitRecords.forEach(kitRecord -> {
            if (kitRecord.name().equalsIgnoreCase(kitName))
                record.set(kitRecord);
        });
        return record.get();
    }

    public enum RemoveResult {
        SUCCESS, NOT_FOUND
    }

    public enum SaveResult {
        CREATED_NEW, UPDATED_EXISTS
    }
}
