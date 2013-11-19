package si.iitech.rest;

import java.util.List;

import org.json.JSONArray;

public interface RestCoreCrud<T> {
	
	public List<T> convertToArticleList(final JSONArray array);
	
}
