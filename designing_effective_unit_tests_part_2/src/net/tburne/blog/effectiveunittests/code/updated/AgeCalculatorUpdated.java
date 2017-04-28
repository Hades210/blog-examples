package net.tburne.blog.effectiveunittests.code.updated;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;

import net.tburne.blog.effectiveunittests.code.updated.impl.StreamDestinationFileImpl;
import net.tburne.blog.effectiveunittests.code.container.IContainer;
import net.tburne.blog.effectiveunittests.code.updated.impl.WriterImpl;
import net.tburne.blog.effectiveunittests.code.updated.impl.TransformerImpl;
import net.tburne.blog.effectiveunittests.code.updated.impl.IteratorImpl;
import net.tburne.blog.effectiveunittests.code.updated.impl.ParserImpl;
import net.tburne.blog.effectiveunittests.code.updated.impl.StreamSourceURLImpl;
import net.tburne.blog.effectiveunittests.code.updated.types.IDestinationRecord;
import net.tburne.blog.effectiveunittests.code.updated.types.IWriter;
import net.tburne.blog.effectiveunittests.code.updated.types.ITransformer;
import net.tburne.blog.effectiveunittests.code.updated.types.IIterator;
import net.tburne.blog.effectiveunittests.code.updated.types.IParser;
import net.tburne.blog.effectiveunittests.code.updated.types.ISourceRecord;
import net.tburne.blog.effectiveunittests.code.updated.types.IStreamDestination;
import net.tburne.blog.effectiveunittests.code.updated.types.IStreamSource;

public class AgeCalculatorUpdated {
	
	protected IContainer createDependencyContainer(){
		IContainer dep = IContainer.create();
		dep.add(StreamSourceURLImpl.class).add(StreamDestinationFileImpl.class);
		dep.add(IteratorImpl.class).add(ParserImpl.class).add(TransformerImpl.class).add(WriterImpl.class);
    	return dep;
	}
	
	public void writeAges(URL url, File outputFile) throws IOException{
		IContainer dep = createDependencyContainer().add(url).add(outputFile).add(LocalDate.now());
		IStreamSource src = dep.get(IStreamSource.class);
		IStreamDestination dest = dep.get(IStreamDestination.class);
		try(InputStream input = src.openStream(); OutputStream output = dest.openStream()){
			dep.add(input).add(output);
			IParser parser = dep.get(IParser.class);
			ITransformer trans = dep.get(ITransformer.class);
			try(IIterator iterator = dep.get(IIterator.class);IWriter writer = dep.get(IWriter.class)){
				for(String rawRecord : iterator){
					ISourceRecord sourceRecord = parser.parse(rawRecord);
					IDestinationRecord destintationRecord = trans.transform(sourceRecord);
					writer.write(destintationRecord);
				}
			}
		}
	}
	
}
