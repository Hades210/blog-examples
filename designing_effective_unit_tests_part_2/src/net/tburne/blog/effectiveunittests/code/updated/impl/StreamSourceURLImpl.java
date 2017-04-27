package net.tburne.blog.effectiveunittests.code.updated.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.tburne.blog.effectiveunittests.code.updated.types.IStreamSource;

public class StreamSourceURLImpl implements IStreamSource{

	private URL mUrl;
	
	public StreamSourceURLImpl(URL url) {
		mUrl = url;
	}

	@Override
	public InputStream openStream() throws IOException {
		return mUrl.openStream();
	}
	
}
