package backLinksChecker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.io.File;




import java.util.List;

public class SeleniumLinkChecker {
	private WebDriver driver;

	 public SeleniumLinkChecker() { //конструктор класса инициализирующий объект
	        System.setProperty("webdriver.gecko.driver", "F:\\FireFox\\geckodriver.exe");
	        FirefoxBinary firefoxBinary = new FirefoxBinary();
	        firefoxBinary.setExecutable(new File("F:\\FireFox\\firefox.exe")); //указываем путь к Firefox браузеру
	        FirefoxOptions firefoxOptions = new FirefoxOptions();
	        firefoxOptions.setBinary(firefoxBinary);
	        this.driver = new FirefoxDriver(); //сохраняем его в переменной
	    }

	    public void checkLink(String link, String url) { //метод которым проверяем ссылки переходя по урлам
	        this.driver.get(link); //драйвером открываем ссылки
	        List<WebElement> links = this.driver.findElements(By.xpath("//a[@href]")); //ищем ссылки
	        for (WebElement elem : links) { //перебор всех найденных ссылок и поиск совпадений с задаными ссылками
	            if (elem.getAttribute("href").equals(url)) {
	                String status = elem.getAttribute("href") + " is valid";
	                System.out.println(status);
	                return;
	            }
	        }
	        String status = url + " is broken";
	        System.out.println(status);
	    }

	    public void checkLinks(List<String> links, String url) {
	        for (String link : links) {
	            checkLink(link, url);
	        }
	    }
	}