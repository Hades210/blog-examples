package net.tburne.blog.effectiveunittests.code.updated.types;

import java.io.IOException;
import java.io.InputStream;

public interface IStreamSource {
	
	public InputStream openStream() throws IOException;
	
}
