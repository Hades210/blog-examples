package net.tburne.blog.effectiveunittests.tests.helpers;

import java.io.IOException;
import java.io.OutputStream;

public class FailingOutputStream extends OutputStream {	

	private String mFailString;
	
	private StringBuffer mReceived = new StringBuffer();
	
	public FailingOutputStream(String failString) {
		mFailString = failString;
	}

	@Override
	public void write(int arg0) throws IOException {
		char b = (char)arg0;
		if(mReceived.toString().equals(mFailString)){
			throw new IOException("Unexpected exception");
		}
		mReceived.append(b);
	}
	
}