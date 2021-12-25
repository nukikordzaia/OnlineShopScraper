import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Main {
	public static void main(String[] args) {
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			try {
				URL url = new URL("https://api2.mymarket.ge/api/ka/products");
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);

				Map<String, String> arguments = new HashMap<>();
				arguments.put("Attrs", "82.87.86-138.146");
				arguments.put("Brands", "42");
				arguments.put("CatID", "53");
				arguments.put("Limit", "26");
				arguments.put("Page", "1");
				arguments.put("PriceFrom", "800");
				arguments.put("PriceTo", "1000");
				arguments.put("SortID", "1");
				StringJoiner sj = new StringJoiner("&");
				for (Map.Entry<String, String> entry : arguments.entrySet())
					sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
						+ URLEncoder.encode(entry.getValue(), "UTF-8"));
				byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
				int length = out.length;
				connection.setFixedLengthStreamingMode(length);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				connection.connect();
				try (OutputStream os = connection.getOutputStream()) {
					os.write(out);
				}

				connection.setConnectTimeout(10000);
				connection.setReadTimeout(10000);

				int status = connection.getResponseCode();
				if (status > 299) {
					reader = new BufferedReader((new InputStreamReader(connection.getErrorStream())));
					while ((line = reader.readLine()) != null) {
						responseContent.append(line);
					}
					reader.close();
				} else {
					reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					while ((line = reader.readLine()) != null) {
						responseContent.append(line);
					}
					reader.close();
				}
				System.out.println(responseContent);
				JsonObject jsonObject = new JsonParser().parse(String.valueOf(responseContent)).getAsJsonObject();
				System.out.println(jsonObject.getAsJsonObject("data").getAsJsonArray("Prs").get(0));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}
}
