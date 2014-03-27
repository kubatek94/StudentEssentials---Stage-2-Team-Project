package csc.team10.RssHandling;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* 														  *
 * Author: Connor Shields								  *
 * This class is our custom handler used by the SAXParser *
 * 														  */
public class ParseHandler extends DefaultHandler 
{

	//List that will contain all of the parsed rssItems.
	private List<RssItem> rssItems;
	
	// Used to check what the current item is while parsing.
	private RssItem currentItem;
	
	// Is the title being parsed.
	private boolean parsingTitle;
	// Is the link being parsed.
	private boolean parsingLink;
	
	/*								*
	 * Constructor to set rssItems. *
	 * 								*/
	public ParseHandler()
	{
		rssItems = new ArrayList<RssItem>();
	}
	
	/*						*
	 * Get method to return *
	 * rssItems. 			*
	 * 						*/
	public List<RssItem> getItems()
	{
		return rssItems;
	}
	
	/*												  *
	 * A method to indicate the start of an rss item, *
	 * and to parse it into an RssItem object.        *
	 * 												  */	
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException 
	{
		// if the tag is the <item> tag then create a new RssItem and save it in current item.
		if ("item".equals(name))
		{
			currentItem = new RssItem();
		} 
		// if the tag is the <title> tag set parsingTitle to true.
		else if ("title".equals(name)) 
		{
			parsingTitle = true;
		} 
		// if the tag is the <link> tag set parsingLink to true.
		else if ("link".equals(name)) 
		{
			parsingLink = true;		
		} 
	}
	
	/*												  *
	 * A method to indicate the end of an rss item,   *
	 * and to end the currentItem.			          *
	 * 												  */	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		// if the tag is the <item> tag then add the current item into the list rssItems 
		//and set currentItem to null.
		if ("item".equals(qName))
		{
			rssItems.add(currentItem);
			currentItem = null;	
		}
		// if the tag is the <title> tag set parsingTitle to false.
		else if ("title".equals(qName)) 
		{
			parsingTitle = false;
		}
		// if the tag is the <link> tag set parsingLink to false.
		else if ("link".equals(qName))
		{
			parsingLink = false;
		}
	}
	
	/*							 	      *
	 * A method parse the contents of the *
	 * xml tags into the RssItem fields.  *
	 *						  		  	  */	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{	
		// If the element has a title then put the contents of it into the title field in currentItem(an RssItem).
		if (parsingTitle) 
		{
		// A check to see if an RssItem actually exists to put this into.
			if (currentItem != null)
			{	
				currentItem.setTitle(new String(ch, start, length));
			}
			
		// If the element has a link then put the contents of it into the title field in currentItem(an RssItem).
		} else if (parsingLink)
		{
			// A check to see if an RssItem actually exists to put this into.
			if (currentItem != null) 
			{
				currentItem.setLink(new String(ch, start, length));
				parsingLink = false;
			}
			
		}
	}
	
}
