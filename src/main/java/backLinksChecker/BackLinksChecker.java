package backLinksChecker;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;


public class BackLinksChecker {
	
	public static void main(String[] args) {
		
		int inputCount = -1;
		ArrayList<String> linksContainList = new ArrayList<String>(); //массив с сайтами содержащими ссылки на наш сайт
		ArrayList<String> timeoutContainList = new ArrayList<String>(); //массив с сайтами показавшими timeout при подключении
		ArrayList<String> protectedLinksContainList = new ArrayList<String>(); //массив с сайтами защищёнными от парсинга
		ArrayList<String> uncorrectLinksContainList = new ArrayList<String>(); //массив с сайтами содержащими некорректный URL
		ArrayList<String> cannotUseLinksContainList = new ArrayList<String>(); //не удалось получить доступ к сайту
		ArrayList<String> errorLinksContainList = new ArrayList<String>(); //Произошла неизвестная ошибка
		
		
		
		//Считывание ссылок сайтов которые парсим, считывание ссылок в столбик.
		Scanner scanner = new Scanner(System.in);
		System.out.println("Введите ссылки сайтов на которых парсим, каждую с новой строки, чтобы закончить ввод напишите S: ");
		String inputTargetPages = "";
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			inputCount++;
			if (line.equals("S")) {
				System.out.println("Введено " + inputCount + " ссылок");
				break;
			}
			inputTargetPages += line.trim() + "\n";
		}
		//Считывание ссылок наших сайтов, считывание ссылок в столбик.
		System.out.println("Введите ссылки наших сайтов, каждую с новой строки, чтобы закончить ввод напишите S: ");
		String inputOurPages = "";
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.equals("S")) {
				scanner.close();
				break;
			}
			inputOurPages += line.trim() + ",";
		}

		String[] targetPages = inputTargetPages.split("\n"); //Здесь задаём массивы
		String[] ourPages = inputOurPages.split(",");
		
	    for (String targetPage : targetPages) {
	    	String url = targetPage.trim();
            // Проверяем, начинается ли URL-адрес с "https://", и если нет, то добавляем его.
            if (!url.startsWith("https://")) {
                url = "https://" + url;
            }
	    	try {
	        	Document doc = Jsoup.connect(url).get(); //Коннект к страничке
	        	Elements links = doc.select("a[href], div a[href], img[src], iframe[src], span a[href], p a[href]"); 
	            // обрабатываем возможные исключения, связанные с недоступностью ссылок
	            if (links.isEmpty()) {
	                System.out.println("Внимание! На странице " + url + " отсутствуют ссылки!");
	                continue; // переходим к следующей итерации
	            }

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
	                        //System.out.println("Link " + url + " !!!contains!!! " + count + " backlink(s) to " + linkHref);
	                        //System.out.println("Рабочих " + outputCount + " ссылок из " + inputCount);
	                    	linksContainList.add(url);
	                    }    else {
	                    	
	                        // ПРИКРЫТО System.out.println("Link " + url + " does not contain a backlink to " + linkHref);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            String errorMessage = e.getMessage();
	            // обрабатываем возможные ошибки соединения
	            if (errorMessage.contains("403") || errorMessage.contains("429")) {
	                //System.out.println("Сайт " + url + " защищён от парсинга.");
	                protectedLinksContainList.add(url);
	            } else if (errorMessage.contains("timeout")) {
	                //System.out.println("Не удалось установить соединение с сайтом " + url + ". Время ожидания истекло.");
	            	timeoutContainList.add(url);
	            } else {
	                //System.out.println("Не удалось получить доступ к " + url + ": " + e.getMessage());
	                cannotUseLinksContainList.add(url);
	            }
	            continue;
	        } catch (IllegalArgumentException e) {
	            //System.out.println("Некорректный URL: " + e.getMessage());
	            uncorrectLinksContainList.add(url);
	            continue;
	        } catch (Exception e) {
	            //System.out.println("Произошла неизвестная ошибка: " + e.getMessage());
	            errorLinksContainList.add(url);
	            continue;
	        }
	    } //Конец проверки
	    
	    //чистка дублей
		HashSet<String> linksContainListSet = new HashSet<String>(linksContainList);
		linksContainList = new ArrayList<String>(linksContainListSet);
		HashSet<String> timeoutContainListHashSet = new HashSet<String>(timeoutContainList);
		timeoutContainList = new ArrayList<String>(timeoutContainListHashSet);
		HashSet<String> protectedLinksContainListHashSet = new HashSet<String>(protectedLinksContainList);
		protectedLinksContainList = new ArrayList<String>(protectedLinksContainListHashSet);
		HashSet<String> uncorrectLinksContainListHashSet = new HashSet<String>(uncorrectLinksContainList);
		uncorrectLinksContainList = new ArrayList<String>(uncorrectLinksContainListHashSet);
		HashSet<String> cannotUseLinksContainListHashSet = new HashSet<String>(cannotUseLinksContainList);
		cannotUseLinksContainList = new ArrayList<String>(cannotUseLinksContainListHashSet);
		HashSet<String> errorLinksContainListHashSet = new HashSet<String>(errorLinksContainList);
		errorLinksContainList = new ArrayList<String>(errorLinksContainListHashSet);

		
		int lengthContainListSet = linksContainList.size();
		int lenghtTimeoutContainList = timeoutContainList.size();
		int lengthProtectedLinksContainList = protectedLinksContainList.size();
		int lengthUncorrectLinksContainList = uncorrectLinksContainList.size();
		int lengthCannotUseLinksContainList = cannotUseLinksContainList.size();	
		int lengthErrorLinksContainList = errorLinksContainList.size();
	    
	    for (String link : linksContainList) { //Содержащие ссылку сайты
	        System.out.println("Ссылка: " + link + " содержит ссылку на наш сайт.");
	    }
	    for (String link : timeoutContainList) { //Таймаут сайты
	        System.out.println("Ссылку: " + link + " невозможно проверить из-за таймаута при подключении.");
	    }
	    for (String link : protectedLinksContainList) { //Защищённые от парсинга сайты 
	        System.out.println("Ссылку: " + link + " невозможно проверить из-за защиты от парсинга.");
	    }
	    for (String link : uncorrectLinksContainList) { //Некорректный URL при вводе
	        System.out.println("Ссылку: " + link + " невозможно проверить из-за некорректно введённого URL.");
	    }
	    for (String link : cannotUseLinksContainList) { //Не удалось получить доступ
	        System.out.println("Ссылку: " + link + " невозможно проверить из-за того, что не удалось получить доступ к сайту.");
	    }
	    for (String link : errorLinksContainList) { //Не удалось получить доступ
	        System.out.println("Ссылку: " + link + " невозможно проверить из-за того, что произошла неизвестная ошибка.");
	    }
	    
	    System.out.println("Итого:");
	    System.out.println("Содержит ссылку на наш сайт " + lengthContainListSet + " сайтов.");
	    System.out.println("Невозможно проверить из-за таймаута при подключении " + lenghtTimeoutContainList + " сайтов.");
	    System.out.println("Невозможно проверить из-за защиты от парсинга " + lengthProtectedLinksContainList + " сайтов.");
	    System.out.println("Невозможно проверить из-за некорректно введённого URL " + lengthUncorrectLinksContainList + " сайтов.");
	    System.out.println("Невозможно проверить из-за того, что не удалось получить доступ к сайту " + lengthCannotUseLinksContainList + " сайтов.");
	    System.out.println("Невозможно проверить из-за того, что произошла неизвестная ошибка " + lengthErrorLinksContainList + " сайтов.");
 
	}
}