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

        ArrayList<String> linksContainList = new ArrayList<String>(); // массив с сайтами содержащими ссылки на наш сайт
        ArrayList<String> doesntContainList = new ArrayList<String>(); // массив с сайтами НЕ содержащими ссылки на наш сайт

        // Считывание ссылок сайтов, которые парсим, считывание ссылок в столбик.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ссылки сайтов, которые парсим, каждую с новой строки. Для завершения введите S:");
        String inputTargetPages = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("S")) {
                System.out.println("Введено " + linksContainList.size() + " ссылок");
                break;
            }
            inputTargetPages += line.trim() + "\n";
        }

        // Считывание ссылок наших сайтов, считывание ссылок в столбик.
        System.out.println("Введите ссылки наших сайтов, каждую с новой строки. Для завершения введите S:");
        String inputOurPages = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("S")) {
                scanner.close();
                break;
            }
            inputOurPages += line.trim() + ",";
        }

        String[] targetPages = inputTargetPages.split("\n"); // Здесь задаём массивы
        String[] ourPages = inputOurPages.split(",");

        // Создание экземпляра WebDriver для Selenium
        System.setProperty("webdriver.gecko.driver", "F:\\FireFox\\geckodriver.exe");
        System.setProperty("webdriver.firefox.bin", "F:\\FireFox\\firefox.exe");

        WebDriver driver = new FirefoxDriver();

        for (String targetPage : targetPages) {
            String url = targetPage.trim();
            // Проверяем, начинается ли URL-адрес с "https://", и если нет, то добавляем его.
            if (!url.startsWith("https://")) {
                url = "https://" + url;
            }

            // Используем Selenium для открытия страницы
            driver.get(url);
            try {
                Thread.sleep(6000); // Задержка в 4 секунды
            } catch (InterruptedException e) {
                e.printStackTrace(); // Обработка исключения
            }


            // Получаем содержимое страницы с помощью JSoup
            Document doc = Jsoup.parse(driver.getPageSource());

            // Ищем ссылки на странице
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

        driver.quit(); // Закрываем браузер после использования

        // Чистка дублей
        HashSet<String> linksContainListSet = new HashSet<String>(linksContainList);
        linksContainList = new ArrayList<String>(linksContainListSet);

        for (String link : linksContainList) { // Содержащие ссылку сайты
            System.out.println("Ссылка: " + link + " содержит ссылку на наш сайт.");
        }
        for (String link : doesntContainList) { // НЕ содержащие ссылку сайты
            System.out.println("Ссылка: " + link + " не содержит ссылку на наш сайт.");
        }

        System.out.println("Итого:");
        System.out.println("Содержит ссылку на наш сайт " + linksContainList.size() + " сайтов.");
        System.out.println("НЕ содержит ссылку на наш сайт " + doesntContainList.size() + " сайтов.");
    }
}
