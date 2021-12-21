import org.h2.jdbcx.JdbcDataSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) throws InterruptedException, SQLException {
//		String chromeDriverPath = "C:\\Users\\AzRy\\Desktop\\web_driver\\chromedriver.exe";
//		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
//
//		WebDriver driver = new ChromeDriver();
//		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//
//		driver.get("https://www.mymarket.ge/ka/search/53/Laptop-kompiuterebi/?Attrs=82.86-138.146-3481.3488.3489.3490.3491-171.180&Brands=42&CatID=53&Page=1&PriceTo=1100");
//		driver.manage().window().maximize();
//		WebElement openTabs = driver.findElement(By.className("search-content-cards"));
//		int tabsCount = openTabs.findElements(By.tagName("a")).size();
//
//		for (int i = 0; i < tabsCount; i++) {
//			String openTabsAgain = Keys.chord(Keys.CONTROL, Keys.ENTER);
//			openTabs.findElements(By.tagName("a")).get(i).sendKeys(openTabsAgain);
//		}
//
////		String navigateInTabs = Keys.chord(Keys.CONTROL, Keys.TAB);
//		ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
//
//		driver.switchTo().window(tabs2.get(2));
//
//		for (String s : tabs2) {
//			driver.switchTo().window(s);
//			Thread.sleep(5000);
//		}
//
//
	}
}
