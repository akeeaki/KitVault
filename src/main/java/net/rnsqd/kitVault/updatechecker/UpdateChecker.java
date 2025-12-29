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

/**
 * Эйо, я люблю деньги, шлюх и наркоту
 * Я всегда с собой беру эту дрянь, рулю игру
 * И я играю как в аду
 * И я ебу свою мечту
 * И я вопру, как негра друг, плюс мой стайл крут, а твой стайл туп
 * И я стою тут так, как дуб
 * А мой хуй — прут твоих подруг
 * И мои треки всех их прут
 * Я еду в блуд, беру их дур
 * Их прёт мой дар — кричат Ура
 * Я Веро-младший, я Jr
 * Янг Трэппа — supa stupid star
 * Когда я прав, твой ход — пиар
 * И я кладу нал-нал на нал, а твой — провал, и твой подвал туда
 * Давно тебя забрал, теперь тут я и мой отвар
 * Дури, дряни, проституток, шмоток Луи, быстрых муток
 * Долгих суток — это глупо, но мне круто-круто-круто-круто!
 * Эйо всем вам, суки, чё за мутки? Я не в курсе.
 * И я люблю суши, курю плюшки, бой, я лучший
 * Мой стиль — пушка, хоуми, лучше открой уши
 * Твоя сука — строго булщит
 * Я деру её под душем, скорость Порша, она булщит!
 * Бой, мой стиль — ток, я вам бью потолок
 * Он потёк будто сок, от моих саб-басов
 * Мой стакан — там сироп, за спиною — мой блок
 * Техноложка, сынок, техноложка, сынок!
 * На лице очки: они скрывают зрачки
 * Я вскрываю мозги — вам нужны врачи
 * Это Трэппа кто? Трэппа YMB
 * Со мной мой хоуми — Веробитз!
 * Это Янг Трэппа в хлам, мусорам — шляпой по губам
 * Всем копам — Аха-ха-ха-ха
 * Они не хапнут, на квартале, курю скрученные бланты
 * Мои парни — растамафия твои мами — в ахуе
 * Знаешь, я к вам пришёл с нуля, но щас дохуя, да и я...
 * Бейпов, треков, бэгов, левый суэг — нет
 * Я включу свет и расчерчу вам снег
 * Объясню вам сленг и приведу вас в трэп
 * Я приведу вас там, где пи-пи-пи-пиздец
 * Там, где мусора, там, где много таблеток и пиздец жара
 * Каждый день недели крутим деньги — вот такая дрянь
 * Бейби, ты поверь мне лучше, верь мне — это Янг Трэппа-Трэппа
 * Я — прямиком из гетто, из клуба, где много таблеток
 * Я буду делать этих девок
 * Я буду делать этих целок-целок-целок-целок
 *
 * Author: don1x
 */
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
