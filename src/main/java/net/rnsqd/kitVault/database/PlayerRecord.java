package net.rnsqd.kitVault.database;

import java.util.HashMap;
import java.util.List;

public record PlayerRecord(String name, HashMap<String, Long> kitsCooldowns) {
}
