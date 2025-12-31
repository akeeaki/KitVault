package net.rnsqd.kitVault.services;

public interface ApplicatorService<T> {
    T apply(Object data);
}
