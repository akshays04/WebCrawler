package edu.calstatela.cs454.hw1.WebCrawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.net.URL;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.simple.JSONArray;
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
    
    private static int depth;
    private static Set<String> domains = new HashSet<String>();
    
    private final static Storage storage = new Storage();
    private static HashMap<String, UUID> urlMapper = new HashMap<String, UUID>();
    
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         
         try{
             URL objurl = new URL(href);
             if(domains.size()<depth)
            	 domains.add(url.getDomain());
             //System.out.println("URL : "+href+"  Domain : "+ url.getDomain()+"  Depth : "+url.getDepth());
             }catch(Exception e){e.printStackTrace();}
             
             //return !FILTERS.matcher(href).matches();
             if(domains.contains(url.getDomain())){
            	 System.out.println("Accepted URL : "+href+"  Domain : "+ url.getDomain());
            	 return true;}
             else{
            	 System.out.println("Rejected URL : "+href+"  Domain : "+ url.getDomain());
            	 return false;}
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
    			JSONArray jsonArr = new JSONArray();
    			String newUrl;
	
    			for(String name : urlMapper.keySet()){
    				json = new JSONObject();
    				newUrl = StringEscapeUtils.unescapeEcmaScript(name);
    				//System.out.println(name+" vs "+newUrl);
    				json.put("url", newUrl);
    				json.put("filename", urlMapper.get(name)+"");
    				jsonArr.add(json);
    				
    				//write txt
    				//file.write(json.toJSONString());
    				//file.write("\r\n");
    				//Call to metadata.json file write
    				
    				//storage.extractMetaData(urlMapper.get(name).toString(),name);
    			}
    			file.write(jsonArr.toJSONString());
				file.write("\r\n");

    			file.flush();
    			file.close();
    			}catch(IOException e){e.printStackTrace();}
     }
     
     public static void setDepth(int d){
    	 depth = d+1;
     }
}