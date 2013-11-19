package si.iitech.dao;

import java.util.List;

public interface DAOCoreCore<T, P> {
	
	//Shranjevanje seznama podatkov, ki ga aplikacija prvo pridobi
	
	public void saveTemporaryDataList(final List<P> list, String tableName) throws IITechDAOExeption;
	
	public List<P> getTemporaryDataList(final String tableName) throws IITechDAOExeption;
	
	
	
	
	public void savePermanentData() throws IITechDAOExeption;
		
	public void deletePermanenData(final long id) throws IITechDAOExeption;
	
	public List<T> returnAllTemporaryData() throws IITechDAOExeption;
	
	public List<T> returnAllPermanentData() throws IITechDAOExeption;
	
	public T returnTemporaryData(final long id) throws IITechDAOExeption;
	
	public T returnPermanenData(final long id) throws IITechDAOExeption;
	
}
