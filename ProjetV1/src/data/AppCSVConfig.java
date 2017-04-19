package data;

import javax.cache.Caching;

import csv.CSVConfig;

public class AppCSVConfig extends CSVConfig {
	
	public AppCSVConfig() {
		super(
				Caching.getCachingProvider().getCacheManager(), new AppCSVDataModel(), new AppCSVDeserializer(),
				new AppCSVSerializer()
		);
	}
	
}
