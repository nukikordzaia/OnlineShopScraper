import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import database.repository.ProductRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) throws InterruptedException, SQLException {

		String chromeDriverPath = "C:\\Users\\AzRy\\Desktop\\selenium\\web_driver\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);

		String url = "https://www.mymarket.ge/ka/search/53/Laptop-kompiuterebi/?Attrs=82.86-138.146-3481.3488.3489.3490.3491-171.180&Brands=42&CatID=53&Page=1&PriceTo=1100";

		openTabs(url);


//
//		ProductRepository repository = new ProductRepository();
//		System.out.println(repository.getAllProduct());
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

	public static void navigateInTabs(WebDriver driver) {
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		tabs.remove(0);
		for (String s : tabs) {
			driver.switchTo().window(s);
			getInfoFromTab(driver);
		}
//
	}

	@SneakyThrows
	public static void getInfoFromTab(WebDriver driver){
		Thread.sleep(20000);
		String price = driver.findElement(By.className("pr-price")).getText();
		String brand = driver.findElement(By.cssSelector("#root > main > div > div.container.mt-32px.mb-40px > div > div > div.d-flex.spec-list.mt-32px.mt-md-72px > div > div > ul > li:nth-child(1) > p")).getText();
		WebElement element = getElement(driver.findElement(By.className("opacity-50")));
		if (element != null) {
			System.out.println(price +" " +brand+ " " + element.getText());
		}
		System.out.println(price +" " +brand+ "" );
	}

	public static WebElement getElement(WebElement element) {
		if(element.getText().equals("პროცესორის ტიპი")) {
			return element;
		}
		return null;
	}
}
