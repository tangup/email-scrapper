package com.emailScrapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Scrapper class which scrapes webpage to get links, maintains pages to be visited and already visited
 *
 */
public class Scrapper
{
	// Set an upper limit to avoid long runs for websites with numerous links
	private static final int MAX_PAGES_TO_SEARCH = 300;

	// Maintain a unique list of pages visited to avoid reruns
	private Set<String> pagesVisited = new HashSet<String>();

	// Maintain a unique list of pages to be visited
	private Set<String> pagesToVisit = new HashSet<String>();

	// Unique list of email addresses found
	Set<String> emailAddresses = new HashSet<String>();

	/**
	 * Entry point to start search of website. Internally it searches links on the webpage and
	 * makes an HTTP request and parse the response to find email addresses.
	 * 
	 * @param url The given website URL
	 */
	public void search(String url)
	{
		String mainDomain = "";
		URL tempURL = null;
		String currentUrl;

		// Append protocol if it is missing from webpage
		if(!(url.startsWith("http:") || url.startsWith("https:")))
			url = "http://" + url;

		// Get main domain to compare, to avoid crawling sub-domains
		try {
			tempURL = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainDomain = tempURL.getHost();

		// Helper class used to find links in a page nd extract email addresses
		Crawler leg = new Crawler(emailAddresses, mainDomain);

		do
		{
			currentUrl = "";
			if(this.pagesToVisit.isEmpty())
			{
				currentUrl = url;
				this.pagesVisited.add(url);
			}
			else
			{
				currentUrl = this.nextUrl();
			}


			try {
				if(currentUrl != "" && new URL(currentUrl).getHost().startsWith(mainDomain))
				{
					leg.crawl(currentUrl);
					leg.extractEmail();
					this.pagesToVisit.addAll(leg.getLinks());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(!pagesToVisit.isEmpty() && this.pagesVisited.size() < MAX_PAGES_TO_SEARCH);

		printAddresses();
		
		//System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
	}


	/**
	 * Returns the next URL to visit (in the order that they were found). We also do a check to make
	 * sure this method doesn't return a URL that has already been visited.
	 * 
	 * @return
	 */
	private String nextUrl()
	{
		String nextUrl = "";
		do
		{
			if(!this.pagesToVisit.isEmpty())
			{
				nextUrl = pagesToVisit.iterator().next();
				pagesToVisit.remove(nextUrl);
			}
			else
			{
				nextUrl = "";
				break;
			}
		} while(this.pagesVisited.contains(nextUrl));
		if(nextUrl != ""){
			this.pagesVisited.add(nextUrl);
		}
		return nextUrl;
	}

	private void printAddresses() {
		//Check if email addresses have been extracted
		if(emailAddresses.size() > 0) {
			//Print out all the extracted emails
			System.out.println("\n\n**************************** ");
			System.out.println("\nExtracted Email Addresses: \n");
			System.out.println("\n**************************** ");
			for(String emails : emailAddresses) {
				System.out.println(emails);
			}
		} else {
			//In case, no email addresses were extracted
			System.out.println("\tNo emails were extracted!");
		}
	}
}