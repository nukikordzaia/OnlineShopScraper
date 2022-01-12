package service;

import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import service.DataCollector;
import service.Service;

import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
	@SneakyThrows
	@Override
	public void run() {
		JsonObject jsonObject = DataCollector.collectData();
		Service.saveObjectsInDB(jsonObject);
	}
}
