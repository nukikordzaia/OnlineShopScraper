import service.ScheduledTask;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
	public static void main(String[] args) {
		long period = 1000L * 60L * 60L * 24L;
		long delay = 0;
		Timer timer = new Timer();
		TimerTask parseWebSite = new ScheduledTask();
		timer.schedule(parseWebSite, delay, period);
	}
}
