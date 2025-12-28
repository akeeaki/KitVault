package net.rnsqd.kitVault.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.rnsqd.kitVault.KitVault;

@Getter @AllArgsConstructor
public abstract class AbstractCommandInstance implements CommandInstance {
    private final CommandRouter router;
}
