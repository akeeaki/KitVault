package net.rnsqd.kitVault.reload;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public final class ReloadResultInstance {
    private ReloadResult result;
    private long took;
    private Set<Exception> exceptions;

    public void start() {
        this.result = ReloadResult.SUCCESS;
        this.took = System.currentTimeMillis();
        this.exceptions = new HashSet<Exception>();
    }

    public void finish() {
        this.took = System.currentTimeMillis() - this.took;
    }

    public ReloadResultInstance exception(Exception exception) {
        this.exceptions.add(exception);
        this.result = ReloadResult.WITH_ERRORS;
        return this;
    }
}
