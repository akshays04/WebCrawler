package edu.calstatela.cs454.hw1.WebCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Crawler {
	
	public static void main(String[] args) {
		
		int depth=Integer.parseInt(args[1]);
		String url = args[3];
		
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setMaxDepthOfCrawling(depth);
        //crawlConfig.setMaxDepthOfCrawling(2);
        crawlConfig.setMaxPagesToFetch(100);
        crawlConfig.setCrawlStorageFolder("C:\\asp\\crawler4jStorage");
        crawlConfig.setIncludeBinaryContentInCrawling(true);
        System.out.println(crawlConfig.toString());
        PageFetcher pageFetcher = new PageFetcher(crawlConfig);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        try {
                CrawlController controller = new CrawlController(crawlConfig,
                        pageFetcher, robotstxtServer);
                
                MyCrawler.setDepth(depth);
                
                //controller.addSeed("http://www.lewebdev.com/");
                //controller.addSeed("http://www.calstatela.edu/");
                //controller.addSeed("http://www.apple.com/");
                controller.addSeed(url);


                controller.start(MyCrawler.class, 100);
                MyCrawler.writeMap();
                
                
        }catch(Exception e){e.printStackTrace();}
	}

}
