package net.tburne.blog.effectiveunittests.code;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.tburne.blog.effectiveunittests.code.original.AgeCalculatorOriginal;
import net.tburne.blog.effectiveunittests.code.updated.AgeCalculatorUpdated;

public class Caller {

    public static void main(String[] args) {
    	try {
        	URL url = new URL("https://s3-ap-southeast-2.amazonaws.com/tburnenet/testdata.txt");
        	{
            	File file = new File("/home/tburne/test_original.txt");
        		(new AgeCalculatorOriginal()).writeAges(url, file);        		
        	}
        	{
            	File file = new File("/home/tburne/test_updated.txt");
        		(new AgeCalculatorUpdated()).writeAges(url, file);        		
        	}
    	} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
	
}
