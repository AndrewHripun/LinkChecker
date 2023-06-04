package backLinksChecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.lang.Thread;

public class BackLinksChecker {

    public static void main(String[] args) {

        ArrayList<String> linksContainList = new ArrayList<String>(); // ������ � ������� ����������� ������ �� ��� ����
        ArrayList<String> doesntContainList = new ArrayList<String>(); // ������ � ������� �� ����������� ������ �� ��� ����

        // ���������� ������ ������, ������� ������, ���������� ������ � �������.
        Scanner scanner = new Scanner(System.in);
        System.out.println("������� ������ ������, ������� ������, ������ � ����� ������. ��� ���������� ������� S:");
        String inputTargetPages = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("S")) {
                System.out.println("������� " + linksContainList.size() + " ������");
                break;
            }
            inputTargetPages += line.trim() + "\n";
        }

        // ���������� ������ ����� ������, ���������� ������ � �������.
        System.out.println("������� ������ ����� ������, ������ � ����� ������. ��� ���������� ������� S:");
        String inputOurPages = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("S")) {
                scanner.close();
                break;
            }
            inputOurPages += line.trim() + ",";
        }

        String[] targetPages = inputTargetPages.split("\n"); // ����� ����� �������
        String[] ourPages = inputOurPages.split(",");

        // �������� ���������� WebDriver ��� Selenium
        System.setProperty("webdriver.gecko.driver", "F:\\FireFox\\geckodriver.exe");
        System.setProperty("webdriver.firefox.bin", "F:\\FireFox\\firefox.exe");

        WebDriver driver = new FirefoxDriver();

        for (String targetPage : targetPages) {
            String url = targetPage.trim();
            // ���������, ���������� �� URL-����� � "https://", � ���� ���, �� ��������� ���.
            if (!url.startsWith("https://")) {
                url = "https://" + url;
            }

            // ���������� Selenium ��� �������� ��������
            driver.get(url);
            try {
                Thread.sleep(6000); // �������� � 4 �������
            } catch (InterruptedException e) {
                e.printStackTrace(); // ��������� ����������
            }


            // �������� ���������� �������� � ������� JSoup
            Document doc = Jsoup.parse(driver.getPageSource());

            // ���� ������ �� ��������
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String linkHref = link.attr("href").trim();
                if (!linkHref.isEmpty()) {
                    int count = 0;
                    for (String ourPage : ourPages) {
                        if (linkHref.contains(ourPage.trim()) && !linkHref.equals(url)) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        linksContainList.add(url);
                    }
                }
            }
        }

        driver.quit(); // ��������� ������� ����� �������������

        // ������ ������
        HashSet<String> linksContainListSet = new HashSet<String>(linksContainList);
        linksContainList = new ArrayList<String>(linksContainListSet);

        for (String link : linksContainList) { // ���������� ������ �����
            System.out.println("������: " + link + " �������� ������ �� ��� ����.");
        }
        for (String link : doesntContainList) { // �� ���������� ������ �����
            System.out.println("������: " + link + " �� �������� ������ �� ��� ����.");
        }

        System.out.println("�����:");
        System.out.println("�������� ������ �� ��� ���� " + linksContainList.size() + " ������.");
        System.out.println("�� �������� ������ �� ��� ���� " + doesntContainList.size() + " ������.");
    }
}
