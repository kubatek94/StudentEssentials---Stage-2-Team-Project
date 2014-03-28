package csc.team10.studentessentials;

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
	// item description
	private String description;
	// item image
	private String image;
	// item date
	private String date;
	
	/*							 *
	 * Getter and Setter methods *
	 * 							 */
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title) 
	{
		String trim = title.replace("&rsquo;", "'").trim();
		if(trim.length() <= 35)
		{
			this.title = trim;
		}else
		{
			
			this.title = (trim.substring(0, 35) + " ...");
		}
	}	

	public String getLink() 
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}
	
	public String getImage() 
	{
		return image;
	}

	public void setImage(String image)
	{	
		this.image = image;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description)
	{
		String formatedDesc = description.replace("&rsquo;", "'").trim();
		formatedDesc = formatedDesc.replace("&#39;", "'");
		if(formatedDesc.length() <= 240)
		{
			this.description = formatedDesc;
		}else
		{
			
			this.description = (formatedDesc.substring(0, 240) + " ...");
		}
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date) 
	{
		this.date = date;
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
