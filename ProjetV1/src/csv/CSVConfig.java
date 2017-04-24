package csv;

import java.io.IOException;
import java.util.HashMap;

import javax.cache.CacheManager;

public class CSVConfig {
	private CSVCache								csvcache;
	private CSVModel								model;
	private CSVDeserializer							deserializer;
	private CSVSerializer							serializer;
	@SuppressWarnings("unused")
	private HashMap<CSVAssociation, CSVDocument>	documents;
	
	protected CSVCache getCsvcache() {
		return csvcache;
	}
	
	protected void setCacheManager(CacheManager cachemanger) {
		this.csvcache = new CSVCache(cachemanger);
	}
	
	protected void setModel(CSVModel model) {
		this.model = model;
	}
	
	protected void setDeserializer(CSVDeserializer deserializer) {
		this.deserializer = deserializer;
	}
	
	protected void setSerializer(CSVSerializer serializer) {
		this.serializer = serializer;
	}
	
	final public CSVCache getCSVCache() {
		return csvcache;
	}
	
	public CSVConfig(CacheManager cachemanger, CSVModel model, CSVDeserializer deserializer, CSVSerializer serializer) {
		super();
		this.csvcache = new CSVCache(cachemanger);
		this.model = model;
		this.deserializer = deserializer;
		this.serializer = serializer;
		this.documents = new HashMap<>();
	}
	
	final public CSVDocument getDocument(Class<? extends CSVEntity> entityclass) throws IOException {
		return getDocument(new CSVAssociation(entityclass, null));
	}
	
	final public CSVDocument getDocument(CSVAssociation assoc) throws IOException {
		
		CSVDocument doc = documents.get(assoc);
		if (doc == null) {
			// System.out.println(assoc + "document is null");
			CSVFileConfig cfg = model.Metadata().getCSVFileConfig(assoc);
			doc = new CSVDocument(cfg);
			documents.put(assoc, doc);
		}
		// CSVFileConfig cfg = model.Metadata().getCSVFileConfig(assoc);
		// CSVDocument doc = new CSVDocument(cfg);
		// else
		// System.out.println("Document non null:" + doc);
		return doc;
		
	}
	
	final public CSVModel getModel() {
		return model;
	}
	
	public CSVDeserializer getDeserializer() {
		return deserializer;
	}
	
	public CSVSerializer getSerializer() {
		return serializer;
	}
	
}
