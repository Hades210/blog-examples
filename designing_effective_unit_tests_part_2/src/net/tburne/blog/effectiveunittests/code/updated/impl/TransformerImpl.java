package net.tburne.blog.effectiveunittests.code.updated.impl;

import java.time.LocalDate;
import java.time.Period;

import net.tburne.blog.effectiveunittests.code.updated.types.IDestinationRecord;
import net.tburne.blog.effectiveunittests.code.updated.types.ITransformer;
import net.tburne.blog.effectiveunittests.code.updated.types.ISourceRecord;

public class TransformerImpl implements ITransformer {

	private LocalDate mAsAt;
	
	public TransformerImpl(LocalDate asAt) {
		if(asAt == null){
			throw new NullPointerException("As at must not be null");
		}
		mAsAt = asAt;
	}
	
	@Override
	public IDestinationRecord transform(ISourceRecord source) {
		if(source == null){
			return null;
		}
    	int age = Period.between(source.date(), mAsAt).getYears();
    	return new IDestinationRecord() {
			
			@Override
			public String name() {
				return source.name();
			}
			
			@Override
			public int age() {
				return age;
			}
		};
	}

}
