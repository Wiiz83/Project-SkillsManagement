package data;

import javax.cache.Caching;

import csv.CSVConfig;

public class AppCSVConfig extends CSVConfig {
	
	private AppCSVConfig() {
		super(null, null, null, null);
	}
	
	public static AppCSVConfig getInstance() {
		AppCSVConfig cfg = new AppCSVConfig();
		AppCSVDeserializer des = new AppCSVDeserializer(cfg);
		cfg.setCacheManager(Caching.getCachingProvider().getCacheManager());
		cfg.setModel(new AppCSVDataModel());
		cfg.setSerializer(new AppCSVSerializer());
		cfg.setDeserializer(des);
		return cfg;
	}
	
}
