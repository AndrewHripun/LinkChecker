package backLinksChecker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class SeleniumLinkChecker {

	String seleniumUrl = "";
	
	//путь к драйверу
	 System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Applicationchromedriver");
	 
	 // —оздать экземпл€р WebDriver дл€ Chrome
     WebDriver driver = new ChromeDriver();

     // ќткрыть страницу
     driver.get(url);
}
