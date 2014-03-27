package csc.team10.RssHandling;
/* 									  *
 * Author: Connor Shields			  *
 * This class holds the information	  * 
 * for individual rss items.          *
 *                                    */
public class RssItem
{	
	// item title
	private String title;
	// item link
	private String link;

	/*							 *
	 * Getter and Setter methods *
	 * 							 */
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getLink() 
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}
	
	/* 															  *
	 * This toString method is used when displaying the rss item  *
	 * it will be used to display the <title> tag on the feed.    *
	 *															  */
	@Override
	public String toString() 
	{
		return title;
	}
	
}
