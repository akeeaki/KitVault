package net.rnsqd.kitVault.updatechecker;

import com.google.gson.annotations.SerializedName;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.rnsqd.kitVault.KitVault;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@UtilityClass
public final class UpdateChecker {
    @SneakyThrows
    public static CheckUpdateResultInstance checkUpdate(final KitVault plugin) {
        if (plugin == null) return null;

        HttpURLConnection getConnection = (HttpURLConnection) new URL("https://raw.githubusercontent.com/akeeaki/KitVault/refs/heads/master/info/VersionInfo.json").openConnection();
        getConnection.setRequestMethod("GET");
        getConnection.setRequestProperty("Content-Type", "application/json");
        getConnection.setConnectTimeout(5000);
        getConnection.setReadTimeout(5000);

        int responseCode = getConnection.getResponseCode();
        if (responseCode != 200 && responseCode != 204) {
            return null;
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getConnection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) response.append(line);
        }

        GetResult result = plugin.getJsonConvert().gson.fromJson(response.toString(), GetResult.class);
        final String fromDesc = plugin.getDescription().getVersion();
        return new CheckUpdateResultInstance(fromDesc, result.latestVersion(), (!fromDesc.equals(result.latestVersion()) ? CheckUpdateResult.LAGGING_VERSION : CheckUpdateResult.LATEST));
    }

    public record GetResult(@SerializedName(value = "latest-version") String latestVersion) { }

    public record CheckUpdateResultInstance(String version, String latestVersion, CheckUpdateResult result) {}

    public enum CheckUpdateResult {
        LATEST, LAGGING_VERSION
    }
}
