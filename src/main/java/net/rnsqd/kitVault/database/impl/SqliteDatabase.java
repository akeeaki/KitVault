package net.rnsqd.kitVault.database.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.database.AbstractDatabase;
import net.rnsqd.kitVault.database.PlayerRecord;
import net.rnsqd.kitVault.database.Type;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

@Getter @Setter
public final class SqliteDatabase extends AbstractDatabase {
    private Connection connection;

    @SneakyThrows
    public SqliteDatabase(KitVault kitVault) {
        super(kitVault, Type.SQLITE);

        this.setConnection(DriverManager.getConnection(String.format("jdbc:sqlite:%s/database.db", kitVault.getDataFolder())));
        this.validateTable();
        this.cacheAll();
    }

    @SneakyThrows
    private void validateTable() {
        String sql = """
            
                CREATE TABLE IF NOT EXISTS players (
                                                        player_name TEXT NOT NULL,
                                                        player_uuid TEXT UNIQUE NOT NULL,
                                                        cooldowns TEXT DEFAULT '',
                                                        PRIMARY KEY (player_uuid)
                                                    );
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public void saveAll() {

    }

    @SneakyThrows
    @Override
    public void cacheAll() {
        final Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM players");
        while (resultSet.next()) {
            final String playerName = resultSet.getString("player_name");
            final String playerUUID = resultSet.getString("player_uuid");
            final HashMap<String, Long> cooldowns = new HashMap<>();
            final String[] split = resultSet.getString("cooldowns").split(";");
            for (String s : split) {
                String[] splited = s.split(":");
                cooldowns.put(splited[0], Long.parseLong(splited[1]));
            }

            this.getPlayerRecords().add(new PlayerRecord(playerName, UUID.fromString(playerUUID), cooldowns));
        }
    }

    @Override
    public void cache(String player) {

    }

    @Override
    public void save(String player) {

    }
}
