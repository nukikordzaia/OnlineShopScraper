import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.DataCollector;

public class DataCollectorTest {

	@Test
	public void createFiltersTest(){
		String expected = "https://www.mymarket.ge/ka/search/53/Laptop-kompiuterebi/?PriceTo=1000&Attrs=82.87.86-138.146&Brands=42&Page=1&SortID=1&Limit=26&CatID=53";
		String actual = "https://www.mymarket.ge/ka/search/53/Laptop-kompiuterebi/?"+DataCollector.createFilters("82.87.86-138.146", "42", "53", "26", "1",
			"1000", "1");
		Assertions.assertEquals(expected, actual);
	}
}
