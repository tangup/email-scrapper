package com.emailScrapper;

/**
 * This is the main class used to Test EmailScrapper.
 */
public class ScrapperTest
{ 
	public static void main(String[] args)
	{
		//Check if URL was given as input
		if(args.length > 0 && args[0] != null) {
			Scrapper scraper = new Scrapper();
			scraper.search(args[0]);

		} else {
			System.out.println("\tInvalid Arguments given");
		}

	}
}