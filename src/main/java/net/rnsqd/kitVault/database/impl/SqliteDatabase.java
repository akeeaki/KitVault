package net.rnsqd.kitVault.database.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.database.AbstractDatabase;
import net.rnsqd.kitVault.database.PlayerRecord;
import net.rnsqd.kitVault.database.Type;
import net.rnsqd.kitVault.kit.KitRecord;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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

    @SneakyThrows
    @Override
    public void saveAll() {
        final long start = System.currentTimeMillis();

        String sql = """
        INSERT INTO players (player_name, player_uuid, cooldowns)
        VALUES (?, ?, ?)
        ON CONFLICT(player_uuid) DO UPDATE SET
            player_name = excluded.player_name,
            cooldowns = excluded.cooldowns;
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (PlayerRecord playerRecord : this.getPlayerRecords()) {
                pstmt.setString(1, playerRecord.name());
                pstmt.setString(2, playerRecord.uuid().toString());
                pstmt.setString(3, formatCooldowns(playerRecord.kitsCooldowns()));

                pstmt.addBatch();
            }

            pstmt.executeBatch();
        }

        this.getKitVault().getSLF4JLogger().info("Saved all players data in {} millis", System.currentTimeMillis() - start);
    }

    private String formatCooldowns(HashMap<String, Long> cooldowns) {
        StringBuilder sb = new StringBuilder();
        AtomicInteger counter = new AtomicInteger(0);
        cooldowns.forEach((kit, cooldown) -> {
            sb.append(kit).append(":").append(cooldown).append(counter.get() == cooldowns.size() - 1 ? "" : ";");
            counter.incrementAndGet();
        });
        return sb.toString();
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
            if (split.length >= 1) {
                for (String s : split) {
                    String[] splited = s.split(":");
                    if (splited[1] == null) return;
                    cooldowns.put(splited[0], Long.parseLong(splited[1]));
                }
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
