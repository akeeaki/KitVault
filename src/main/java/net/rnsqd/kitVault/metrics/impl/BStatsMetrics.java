package net.rnsqd.kitVault.metrics.impl;

import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.metrics.Metrica;

@Getter @Setter
public final class BStatsMetrics implements Metrica {
    private BStatsMetricsAgent agent;

    @Override
    public void load(KitVault kitVault) {
        this.setAgent(new BStatsMetricsAgent(kitVault, 28637));
    }
}
