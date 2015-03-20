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

@SuppressWarnings("unused")
public class DataWeb {

	String url;

	String path;

	List<DataFile> files;

	Elements elements;

	HashMap<String, String> Links;

	JSONObject json;

	JSONObject metadata;

	Metadata metadata2;

	public DataWeb() {
		super();
		json = new JSONObject();
	}

	public DataWeb(String path, List<String> links, List<DataFile> files,
			Elements elements, JSONObject json, String url) {
		super();
		this.url = url;
		this.path = path;
		this.files = files;
		this.elements = elements;
		this.json = json;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	@SuppressWarnings("unchecked")
	public void createJSON() {
		this.json.put("url", this.url);
		this.json.put("URL", url);
		this.json.put("path", path);
		this.json.put("Metadata", this.metadata);
		this.json.put("links", this.Links);

	}

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
