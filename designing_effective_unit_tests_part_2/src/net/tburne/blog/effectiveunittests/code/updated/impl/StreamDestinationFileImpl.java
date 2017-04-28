package net.tburne.blog.effectiveunittests.code.updated.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;

import net.tburne.blog.effectiveunittests.code.updated.types.IStreamDestination;

public class StreamDestinationFileImpl implements IStreamDestination {

	private File mFile;
	
	public StreamDestinationFileImpl(File file) {
		mFile = file;
	}
	
	@Override
	public OutputStream openStream() throws IOException {
		try{
			return new FileOutputStream(mFile);			
		}catch(FileNotFoundException e){
			throw new IOError(e);
		}
	}

}
