import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import database.model.Product;
import database.model.ProductOwner;
import database.repository.ProductOwnerRepository;
import database.repository.ProductRepository;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		ProductRepository productRepository = new ProductRepository();
		ProductOwnerRepository ownerRepository = new ProductOwnerRepository();
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		String chromeDriverPath = "C:\\Users\\AzRy\\Desktop\\selenium\\web_driver\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		String url = "https://www.mymarket.ge/ka/search/53/Laptop-kompiuterebi/?Attrs=82.86-138.146-3481.3488.3489.3490.3491-171.180&Brands=42&CatID=53&Page=1&PriceTo=1000";
		openTabs(url);
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


	public static void openTabs(String url) {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(url);
		WebElement tabs = driver.findElement(By.className("search-content-cards"));
		int tabsCount = tabs.findElements(By.tagName("a")).size();

		for (int i = 0; i < tabsCount; i++) {
			String openTabsAgain = Keys.chord(Keys.CONTROL, Keys.ENTER);
			tabs.findElements(By.tagName("a")).get(i).sendKeys(openTabsAgain);
		}
		navigateInTabs(driver);
	}

	@SneakyThrows
	public static void getInfoFromTab(WebDriver driver) {
		Thread.sleep(20000);
		WebElement element = driver.findElement(By.className("show-number"));
		element.click();
		String phoneNumber = driver.findElement(By.className("opened-number")).getText();
		System.out.println(phoneNumber);
	}

	public static void navigateInTabs(WebDriver driver) {
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		tabs.remove(0);
		for (String s : tabs) {
			driver.switchTo().window(s);
			getInfoFromTab(driver);
		}
	}

	private static void saveObjectsInDB(JsonObject jsonObject, ProductRepository productRepository, ProductOwnerRepository ownerRepository) {
		JsonArray arr = jsonObject.getAsJsonObject("data").getAsJsonArray("Prs");
		for (int i = 0; i < arr.size(); i++) {
			String product_id = arr.get(i).getAsJsonObject().get("product_id").getAsString();
			String title = arr.get(i).getAsJsonObject().get("title").getAsString();
			String description = arr.get(i).getAsJsonObject().get("descr").getAsString();
			String price = arr.get(i).getAsJsonObject().get("price").getAsString();

			String user_id = arr.get(i).getAsJsonObject().get("user_id").getAsString();
			String username = arr.get(i).getAsJsonObject().get("username").getAsString();
			Product product = new Product(product_id, title, description, price);
			productRepository.saveProduct(product);
			ProductOwner productOwner = new ProductOwner(user_id, username, "");
			ownerRepository.saveProductOwner(productOwner);
		}
	}
}
