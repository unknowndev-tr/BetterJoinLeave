package tr.unknown.betterJL;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordWebhookSender {

    private final String webhookUrl;

    public DiscordWebhookSender(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public void sendEmbedMessage(String title, String description, String footer) {
        try {

            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{"
                    + "\"embeds\": [{"
                    + "\"title\": \"" + title + "\","
                    + "\"description\": \"" + description + "\","
                    + "\"color\": 3447003,"
                    + "\"footer\": {"
                    + "\"text\": \"" + footer + "\""
                    + "}"
                    + "}]"
                    + "}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 204) {
                System.out.println("Webhook mesajı başarıyla gönderildi!");
            } else {
                System.out.println("Webhook mesajı gönderilemedi. Yanıt kodu: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}