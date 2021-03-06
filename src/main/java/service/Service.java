package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import database.model.Product;
import database.model.ProductOwner;
import database.repository.ProductOwnerRepository;
import database.repository.ProductRepository;

import javax.mail.MessagingException;

public class Service {

	private static ProductRepository productRepository = new ProductRepository();
	private static ProductOwnerRepository ownerRepository = new ProductOwnerRepository();

	public static void saveObjectsInDB(JsonObject jsonObject) throws MessagingException {
		JsonArray arr = jsonObject.getAsJsonObject("data").getAsJsonArray("Prs");
		saveProduct(arr);
		saveProductOwner(arr);

	}

	private static void saveProduct(JsonArray arr) throws MessagingException {
		String recipient = "exampleMail";
		for (JsonElement element : arr) {
			JsonObject obj = element.getAsJsonObject();
			String product_id = obj.get("product_id").getAsString();
			String title = obj.getAsJsonObject().get("title").getAsString();
			String description = obj.getAsJsonObject().get("descr").getAsString();
			String price = obj.getAsJsonObject().get("price").getAsString();
			String link = "https://www.mymarket.ge/ka/pr/" + product_id;
			Product product = new Product(product_id, title, description, price, link);

			if (productRepository.isProductUnique(product)) {
				productRepository.saveProduct(product);
				MailSender.sendMail(recipient, product.getTitle(), product.getLink());
			}
		}
	}

	private static void saveProductOwner(JsonArray arr) {
		for (JsonElement element : arr) {
			String user_id = element.getAsJsonObject().get("user_id").getAsString();
			String username = element.getAsJsonObject().get("username").getAsString();
			ProductOwner productOwner = new ProductOwner(user_id, username, "");

			if (ownerRepository.isProductOwnerUnique(productOwner)) {
				ownerRepository.saveProductOwner(productOwner);
			}
		}
	}
}
