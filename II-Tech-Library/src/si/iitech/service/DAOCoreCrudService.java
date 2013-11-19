package si.iitech.service;

import java.util.List;

public interface DAOCoreCrudService<T> {

	public T getEntry();

	public boolean updateEntry(T entry);

	public List<T> getAllEntries();

	public boolean deleteEntry(T entry);

}
