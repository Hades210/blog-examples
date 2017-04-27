package net.tburne.blog.effectiveunittests.code.updated.types;

import java.io.Closeable;
import java.io.IOException;

public interface IWriter extends Closeable {

	public void write(IDestinationRecord record) throws IOException;
	
}
