package edu.calstatela.cs454.hw1.WebCrawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Storage {
	
	private final static Pattern FILTERS = Pattern.compile(".*\\.(jpg|xls|xlsx|doc|docx|ppt|pptx|pdf|mp3|jpeg)" +
            "(\\?.*)?$");
	
	public static UUID save(String url) {
		UUID uuid = null;
		try{
			//creating new obj
			DataWeb data = new DataWeb();
			
			//adding url
			data.setUrl(url);
			
			//adding doc data
			Document doc = Jsoup.connect(url).get();
			data.setData(doc.toString());
			
			//addding list of links
			Elements urlLinks = doc.select("a[href]");
			List temp = new ArrayList<String>();
			for(Element ele : urlLinks){
				temp.add(ele.attr("abs:href"));
			}
			data.setLinks(temp);
			
			data.setElements(doc.select("meta"));
			
			data.createJSON();
			
			uuid = UUID.randomUUID();
			System.out.println(uuid);
			FileWriter file = new FileWriter(".\\CrawlerStorage\\"+uuid+".json");
			file.write(data.getJson().toJSONString());
			file.write("\r\n");
			
			//file.flush();
			file.close();
			
		}catch(Exception e){e.printStackTrace();}
		return uuid;
	}

}
