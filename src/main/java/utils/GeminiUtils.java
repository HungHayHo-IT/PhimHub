package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GeminiUtils {

    private static final String API_KEY = "AIzaSyAr8AuKD1WqjqZU6Osrs2RXdiXapO_JmL0"; 
    private static final String MODEL_NAME = "gemini-1.5-flash";
    private static final String API_URL =
    	    "https://generativelanguage.googleapis.com/v1/models/"
    	    + MODEL_NAME + ":generateContent?key=" + API_KEY;


    public static String getResponse(String question) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(API_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoOutput(true);

            // JSON đúng của Gemini v1beta
            String payload = """
                {
                  "contents": [{
                    "parts": [{
                      "text": "%s"
                    }]
                  }]
                }
                """.formatted(question.replace("\"", "\\\""));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();

            InputStream stream = (responseCode >= 200 && responseCode < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            if (responseCode != 200) {
                System.out.println("❌ LỖI API GEMINI (" + responseCode + "): " + response);
                return "Lỗi kết nối AI: " + responseCode;
            }

            return parseGeminiResponse(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return "Hệ thống đang bận (Lỗi Java: " + e.getMessage() + ")";
        }
    }

    private static String parseGeminiResponse(String json) {
        try {
            Gson gson = new Gson();
            JsonObject obj = gson.fromJson(json, JsonObject.class);

            JsonArray candidates = obj.getAsJsonArray("candidates");
            if (candidates != null && candidates.size() > 0) {
                JsonObject content = candidates.get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("content");

                JsonArray parts = content.getAsJsonArray("parts");
                return parts.get(0).getAsJsonObject().get("text").getAsString();
            }

            return "AI không trả lời được.";
        } catch (Exception e) {
            System.out.println("❌ LỖI PARSE JSON: " + e.getMessage());
            return "Lỗi đọc dữ liệu từ AI.";
        }
    }
}
