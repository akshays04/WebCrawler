package edu.calstatela.cs454.hw1.WebCrawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.select.Elements;

public class DataWeb {
	
	String url;
	String data;
	List<String> links;
	List<DataFile> files;
	Elements elements;
	
	JSONObject json;

	public DataWeb() {
		super();
		links = new ArrayList<String>();
		json = new JSONObject();
	}

	public DataWeb(String data, List<String> links, List<DataFile> files,
			Elements elements, JSONObject json) {
		super();
		this.url = url;
		this.data = data;
		this.links = links;
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

	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}

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
		this.json.put("data", this.data);
		//JSONArray arr = new JSONArray();
		//System.out.println(this.links.size());
		//arr.addAll(this.links);
		//System.out.println(arr.size());
		//this.json.put("links", arr);
		this.json.put("links", this.links);
		//this.json.put("files",);
		this.json.put("Metadata", this.elements);
	}
	
	

}
