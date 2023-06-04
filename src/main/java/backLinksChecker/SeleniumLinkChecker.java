package backLinksChecker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import java.time.Duration;
import java.io.File;


import java.util.List;

public class SeleniumLinkChecker {
    private WebDriver driver;

    public SeleniumLinkChecker() { //конструктор класса инициализирующий объект
        System.setProperty("webdriver.gecko.driver", "F:\\FireFox\\geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(new FirefoxBinary(new File("F:\\FireFox\\firefox.exe"))); //указываем путь к Firefox браузеру
        this.driver = new FirefoxDriver(firefoxOptions); //сохран€ем его в переменной
    }
    
    
  public void checkLink(String link, String url) { //метод которым провер€ем ссылки переход€ по урлам. link мы открываем, а url мы ищем
  this.driver.get(link); //драйвером открываем ссылки
  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //«апускаем драйвер дава€ 10 секунд дл€ поиска на 1 страницу
  
  
    try {
    	wait.until (ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='" + url + "']")));
    	String status = url + " is valid";
    	System.out.println(status);
    } catch (TimeoutException e) {
    	String status = url + " is broken";
        System.out.println(status);
    }
  }
  
//  List<WebElement> links = this.driver.findElements(By.xpath("//a[@href]")); //ищем ссылки
//  for (WebElement elem : links) { //перебор всех найденных ссылок и поиск совпадений с задаными ссылками
//      if (elem.getAttribute("href").equals(url)) {
//          String status = elem.getAttribute("href") + " is valid";
//          System.out.println(status);
//          return;
//      }
//  }
  
//  String status = url + " is broken";
//  System.out.println(status);
//}

    public void checkLinks(List<String> links, String url) {
        for (String link : links) {
            checkLink(link, url);
        }
        this.driver.quit(); //закрываем драйвер после проверки ссылок
    }
}
