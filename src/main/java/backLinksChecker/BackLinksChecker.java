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
		ArrayList<String> linksContainList = new ArrayList<String>(); //������ � ������� ����������� ������ �� ��� ����
		ArrayList<String> timeoutContainList = new ArrayList<String>(); //������ � ������� ����������� timeout ��� �����������
		ArrayList<String> protectedLinksContainList = new ArrayList<String>(); //������ � ������� ����������� �� ��������
		ArrayList<String> uncorrectLinksContainList = new ArrayList<String>(); //������ � ������� ����������� ������������ URL
		ArrayList<String> cannotUseLinksContainList = new ArrayList<String>(); //�� ������� �������� ������ � �����
		ArrayList<String> errorLinksContainList = new ArrayList<String>(); //��������� ����������� ������
		
		
		
		//���������� ������ ������ ������� ������, ���������� ������ � �������.
		Scanner scanner = new Scanner(System.in);
		System.out.println("������� ������ ������ �� ������� ������, ������ � ����� ������, ����� ��������� ���� �������� S: ");
		String inputTargetPages = "";
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			inputCount++;
			if (line.equals("S")) {
				System.out.println("������� " + inputCount + " ������");
				break;
			}
			inputTargetPages += line.trim() + "\n";
		}
		//���������� ������ ����� ������, ���������� ������ � �������.
		System.out.println("������� ������ ����� ������, ������ � ����� ������, ����� ��������� ���� �������� S: ");
		String inputOurPages = "";
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.equals("S")) {
				scanner.close();
				break;
			}
			inputOurPages += line.trim() + ",";
		}

		String[] targetPages = inputTargetPages.split("\n"); //����� ����� �������
		String[] ourPages = inputOurPages.split(",");
		
	    for (String targetPage : targetPages) {
	    	String url = targetPage.trim();
            // ���������, ���������� �� URL-����� � "https://", � ���� ���, �� ��������� ���.
            if (!url.startsWith("https://")) {
                url = "https://" + url;
            }
	    	try {
	        	Document doc = Jsoup.connect(url).get(); //������� � ���������
	        	Elements links = doc.select("a[href], div a[href], img[src], iframe[src], span a[href], p a[href]"); 
	            // ������������ ��������� ����������, ��������� � �������������� ������
	            if (links.isEmpty()) {
	                System.out.println("��������! �� �������� " + url + " ����������� ������!");
	                continue; // ��������� � ��������� ��������
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
	                        //System.out.println("������� " + outputCount + " ������ �� " + inputCount);
	                    	linksContainList.add(url);
	                    }    else {
	                    	
	                        // �������� System.out.println("Link " + url + " does not contain a backlink to " + linkHref);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            String errorMessage = e.getMessage();
	            // ������������ ��������� ������ ����������
	            if (errorMessage.contains("403") || errorMessage.contains("429")) {
	                //System.out.println("���� " + url + " ������� �� ��������.");
	                protectedLinksContainList.add(url);
	            } else if (errorMessage.contains("timeout")) {
	                //System.out.println("�� ������� ���������� ���������� � ������ " + url + ". ����� �������� �������.");
	            	timeoutContainList.add(url);
	            } else {
	                //System.out.println("�� ������� �������� ������ � " + url + ": " + e.getMessage());
	                cannotUseLinksContainList.add(url);
	            }
	            continue;
	        } catch (IllegalArgumentException e) {
	            //System.out.println("������������ URL: " + e.getMessage());
	            uncorrectLinksContainList.add(url);
	            continue;
	        } catch (Exception e) {
	            //System.out.println("��������� ����������� ������: " + e.getMessage());
	            errorLinksContainList.add(url);
	            continue;
	        }
	    } //����� ��������
	    
	    //������ ������
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
	    
	    for (String link : linksContainList) { //���������� ������ �����
	        System.out.println("������: " + link + " �������� ������ �� ��� ����.");
	    }
	    for (String link : timeoutContainList) { //������� �����
	        System.out.println("������: " + link + " ���������� ��������� ��-�� �������� ��� �����������.");
	    }
	    for (String link : protectedLinksContainList) { //���������� �� �������� ����� 
	        System.out.println("������: " + link + " ���������� ��������� ��-�� ������ �� ��������.");
	    }
	    for (String link : uncorrectLinksContainList) { //������������ URL ��� �����
	        System.out.println("������: " + link + " ���������� ��������� ��-�� ����������� ��������� URL.");
	    }
	    for (String link : cannotUseLinksContainList) { //�� ������� �������� ������
	        System.out.println("������: " + link + " ���������� ��������� ��-�� ����, ��� �� ������� �������� ������ � �����.");
	    }
	    for (String link : errorLinksContainList) { //�� ������� �������� ������
	        System.out.println("������: " + link + " ���������� ��������� ��-�� ����, ��� ��������� ����������� ������.");
	    }
	    
	    System.out.println("�����:");
	    System.out.println("�������� ������ �� ��� ���� " + lengthContainListSet + " ������.");
	    System.out.println("���������� ��������� ��-�� �������� ��� ����������� " + lenghtTimeoutContainList + " ������.");
	    System.out.println("���������� ��������� ��-�� ������ �� �������� " + lengthProtectedLinksContainList + " ������.");
	    System.out.println("���������� ��������� ��-�� ����������� ��������� URL " + lengthUncorrectLinksContainList + " ������.");
	    System.out.println("���������� ��������� ��-�� ����, ��� �� ������� �������� ������ � ����� " + lengthCannotUseLinksContainList + " ������.");
	    System.out.println("���������� ��������� ��-�� ����, ��� ��������� ����������� ������ " + lengthErrorLinksContainList + " ������.");
 
	}
}