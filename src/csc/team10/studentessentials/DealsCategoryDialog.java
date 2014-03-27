package csc.team10.studentessentials;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class DealsCategoryDialog extends DialogFragment
{
	List<ImageButton> categories = null;
	
	List<CategoryCallback> callbacks = new ArrayList<CategoryCallback>();
	List<Object> callbackContexts = new ArrayList<Object>();
	
    public DealsCategoryDialog()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	getDialog().setTitle("Choose deal category:");
        return inflater.inflate(R.layout.fragment_deals_categories, container, false);
    }
    
    public void addCategoryCallback(CategoryCallback callback, Object callbackContext)
    {
    	callbacks.add(callback);
    	callbackContexts.add(callbackContext);
    }
    
    public void onStart()
    {
    	super.onStart();
    	
    	categories = new ArrayList<ImageButton>();
    	
		View v = getView();
		
		if (v == null)
		{
	        LayoutInflater vi;
	        vi = LayoutInflater.from(this.getActivity());
	        v = vi.inflate(R.layout.fragment_deals_categories, null);
	    }
    	
		categories.add( (ImageButton) v.findViewById(R.id.category_clothing) );
		categories.add( (ImageButton) v.findViewById(R.id.category_groceries) );
		categories.add( (ImageButton) v.findViewById(R.id.category_restaurants) );
		categories.add( (ImageButton) v.findViewById(R.id.category_gaming) );
		categories.add( (ImageButton) v.findViewById(R.id.category_electronics) );
		categories.add( (ImageButton) v.findViewById(R.id.category_books) );
		categories.add( (ImageButton) v.findViewById(R.id.category_travel) );
		categories.add( (ImageButton) v.findViewById(R.id.category_mobiles) );
		categories.add( (ImageButton) v.findViewById(R.id.category_other) );
    	
    	for(ImageButton category : categories)
    	{
    		category.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					ImageButton category = (ImageButton)v;
					int index =  1 + categories.indexOf(category);
					
					for(int i = 0; i < callbacks.size(); i++)
					{
						CategoryCallback callback = callbacks.get(i);
						Object callbackContext = callbackContexts.get(i);
						
						callback.onSelect(callbackContext, index);
					}
				}
    		});
    	}		
    }
}
