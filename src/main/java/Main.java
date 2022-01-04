import com.google.gson.JsonObject;
import service.DataCollector;
import service.Service;

import javax.mail.MessagingException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws MessagingException, IOException {
		JsonObject jsonObject = DataCollector.collectData();
		Service.saveObjectsInDB(jsonObject);
	}
}
