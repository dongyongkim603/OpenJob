package com.JohnHaney.OpenJob;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadDictionaries {
	

	// class variables
	public static ArrayList<String> countries = new ArrayList<>();

	// constructor that will automatically load the dictionary
	public LoadDictionaries() {
		initiateDicationary();
	}

//-----------load file method-------------------------------------
	
	/**
	 * Loads a dictionary of countries in the root directory into an ArrayList
	 */
	public static ArrayList<String> initiateDicationary() {
		System.out.println("starting");
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\Eclipse\\EE Projects\\OpenJob\\src\\main\\resources\\static\\dictionaries\\countrylist.txt"))) {
			System.out.println("Loading dictionary to memory...");
			String word = "";
			while ((word = bufferedReader.readLine()) != null) {
				System.out.println(word);
				countries.add(word);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File was not found");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error with the IO");
			return null;
		}
		return countries;
	}
}
