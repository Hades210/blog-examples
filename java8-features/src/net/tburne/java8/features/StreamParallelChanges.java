package net.tburne.java8.features;

import java.util.ArrayList;
import java.util.List;

public class StreamParallelChanges {

	public static void main(String[] args) {
		// source values
		List<String> items = new ArrayList<String>();
		items.add("aaa");
		items.add("bbb");
		items.add("ccc");
		items.add("ddd");
		items.add("eee");
		items.add("fff");
		items.add("ggg");
		items.add("hhh");
		items.add("iii");
		items.add("jjj");
		List<String> output = new ArrayList<String>();
		// items converted to stream, mapped to new uppercase values, terminated to output list
		items.stream().parallel().map(s->s.toUpperCase()).forEach(s->output.add(s));
		// print output list
		for(String s : output){
			System.out.println(s);
		}
	}
	
	
}
