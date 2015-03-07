package edu.calstatela.cs454.hw1.WebCrawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*\\.(bmp|gif|png|tiff?|ico|xaml|pict|rif|pptx?|ps" +
            "|mid|mp2|mp4|wav|wma|au|aiff|flac|ogg|3gp|aac|amr|au|vox" +
            "|avi|mov|mpe?g|ra?m|m4v|smil|wm?v|swf|aaf|asf|flv|mkv" +
            "|zip|rar|gz|7z|aac|ace|alz|apk|arc|arj|dmg|jar|lzip|lha)" +
            "(\\?.*)?$");
    
    private final static Storage storage = new Storage();
    private static HashMap<String, UUID> urlMapper = new HashMap<String, UUID>();
    
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         //return !FILTERS.matcher(href).matches();
         return true;
         //return !FILTERS.matcher(href).matches() && href.startsWith("http://www.ics.uci.edu/");
     }

     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("URL: " + url);
         UUID filename = storage.saveTika(url);
         if(filename != null)
        	 urlMapper.put(url, filename);

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             Set<WebURL> links = htmlParseData.getOutgoingUrls();

             System.out.println("Text length: " + text.length());
             System.out.println("Html length: " + html.length());
             System.out.println("Number of outgoing links: " + links.size());
         }
    }
     
     public static void writeMap(){
    	 try{
    			FileWriter file = new FileWriter(".\\CrawlerStorage\\map.json");
    			JSONObject json;
    			Storage storage = new Storage();
	
    			for(String name : urlMapper.keySet()){
    				json = new JSONObject();
    				json.put("url", name);
    				json.put("filename", urlMapper.get(name)+".json");
    				
    				//write txt
    				file.write(json.toJSONString());
    				file.write("\r\n");
    				//Call to metadata.json file write
    				
    				storage.extractMetaData(urlMapper.get(name).toString(),name);
    			}

    			file.flush();
    			file.close();
    			}catch(IOException e){e.printStackTrace();}
     }
}