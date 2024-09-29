package org.cptgummiball.sshchecker;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class SSHChecker extends JavaPlugin {


    @Override
    public void onEnable() {
        saveDefaultConfig();

        long INTERVAL = getConfig().getLong("ssh.interval", 72000L);
        // Start the repeating task based on the configured interval (in ticks, 20 ticks = 1 second)
        new BukkitRunnable() {
            @Override
            public void run() {
                checkSSH();
            }
        }.runTaskTimerAsynchronously(this, 0L, INTERVAL); // 72000 Ticks = 60 Minuten
    }

    private void checkSSH() {
        try {
            // get SSH login data
            String SSH_HOST = getConfig().getString("ssh.host", "localhost");
            int SSH_PORT = getConfig().getInt("ssh.port", 22);
            String SSH_USER = getConfig().getString("ssh.user", "root");
            String SSH_PASSWORD = getConfig().getString("ssh.password", "");
            int SSH_TIMEOUT = getConfig().getInt("ssh.timeout", 5000);

            // Initialize JSch for SSH
            JSch jsch = new JSch();
            Session session = jsch.getSession(SSH_USER, SSH_HOST, SSH_PORT);

            // Set SSH password
            session.setPassword(SSH_PASSWORD);

            // Avoid host key verification (not recommended for production!)
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // Connect to SSH server
            session.connect(SSH_TIMEOUT);

            // If connection successful
            getLogger().info("SSH Server " + SSH_HOST + " is reachable.");

            // Close the session
            session.disconnect();
        } catch (Exception e) {
            boolean DISCORD_WEBHOOK_ACTIVE = getConfig().getBoolean("discord.use_webhook", false);
            String SSH_HOST = getConfig().getString("ssh.host", "localhost");

            // If the connection fails, send a message to Console and optionally to the Discord webhook
            if (DISCORD_WEBHOOK_ACTIVE) {
                getLogger().info("SSH Server " + SSH_HOST + " cannot be reached.");
                sendDiscordMessage("Error: SSH server  " + SSH_HOST + " cannot be reached. Error message: " + e.getMessage());
            }else{
                getLogger().info("SSH Server " + SSH_HOST + " cannot be reached.");
            }
        }
    }

    private void sendDiscordMessage(String message) {
        try {
            // Get webhook url
            String DISCORD_WEBHOOK_URL = getConfig().getString("discord.webhook_url", "");

            // Establish URL connection
            URL url = new URL(DISCORD_WEBHOOK_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // JSON data for Discord
            String jsonPayload = "{\"content\":\"" + message + "\"}";

            // Send data
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Check the answer
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                getLogger().info("Error message successfully sent to Discord.");
            } else {
                getLogger().warning("Error sending message to Discord: " + responseCode);
            }

        } catch (Exception e) {
            getLogger().warning("Error sending Discord message: " + e.getMessage());
        }
    }
}