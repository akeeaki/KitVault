package net.rnsqd.kitVault.database;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public record PlayerRecord(String name, UUID uuid, HashMap<String, Long> kitsCooldowns) {
}
