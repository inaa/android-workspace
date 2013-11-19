package si.iitech.service;

import java.util.List;

import org.json.JSONObject;

public interface RestCoreCrudService<T> {

	public List<T> getLatestEntries(JSONObject o);

	public T getEntry(JSONObject o);
}
