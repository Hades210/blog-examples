package net.tburne.blog.effectiveunittests.code.updated.types;

import java.io.IOException;
import java.io.OutputStream;

public interface IStreamDestination {

	public OutputStream openStream() throws IOException;
	
}
