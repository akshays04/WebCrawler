package edu.calstatela.cs454.hw1.WebCrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.Link;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.select.Elements;

public class DataWeb {
	
	String url;
	String data;
	//List<String> links;
	List<DataFile> files;
	Elements elements;
	//List<Link> Urllinks;
	HashMap<String,String> Links;
	JSONObject json;
	JSONObject metadata;
	Metadata metadata2;

	public DataWeb() {
		super();
		//links = new ArrayList<String>();
		json = new JSONObject();
	}

	public DataWeb(String data, List<String> links, List<DataFile> files,
			Elements elements, JSONObject json) {
		super();
		this.url = url;
		this.data = data;
		//this.links = links;
		this.files = files;
		this.elements = elements;
		this.json = json;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/*public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}
*/
	public List<DataFile> getFiles() {
		return files;
	}

	public void setFiles(List<DataFile> files) {
		this.files = files;
	}

	public Elements getElements() {
		return elements;
	}

	public void setElements(Elements elements) {
		this.elements = elements;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void createJSON(){
		this.json.put("url", this.url);
		//this.json.put("data", this.data);
		//JSONArray arr = new JSONArray();
		//System.out.println(this.links.size());
		//arr.addAll(this.links);
		//System.out.println(arr.size());
		//this.json.put("links", arr);
		//this.json.put("links", this.links);
		//this.json.put("files",);
		//this.json.put("Metadata", this.elements);
		this.json.put("Metadata", this.metadata);
		this.json.put("links", this.Links);
		
	}

	/*public List<Link> getUrllinks() {
		return Urllinks;
	}

	public void setUrllinks(List<Link> urllinks) {
		Urllinks = urllinks;
	}
*/
	/*public Metadata getMetadata2() {
		return metadata2;
	}

	public void setMetadata2(Metadata metadata2) {
		this.metadata2 = metadata2;
	}
*/
	

	public void setLinks(HashMap<String, String> links) {
		Links = links;
	}

	public JSONObject getMetadata() {
		return metadata;
	}

	public void setMetadata(JSONObject metadata) {
		this.metadata = metadata;
	}
	
	

}
