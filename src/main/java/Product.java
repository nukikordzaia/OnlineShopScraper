public class Product {
	private String link;
	private String brand;
	private String processorType;
	private int ramSize;
	private int ssdSize;
	private int price;

	public Product(){}

	public Product(String link, String brand, String processorType, int ramSize, int ssdSize, int price) {
		this.link = link;
		this.brand = brand;
		this.processorType = processorType;
		this.ramSize = ramSize;
		this.ssdSize = ssdSize;
		this.price = price;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProcessorType() {
		return processorType;
	}

	public void setProcessorType(String processorType) {
		this.processorType = processorType;
	}

	public int getRamSize() {
		return ramSize;
	}

	public void setRamSize(int ramSize) {
		this.ramSize = ramSize;
	}

	public int getSsdSize() {
		return ssdSize;
	}

	public void setSsdSize(int ssdSize) {
		this.ssdSize = ssdSize;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product{" +
			"link='" + link + '\'' +
			", brand='" + brand + '\'' +
			", processorType='" + processorType + '\'' +
			", ramSize=" + ramSize +
			", ssdSize=" + ssdSize +
			", price=" + price +
			'}';
	}
}
