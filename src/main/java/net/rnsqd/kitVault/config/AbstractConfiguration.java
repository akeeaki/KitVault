package net.rnsqd.kitVault.config;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.reflect.GoodForReflection;
import net.rnsqd.kitVault.services.ConfigService;

import java.io.File;

@Getter @Setter
@AllArgsConstructor
public abstract class AbstractConfiguration implements ConfigService {
    public GoodForReflection goodForReflection;
    private transient final File file;
}
