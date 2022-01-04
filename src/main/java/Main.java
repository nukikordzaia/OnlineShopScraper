import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import database.model.Product;
import database.model.ProductOwner;
import database.repository.ProductOwnerRepository;
import database.repository.ProductRepository;

import javax.mail.MessagingException;
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
	public static void main(String[] args) throws MessagingException {
		ProductRepository productRepository = new ProductRepository();
		ProductOwnerRepository ownerRepository = new ProductOwnerRepository();
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		try {
			try {
				URL url2 = new URL("https://api2.mymarket.ge/api/ka/products");
				connection = (HttpURLConnection) url2.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);

				Map<String, String> arguments = new HashMap<>();
				arguments.put("Attrs", "82.87.86-138.146");
				arguments.put("Brands", "42");
				arguments.put("CatID", "53");
				arguments.put("Limit", "26");
				arguments.put("Page", "1");
				arguments.put("PriceTo", "1000");
				arguments.put("SortID", "1");
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

				JsonObject jsonObject = new JsonParser().parse(String.valueOf(responseContent)).getAsJsonObject();
				saveObjectsInDB(jsonObject, productRepository, ownerRepository);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	private static void saveObjectsInDB(JsonObject jsonObject, ProductRepository productRepository, ProductOwnerRepository ownerRepository) throws MessagingException {
		JsonArray arr = jsonObject.getAsJsonObject("data").getAsJsonArray("Prs");
		for (int i = 0; i < arr.size(); i++) {
			String user_id = arr.get(i).getAsJsonObject().get("user_id").getAsString();
			String username = arr.get(i).getAsJsonObject().get("username").getAsString();
			ProductOwner productOwner = new ProductOwner(user_id, username, "");

			if (ownerRepository.isProductOwnerUnique(productOwner)) {
				ownerRepository.saveProductOwner(productOwner);
			}

			String product_id = arr.get(i).getAsJsonObject().get("product_id").getAsString();
			String title = arr.get(i).getAsJsonObject().get("title").getAsString();
			String description = arr.get(i).getAsJsonObject().get("descr").getAsString();
			String price = arr.get(i).getAsJsonObject().get("price").getAsString();
			String link = "https://www.mymarket.ge/ka/pr/" + product_id;
			Product product = new Product(product_id, title, description, price, link);

			if (productRepository.isProductUnique(product)) {
				productRepository.saveProduct(product);
				MailSender.sendMail("exampleMail", product.getTitle(), product.getLink());
			}
		}
	}
}
