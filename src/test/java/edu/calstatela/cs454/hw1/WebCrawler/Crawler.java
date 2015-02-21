package edu.calstatela.cs454.hw1.WebCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Crawler {
	
	public static void main(String[] args) {
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setMaxDepthOfCrawling(2);
        crawlConfig.setCrawlStorageFolder("C:\\asp\\crawler4jStorage");
        System.out.println(crawlConfig.toString());
        PageFetcher pageFetcher = new PageFetcher(crawlConfig);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        try {
                CrawlController controller = new CrawlController(crawlConfig,
                        pageFetcher, robotstxtServer);
                
                controller.addSeed("http://web.mit.edu/");
                //controller.addSeed("http://www.calstatela.edu/");
                //controller.addSeed("http://www.ics.uci.edu/");


                controller.start(MyCrawler.class, 1);
                MyCrawler.writeMap();
                
                
        }catch(Exception e){e.printStackTrace();}
	}

}
