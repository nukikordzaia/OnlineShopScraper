import java.util.List;

public class ProductOwner {
	private String name;
	private int phoneNumber;
	private List<Product> products;

	public ProductOwner() {}

	public ProductOwner(String name, int phoneNumber, List<Product> products) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.products = products;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "ProductOwner{" +
			"name='" + name + '\'' +
			", phoneNumber=" + phoneNumber +
			", products=" + products +
			'}';
	}
}
