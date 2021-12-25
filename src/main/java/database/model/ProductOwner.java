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
	private String phoneNumber;
	private String productList;
}
