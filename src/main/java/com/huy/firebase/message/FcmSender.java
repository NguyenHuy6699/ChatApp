package com.huy.firebase.message;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.google.auth.oauth2.GoogleCredentials;
import com.huy.constant.AppConstants;

public class FcmSender {
	private static String getAccessToken() throws Exception {
		GoogleCredentials googleCredentials = GoogleCredentials.getApplicationDefault()
				.createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
		googleCredentials.refresh();
		return googleCredentials.getAccessToken().getTokenValue();
	}

	public static String sendMessage(String title, String message, String senderUserName, String fcmToken) throws Exception {
		String accessToken = getAccessToken();

		URL url = new URL("https://fcm.googleapis.com/v1/projects/" + AppConstants.fcm_project_id + "/messages:send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Bearer" + accessToken);
		conn.setRequestProperty("Content-Type", "application/json; UTF-8");
		conn.setDoOutput(true);

		String payload = """
				{
				          "message": {
				            "token": "%s",
				            "notification": {
				              "title": "%s",
				              "body": "%s"
				            },
				            "data": {
				              "chatId": "123",
				              "senderId": "%s"
				            }
				          }
				        }
				""".formatted(fcmToken, title, message, senderUserName);
		
		try (OutputStream os = conn.getOutputStream()) {
			os.write(payload.getBytes(StandardCharsets.UTF_8));
		}
		
		int responseCode = conn.getResponseCode();
		return null;
	}
}
