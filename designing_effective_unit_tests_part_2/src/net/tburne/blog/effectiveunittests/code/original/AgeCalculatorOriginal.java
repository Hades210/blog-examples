package net.tburne.blog.effectiveunittests.code.original;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import static java.lang.Integer.*;
import java.util.Scanner;

public class AgeCalculatorOriginal {

    public void writeAges(URL url, File output) throws IOException{
        Scanner in = new Scanner(new InputStreamReader(url.openConnection().getInputStream()));
        FileOutputStream out = new FileOutputStream(output);
        LocalDate now = LocalDate.now();
        while (in.hasNextLine()) {
        	String line = in.nextLine();
        	String[] c = line.split("[,]");
        	if(c.length == 4){
            	LocalDate date = LocalDate.of(parseInt(c[1]), parseInt(c[2]), parseInt(c[3]));
            	Integer age = Period.between(date, now).getYears();
            	out.write(c[0].getBytes());
            	out.write(",".getBytes());
            	out.write(age.toString().getBytes());
            	out.write("\n".getBytes());
        	}
        }
        in.close();
        out.close();
    }
    
}
