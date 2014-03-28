package csc.team10.studentessentials;

import org.json.JSONObject;

public class Club {
	//id in the database
	private String ID = "";
	
	//creator (student) id in the database (not student ID!)
	private String sID = "";
	
	private String title = "";
	private String subtitle = "";
	private String description = "";
	private int category = 9;
	private String starts = "";
	private String ends = "";
	private String location = "";
	private String added = "";
	private String creatorNumber = "";
	
	//regards to currently logged in user
	private int hotOrCold = 0;
	
	private int rank = 0;
	
	public static int[] CATEGORIES = {
		0,
		R.drawable.icon_clothing,
		R.drawable.icon_groceries,
		R.drawable.icon_restaurants,
		R.drawable.icon_gaming,
		R.drawable.icon_electronics,
		R.drawable.icon_books,
		R.drawable.icon_travel,
		R.drawable.icon_mobiles,
		R.drawable.icon_other
	};
	
	public Club(JSONObject data)
	{
		JSONObject information = data.optJSONObject("information");
		
		if(information != null)
		{
			ID = information.optString("ID", ID);
			sID = information.optString("sID", sID);
			title = information.optString("title", title);
			subtitle = information.optString("subtitle", subtitle);
			description = information.optString("description", description);
			
			starts = information.optString("starts", starts);
			ends = information.optString("ends", ends);
			location = information.optString("location", location);
			added = information.optString("added", added);
			rank = information.optInt("rank", rank);
			category = information.optInt("category", category);
		}
		
		creatorNumber = data.optString("creatorNumber", creatorNumber);
		hotOrCold = data.optInt("hotOrCold", hotOrCold);
	}
	
	public void setCreatorNumber(String number)
	{
		this.creatorNumber = number;
	}
	
	public String getCreatorNumber()
	{
		return this.creatorNumber;
	}
	
	public void setCreatorId(String sID)
	{
		this.sID = sID;
	}
	
	public String getCreatorId()
	{
		return this.sID;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setSubtitle(String subtitle)
	{
		this.subtitle = subtitle;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public void setCategory(int category)
	{
		this.category = category;
	}
	
	public void setRank(int rank)
	{
		this.rank = rank;
	}
	
	public String getId()
	{
		return ID;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getSubtitle()
	{
		return subtitle;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public int getCategory()
	{
		return category;
	}
	
	public int getRank()
	{
		return rank;
	}
	
	public String getAdded()
	{
		return added;
	}
	
	public String toString()
	{
		return "Title: " + title + "\n" +
				"Description: " + description + "\n" +
				"Rank: " + rank;
	}
}
