package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public static int timestamp = (int) (new Date().getTime()/1000);
	
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/config/config.properties");
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() {
		TimeZone.setDefault(TimeZone.getTimeZone("Australia/Sydney"));
		String browserName = prop.getProperty("browser");
		if (browserName.equals("chrome")) {
			//System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("start-maximized");
			chromeOptions.addArguments("--window-size=1920,1080");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			driver = new ChromeDriver(chromeOptions);
			System.out.println("Browser Open");
		} else if (browserName.equals("FF")) {
			System.setProperty("webdriver.gecko.driver", "");
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
	}
}
