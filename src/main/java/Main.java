import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Main {
	public static void main(String[] args) {
		String searchQuery = "iphone";
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
//		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setThrowExceptionOnScriptError(false);

		try {
			String searchUrl = "https://www.mymarket.ge/ka/search/1064/iyideba-teqnika/?CatID=1064&Keyword="+searchQuery+"%20x";
			HtmlPage page = client.getPage(searchUrl);
			System.out.println(page.asXml());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
