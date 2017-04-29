package csv;

import javax.cache.Cache;
import javax.cache.CacheManager;

import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;

public class CSVEntityCache<E extends CSVEntity> {
	
	private Cache<String, E> cache;
	
	public CSVEntityCache(CacheManager manager, Class<E> entity, String name) {
		
		Configuration<String, E> config = new MutableConfiguration<String, E>().setTypes(String.class, entity)
				.setWriteThrough(false).setReadThrough(false).setStoreByValue(false);
		
		this.cache = manager.createCache(name, config);
	}
	
	public Cache<String, E> getCache() {
		return cache;
	}
	
}
