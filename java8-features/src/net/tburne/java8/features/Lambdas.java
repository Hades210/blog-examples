package net.tburne.java8.features;

public class Lambdas {

	public interface IFormatter {
	
		public String format(String value);
		
	}

	public static String format(IFormatter formatter, String data){
		return formatter.format(data);
	}
	
	public String instanceFormatLowercase(String value){
		return value.toLowerCase();
	}
	
	public static String staticFormatLowercase(String value){
		return value.toLowerCase();
	}
	
	public static void main(String[] args) {
		// anonymous class implementation
		String data = "Testing";
		System.out.println(format(new IFormatter() {

			@Override
			public String format(String value) {
				return value.toLowerCase();
			}
			
		}, data));
		// lambda implementation
		System.out.println(format((String value)-> value.toLowerCase(), data));
		// instance method reference implementation
		Lambdas l = new Lambdas();
		System.out.println(format(l::instanceFormatLowercase, data));		
		// static method reference implementation
		System.out.println(format(Lambdas::staticFormatLowercase, data));			
	}
	
	
	
}
