package com.test.utils;

public class URLUtils {
	public static int getTeamNameInURI(String uri){
		String[] tokens = uri.split("/");
		switch(tokens[tokens.length-1]) {
			case "borregos.jsf": return 1;
			case "buhos.jsf": return 2;
		}
		return -1;
	}
}
