package com.emailScrapper;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Helper class used to find links in a page and extract email addresses
 */
public class Crawler
{
	//Email Address Pattern
	private static final String pattern = "\\b[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9.-]+\\b";

	// Unique list of links found in a page
	private Set<String> links = new HashSet<String>();
	private Document htmlDocument;
	private Set<String> emailAddresses;
	private String mainDomain;


	public Crawler(Set<String> emailAddresses, String mainDomain) {
		this.emailAddresses = emailAddresses;
		this.mainDomain = mainDomain;
	}


	/**
	 * Makes an HTTP request and parses the reponse to collect all
	 * the links on the page.
	 * 
	 * @param url The URL to visit
	 */
	public void crawl(String url)
	{
		// Clear the list of new page coming in
		links.clear();

		try
		{
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;
			
			if(connection.response().statusCode() == 200) // 200 HTTP OK status code
			{
				System.out.println("\n**Visiting** webpage = " + url);
			}
			
			if(!connection.response().contentType().contains("text/html"))
			{
				System.out.println("**Failure** Retrieved something other than HTML");
				return;
			}
			Elements linksOnPage = htmlDocument.select("a[href]");
			for(Element link : linksOnPage)
			{
				// Filter out sub-domains
				if(new URL(link.absUrl("href")).getHost().startsWith(mainDomain)){
					this.links.add(link.absUrl("href"));
				}
			}
			return;
		}
		catch(IOException ioe)
		{
			// We were not successful in our HTTP request (Add error message if required)
			return;
		}
	}


	/**
	 * Performs a search on the body of on the HTML document to find email addresses
	 * 
	 */
	public void extractEmail() {
		//Creates a Pattern
		Pattern pat = Pattern.compile(pattern);
		//Matches contents against the given Email Address Pattern
		if(this.htmlDocument == null || htmlDocument.body() ==  null)
		{
			System.out.println("ERROR! not reachable");
			return;
		}
		Matcher match = pat.matcher(htmlDocument.body().text());
		//If match found, append to emailAddresses
		while(match.find()) {
			emailAddresses.add(match.group());
		}
	}


	public Set<String> getLinks()
	{
		return this.links;
	}

}