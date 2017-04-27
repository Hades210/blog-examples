package net.tburne.blog.effectiveunittests.tests.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class FailingInputStream extends InputStream {

	private String mFailAt;
	
	private byte[] mData;
	
	private int mPos = 0;
	
	public FailingInputStream(String data, String failAt) {
		mData = data.getBytes();
		mFailAt = failAt;
	}
	
	@Override
	public int read() throws IOException {
		String current = new String(Arrays.copyOf(mData, mPos));				
		if(current.equals(mFailAt)){
			throw new IOException("Unexpected exception");
		}
		if(mPos > mData.length){
			return -1;
		}
		try{
			return mData[mPos];				
		}finally{
			mPos++;
		}
	}
	
}
