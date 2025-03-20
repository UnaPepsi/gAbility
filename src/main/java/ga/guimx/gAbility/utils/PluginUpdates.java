package ga.guimx.gAbility.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ga.guimx.gAbility.GAbility;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class PluginUpdates {
    public static String getLatestVersion() throws IOException {
        HttpURLConnection conn = ((HttpURLConnection) URI.create("https://api.github.com/repos/UnaPepsi/gAbility/releases/latest").toURL().openConnection());
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept","application/vnd.github+json");
        conn.setDoOutput(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        if (conn.getResponseCode() != 200){
            GAbility.getInstance().getLogger().warning("gAbility couldn't get the latest version of the plugin");
            return GAbility.getInstance().getPluginMeta().getVersion();
        }
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        JsonObject jsonResponse = JsonParser.parseString(content.toString()).getAsJsonObject();
        return jsonResponse.get("tag_name").getAsString();
    }
}
