package edu.calstatela.cs454.hw1.WebCrawler;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.jsoup.nodes.Document;

public class Indexer {

	public Map<String, Map<Integer, Integer>> performIndex(Document document,
			Map<String, Map<Integer, Integer>> wordMap, int linkCount) {
		StringTokenizer token = new StringTokenizer(document.text(), " |.");
		while (token.hasMoreTokens()) {
			String element = token.nextToken();
			if (!element.equalsIgnoreCase("a")
					&& !element.equalsIgnoreCase("the")
					&& !element.equalsIgnoreCase("is")) {
				Map<Integer, Integer> wordCount = new HashMap<Integer, Integer>();
				if (wordMap.get(element) == null) {
					wordCount.put(linkCount, 1);
				} else {
					wordCount = wordMap.get(element);
					int count;
					if (wordCount.get(linkCount) == null) {
						count = 0;
					} else {
						count = wordCount.get(linkCount);
					}
					wordCount.put(linkCount, ++count);
				}
				wordMap.put(element, wordCount);
			}
		}
		return wordMap;
	}

	public Map<String, Integer> performRankingCal(Map<String, Integer> rankMap,
			String url) {
		int count;
		if (rankMap.get(url) == null) {
			count = 0;
		} else

		{
			count = rankMap.get(url);

		}
		rankMap.put(url, ++count);

		return rankMap;
	}

	public void performRank(Map<String, Integer> rankMap,
			Map<String, Set<String>> pageUrl) {

		int numberOfLinks = rankMap.size();
		double avgRank = 1.0 / numberOfLinks;
		Map<String, Double> pageRank = new HashMap<String, Double>();

		for (String s : rankMap.keySet()) {
			pageRank.put(s, avgRank);
		}

		for (int i = 0; i < 10; i++) {
			for (String rootPage : rankMap.keySet()) {
				for (String page : pageUrl.keySet()) {
					if (rootPage.equals(page)) {
						double calRank = 0.0;
						double totalRank = 0.0;
					
						for (String urlPage : pageUrl.get(rootPage)) {
							
							calRank = pageRank.get(urlPage)
									/ rankMap.get(urlPage);
							totalRank += calRank;
						}

						pageRank.put(page, totalRank);
					}
				}
			}

		}
		displayRank(pageRank);
	}

	public void displayRank(Map<String, Double> pageRank) {
		int count = 1;
		System.out.println("#document number, page.html, ranking");
		for (String s : pageRank.keySet()) {
			System.out.println(count + ". " + s + " "
					+ new DecimalFormat("##.##").format(pageRank.get(s)*0.06));
			count++;
		}
	}

}

