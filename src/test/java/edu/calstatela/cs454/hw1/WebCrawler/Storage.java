package edu.calstatela.cs454.hw1.WebCrawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
			if(!url.toLowerCase().contains("https"))
			{
				uuid = UUID.randomUUID();
			File directory = new File(".\\CrawlerStorage\\"+uuid.toString());
			if (directory.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
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
			if(url.toLowerCase().contains(".pdf"))
			{
				URL website = new URL(url);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream(".\\CrawlerStorage\\"+uuid.toString()+"\\"+uuid.toString()+".pdf");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			}
			if(true)
			{
				URL website = new URL(url);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream(".\\CrawlerStorage\\"+uuid.toString()+"\\"+uuid.toString()+".html");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			}
			
			
			System.out.println(uuid);
			FileWriter file = new FileWriter(".\\CrawlerStorage\\"+uuid.toString()+"\\"+uuid+".json");
			file.write(data.getJson().toJSONString());
			file.write("\r\n");
			
			//file.flush();
			file.close();
			}
			
		}catch(Exception e){e.printStackTrace();}
		return uuid;
	}

}
