package net.tburne.blog.effectiveunittests.code.updated.impl;

import java.io.IOException;
import java.io.OutputStream;

import net.tburne.blog.effectiveunittests.code.updated.types.IDestinationRecord;
import net.tburne.blog.effectiveunittests.code.updated.types.IWriter;

public class WriterImpl implements IWriter{

	private OutputStream mOutput;

	private boolean mHasWritten;
	
	public WriterImpl(OutputStream output) {
		mOutput = output;
	}
	
	@Override
	public void close() throws IOException {
		mOutput.close();
	}

	@Override
	public void write(IDestinationRecord record) throws IOException{
		if(record == null){
			return;
		}
		if(mHasWritten){
			mOutput.write("\n".getBytes());			
		}
		mOutput.write(record.name().getBytes());
		mOutput.write(",".getBytes());
		mOutput.write(((Integer)record.age()).toString().getBytes());
		mOutput.flush();
		mHasWritten = true;
	}

}
