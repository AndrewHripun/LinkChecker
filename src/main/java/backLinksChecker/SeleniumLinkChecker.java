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

	 public SeleniumLinkChecker() { //����������� ������ ���������������� ������
	        System.setProperty("webdriver.gecko.driver", "F:\\FireFox\\geckodriver.exe");
	        FirefoxBinary firefoxBinary = new FirefoxBinary();
	        firefoxBinary.setExecutable(new File("F:\\FireFox\\firefox.exe")); //��������� ���� � Firefox ��������
	        FirefoxOptions firefoxOptions = new FirefoxOptions();
	        firefoxOptions.setBinary(firefoxBinary);
	        this.driver = new FirefoxDriver(); //��������� ��� � ����������
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