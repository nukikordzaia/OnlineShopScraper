package database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private String link;
	private String brand;
	private String processorType;
	private int ramSize;
	private int ssdSize;
	private int price;
}
