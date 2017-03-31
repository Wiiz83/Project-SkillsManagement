package csv;

import javax.cache.Cache;
import javax.cache.CacheManager;

public class CSVCache {
	private CacheManager manager;
	
	public CSVCache(CacheManager manager) {
		this.manager = manager;
	}
	
	public <E extends CSVEntity> Cache<String, E> getCache(Class<E> entityClass) {
		Cache<String, E> cache = manager.getCache(createName(entityClass), String.class, entityClass);
		if (cache == null) {
			cache = new CSVEntityCache<E>(manager, entityClass, createName(entityClass)).getCache();
		}
		return cache;
	}
	
	private static String createName(Class<? extends CSVEntity> entityClass) {
		return "csvobjects;" + entityClass.getName();
	}
}
