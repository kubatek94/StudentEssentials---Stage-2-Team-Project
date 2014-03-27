package csc.team10.RssHandling;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/* 									   *
 * Author: Connor Shields			   *
 * This class handles the input stream * 
 * and parsing of the xml.			   *
 *                                     */
public class Reader
{
	private String url;
	
	/*									 *	
	 *  Constructor just to set the url. *
	 *									 */
	public Reader(String url)
	{
		this.url = url;
	}

	/* 											 *
	 * Method that parses and returns the rss    *
	 *  feed in the form of List<RssItem>.       *
	 * 											 */	
	public List<RssItem> getItems() throws Exception
	{
		// Initialise SAXParserFactory and SAXParser.
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		
		// Initialise our customised handler for our rss.
		ParseHandler handler = new ParseHandler();
		
		// Pass the SAXParser our url(input stream) and custom handler.
		saxParser.parse(url, handler);

		// Returns a List of <RssItem>.
		return handler.getItems();
		
	}

}