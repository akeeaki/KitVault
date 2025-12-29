package net.rnsqd.kitVault.database.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.database.AbstractDatabase;
import net.rnsqd.kitVault.database.Type;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Getter @Setter
public final class SqliteDatabase extends AbstractDatabase {
    private Connection connection;

    @SneakyThrows
    public SqliteDatabase(KitVault kitVault) {
        super(kitVault, Type.SQLITE);

        this.setConnection(DriverManager.getConnection(String.format("jdbc:sqlite:%s/database.db", kitVault.getDataFolder())));
        this.cacheAll();
    }

    @Override
    public void saveAll() {

    }

    @SneakyThrows
    @Override
    public void cacheAll() {
        final Statement statement = connection.createStatement();

    }

    @Override
    public void cache(String player) {

    }

    @Override
    public void save(String player) {

    }
}
