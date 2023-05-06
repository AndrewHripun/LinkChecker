package backLinksChecker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class SeleniumLinkChecker {
	private WebDriver driver;

	 public SeleniumLinkChecker() { //конструктор класса инициализирующий объект
	        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
	        this.driver = new ChromeDriver(); //сохраняем его в переменной
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