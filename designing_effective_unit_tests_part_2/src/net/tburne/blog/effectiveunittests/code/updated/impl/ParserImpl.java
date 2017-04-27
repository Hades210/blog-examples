package net.tburne.blog.effectiveunittests.code.updated.impl;

import java.time.LocalDate;

import net.tburne.blog.effectiveunittests.code.updated.types.IParser;
import net.tburne.blog.effectiveunittests.code.updated.types.ISourceRecord;

public class ParserImpl implements IParser{

	@Override
	public ISourceRecord parse(String record) {
		if(record != null){
			String[] components = record.split("[,]");
	    	if(components.length == 4){
	        	String name = components[0];
	        	Integer yearOfBirth = Integer.parseInt(components[1]);
	        	Integer monthOfBirth = Integer.parseInt(components[2]);
	        	Integer dayOfBirth = Integer.parseInt(components[3]);
	        	LocalDate date = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
	    		return new ISourceRecord() {

					@Override
					public String name() {
						return name;
					}

					@Override
					public LocalDate date() {
						return date;
					}
	    			
	    		};
	    	}	
		}
		return null;
	}

}
