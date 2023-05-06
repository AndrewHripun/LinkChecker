package backLinksChecker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class SeleniumLinkChecker {
	private WebDriver driver;

	 public SeleniumLinkChecker() { //����������� ������ ���������������� ������
	        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
	        this.driver = new ChromeDriver(); //��������� ��� � ����������
	    }

	    public void checkLink(String link, String url) { //����� ������� ��������� ������ �������� �� �����
	        this.driver.get(link); //��������� ��������� ������
	        List<WebElement> links = this.driver.findElements(By.xpath("//a[@href]")); //���� ������
	        for (WebElement elem : links) { //������� ���� ��������� ������ � ����� ���������� � �������� ��������
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