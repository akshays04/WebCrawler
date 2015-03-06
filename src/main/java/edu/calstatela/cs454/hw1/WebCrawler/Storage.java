package edu.calstatela.cs454.hw1.WebCrawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.Link;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.ContentHandler;

import com.sleepycat.je.sync.impl.SyncCleanerBarrier.SyncTrigger;
public class Storage {

	private final static Pattern FILTERS = Pattern
			.compile(".*\\.(jpg|xls|xlsx|doc|docx|ppt|pptx|pdf|mp3|jpeg)"
					+ "(\\?.*)?$");

	/*
	 * public static UUID save(String url) { UUID uuid = null; try{
	 * if(!url.toLowerCase().contains("https")) { uuid = UUID.randomUUID(); File
	 * directory = new File(".\\CrawlerStorage\\"+uuid.toString()); if
	 * (directory.mkdir()) { System.out.println("Directory is created!"); } else
	 * { System.out.println("Failed to create directory!"); } //creating new obj
	 * DataWeb data = new DataWeb();
	 * 
	 * //adding url data.setUrl(url);
	 * 
	 * 
	 * 
	 * //adding doc data Document doc = Jsoup.connect(url).get();
	 * data.setData(doc.toString());
	 * 
	 * //addding list of links Elements urlLinks = doc.select("a[href]"); List
	 * temp = new ArrayList<String>(); for(Element ele : urlLinks){
	 * temp.add(ele.attr("abs:href")); } data.setLinks(temp);
	 * 
	 * data.setElements(doc.select("meta")); data.createJSON();
	 * if(url.toLowerCase().contains(".pdf")) { URL website = new URL(url);
	 * ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	 * FileOutputStream fos = new
	 * FileOutputStream(".\\CrawlerStorage\\"+uuid.toString
	 * ()+"\\"+uuid.toString()+".pdf"); fos.getChannel().transferFrom(rbc, 0,
	 * Long.MAX_VALUE); } if(true) { URL website = new URL(url);
	 * ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	 * FileOutputStream fos = new
	 * FileOutputStream(".\\CrawlerStorage\\"+uuid.toString
	 * ()+"\\"+uuid.toString()+".html"); fos.getChannel().transferFrom(rbc, 0,
	 * Long.MAX_VALUE); }
	 * 
	 * 
	 * System.out.println(uuid); FileWriter file = new
	 * FileWriter(".\\CrawlerStorage\\"+uuid.toString()+"\\"+uuid+".json");
	 * file.write(data.getJson().toJSONString()); file.write("\r\n");
	 * 
	 * //file.flush(); file.close(); }
	 * 
	 * }catch(Exception e){e.printStackTrace();} return uuid; }
	 */
	public static UUID saveTika(String url) {
		UUID uuid = null;
		try {
			File dir2 = new File(".\\CrawlerStorage");
			if(dir2.mkdir()){}
			if (!url.toLowerCase().contains("https")) {
				uuid = UUID.randomUUID();
				File directory = new File(".\\CrawlerStorage\\"
						+ uuid.toString());
				if (directory.mkdir()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}

				Map<String, Object> metadata = new HashMap<String, Object>();

				Tika tika = new Tika();
				tika.setMaxStringLength(10 * 1024 * 1024);
				Metadata met = new Metadata();
				URL objurl = new URL(url.toString());

				ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
				ParseContext parseContext = new ParseContext();
				LinkContentHandler linkHandler = new LinkContentHandler();
				ContentHandler textHandler = new BodyContentHandler(10 * 1024 * 1024);
				TeeContentHandler teeHandler = new TeeContentHandler(
						linkHandler, textHandler, toHTMLHandler);
				 //ContentHandler contenthandler = new BodyContentHandler();
				

				AutoDetectParser parser = new AutoDetectParser();
				parser.parse(objurl.openStream(), teeHandler, met, parseContext);
				//System.out.println("ContentHandler"+textHandler.toString());

				@SuppressWarnings("deprecation")
				String title = met.get(Metadata.TITLE);
				String type = met.get(Metadata.CONTENT_TYPE);
				System.out.println(type);

				System.out.println(type);
				//List<Link> links = linkHandler.getLinks();
				// creating new obj
				DataWeb data = new DataWeb();
				HashMap<String, String> linkMap=new HashMap<String, String>();
				for(Link objlink:linkHandler.getLinks())
				{
					linkMap.put(objlink.getUri().toString(), objlink.getText());
			
				}

				// adding url
				data.setUrl(url);

				Date date = new Date();

				//data.setUrllinks(links);

				metadata.put("title", title);
				metadata.put("type", type);
				metadata.put("url", url);
				metadata.put("last pulled", date);
				//metadata.put("links", links.toString());
				data.setMetadata(metadata);
				data.setLinks(linkMap);

				// data.setElements(doc.select("meta"));
				data.createJSON();
				if (url.toLowerCase().contains(".pdf")) {
					URL website = new URL(url);
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(
							".\\CrawlerStorage\\" + uuid.toString() + "\\"
									+ uuid.toString() + ".pdf");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}
				if(type.equals("application/vnd.ms-powerpoint")){
					URL website = new URL(url);
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(
							".\\CrawlerStorage\\" + uuid.toString() + "\\"
									+ uuid.toString() + ".ppt");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}
				if(type.equals("image/png")){
					URL website = new URL(url);
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(
							".\\CrawlerStorage\\" + uuid.toString() + "\\"
									+ uuid.toString() + ".png");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}
				if(type.equals("image/jpeg")){
					URL website = new URL(url);
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(
							".\\CrawlerStorage\\" + uuid.toString() + "\\"
									+ uuid.toString() + ".jpg");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}
				if(type.equals("image/gif")){
					URL website = new URL(url);
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(
							".\\CrawlerStorage\\" + uuid.toString() + "\\"
									+ uuid.toString() + ".gif");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}
				if(type.equals("application/xml")){
					URL website = new URL(url);
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(
							".\\CrawlerStorage\\" + uuid.toString() + "\\"
									+ uuid.toString() + ".xml");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}
				if(type.equals("image/vnd.microsoft.icon")){
					URL website = new URL(url);
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(
							".\\CrawlerStorage\\" + uuid.toString() + "\\"
									+ uuid.toString() + ".icon");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}
				if (true) {
					URL website = new URL(url);
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(
							".\\CrawlerStorage\\" + uuid.toString() + "\\"
									+ uuid.toString() + ".html");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}

				System.out.println(uuid);
				FileWriter file = new FileWriter(".\\CrawlerStorage\\"
						+ uuid.toString() + "\\" + uuid + ".json");
				file.write(data.getJson().toJSONString());
				file.write("\r\n");

				// file.flush();
				file.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return uuid;
	}

}
