package csv;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;

public class CSVCache {
	private CacheManager manager;
	
	public CSVCache() {
		manager = Caching.getCachingProvider().getCacheManager();
	}
	
	public <E extends CSVEntity> Cache<String, E> getCache(Class<E> entityClass) {
		Cache<String, E> cache = manager.getCache(createName(entityClass), String.class, entityClass);
		if (cache == null) {
			cache = new CSVEntityCache<E>(manager, entityClass, createName(entityClass)).getCache();
		}
		return cache;
	}
	
	private static String createName(Class<? extends CSVEntity> entityClass) {
		return "csvcache;" + entityClass.getName();
	}
}
