package service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class DataCollector {

	private static HttpURLConnection connection;
	private static BufferedReader reader;
	private static String line;
	private static StringBuilder responseContent = new StringBuilder();

	public static JsonObject collectData() throws IOException {
		URL url2 = new URL("https://api2.mymarket.ge/api/ka/products");
		connection = (HttpURLConnection) url2.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		createFilters("82.87.86-138.146","42", "53", "26", "1",
			"1000", "1");
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(10000);

		int status = connection.getResponseCode();
		if (status > 299) {
			reader = new BufferedReader((new InputStreamReader(connection.getErrorStream())));
		} else {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		}
		while ((line = reader.readLine()) != null) {
			responseContent.append(line);
		}
		reader.close();

		return new JsonParser().parse(String.valueOf(responseContent)).getAsJsonObject();
	}

	private static void createFilters(String attrs, String brands, String catID, String limit, String page,
									  String priceTo, String sortID) throws IOException {
		Map<String, String> arguments = new HashMap<>();
		//putting filters
		arguments.put("Attrs", attrs);
		arguments.put("Brands", brands);
		arguments.put("CatID", catID);
		arguments.put("Limit", limit);
		arguments.put("Page", page);
		arguments.put("PriceTo", priceTo);
		arguments.put("SortID", sortID);
		StringJoiner sj = new StringJoiner("&");
		for (Map.Entry<String, String> entry : arguments.entrySet())
			sj.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
				+ URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
		byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
		int length = out.length;
		connection.setFixedLengthStreamingMode(length);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		connection.connect();
		try (OutputStream os = connection.getOutputStream()) {
			os.write(out);
		}
	}
}
