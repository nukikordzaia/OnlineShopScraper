package database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOwner {

	private String userID;
	private String username;
	private String productList;

	public ProductOwner(String userID, String username) {
		this.userID = userID;
		this.username = username;
	}
}
