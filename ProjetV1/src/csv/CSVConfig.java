package csv;

import java.io.IOException;
import java.util.HashMap;

import javax.cache.CacheManager;

final public class CSVConfig {
	private CSVCache								csvcache;
	private CSVModel								model;
	private CSVDeserializer							deserializer;
	private CSVSerializer							serializer;
	private HashMap<CSVAssociation, CSVDocument>	documents;
	
	public CSVConfig(
			CacheManager manager, Class<? extends CSVModel> model, Class<? extends CSVDeserializer> deserializer,
			Class<? extends CSVSerializer> serializer
	) throws InstantiationException, IllegalAccessException {
		this.csvcache = new CSVCache(manager);
		this.model = model.newInstance();
		this.serializer = serializer.newInstance();
		this.deserializer = deserializer.newInstance();
		this.deserializer.setCSVConfig(this);
		this.documents = new HashMap<>();
	}
	
	public CSVCache getCSVCache() {
		return csvcache;
	}
	
	public CSVDocument getDocument(Class<? extends CSVEntity> entityclass) throws IOException {
		return getDocument(new CSVAssociation(entityclass, null));
	}
	
	public CSVDocument getDocument(CSVAssociation assoc) throws IOException {
		CSVDocument doc = documents.get(assoc);
		if (doc == null) {
			CSVFileConfig cfg = model.Metadata().getCSVFileConfig(assoc);
			doc = new CSVDocument(cfg);
			documents.put(assoc, doc);
		}
		return doc;
	}
	
	public CSVModel getModel() {
		return model;
	}
	
	public CSVDeserializer getDeserializer() {
		return deserializer;
	}
	
	public CSVSerializer getSerializer() {
		return serializer;
	}
	
}
