package csc.team10.studentessentials;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddDealsDialog extends DialogFragment
{
	List<DealsCallback> callbacks = new ArrayList<DealsCallback>();
	List<Object> callbackContexts = new ArrayList<Object>();
	
    public AddDealsDialog()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	getDialog().setTitle("Add new deal:");
        return inflater.inflate(R.layout.fragment_deals_add, container, false);
    }
    
    public void addDealsCallback(DealsCallback callback, Object callbackContext)
    {
    	callbacks.add(callback);
    	callbackContexts.add(callbackContext);
    }
    
    public void onStart()
    {
    	super.onStart();
    	
		View v = getView();
		
		if (v == null)
		{
	        LayoutInflater vi;
	        vi = LayoutInflater.from(this.getActivity());
	        v = vi.inflate(R.layout.fragment_deals_add, null);
	    }
    	
    	Button submit = (Button) v.findViewById(R.id.add_deal_submit);
    	
    	submit.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view) {
				Common common = new Common(getActivity());
				
				View v = getView();
				
		    	EditText title = (EditText) v.findViewById(R.id.add_deal_title);
		    	EditText description = (EditText) v.findViewById(R.id.add_deal_description);
		    	EditText startDate = (EditText) v.findViewById(R.id.add_deal_starts);
		    	EditText endDate = (EditText) v.findViewById(R.id.add_deal_ends);
		    	EditText location = (EditText) v.findViewById(R.id.add_deal_location);
		    	
		    	if(title.getText().length() == 0)
		    	{
		    		common.showShortToast("Title is required!");
		    		return;
		    	}
		    	
		    	if(description.getText().length() == 0)
		    	{
		    		common.showShortToast("Description is required!");
		    		return;
		    	}
		    	
		    	if(startDate.getText().length() == 0)
		    	{
		    		common.showShortToast("Start date is required!");
		    		return;
		    	}
		    	
		    	if(endDate.getText().length() == 0)
		    	{
		    		common.showShortToast("End date is required!");
		    		return;
		    	}
		    	
		    	if(location.getText().length() == 0)
		    	{
		    		common.showShortToast("Location is required!");
		    		return;
		    	}
		    	
		    	Spinner category = (Spinner) v.findViewById(R.id.add_deal_category);
		    	int position = 0;
		    	if( (position = category.getSelectedItemPosition()) == 0 )
		    	{
		    		common.showShortToast("Please select category!");
		    		return;
		    	}
		    	
		    	//now create JSONObject with the input data
				try {
					JSONObject information = new JSONObject();
					information.put("title", title.getText().toString());
					information.put("description", description.getText().toString());
					information.put("starts", startDate.getText().toString());
					information.put("ends", endDate.getText().toString());
					information.put("location", location.getText().toString());
					information.put("category", position);
					
					//dummy key
					information.put("subtitle", "");

					//call callback with the input data
					for(int i = 0; i < callbacks.size(); i++)
					{
						DealsCallback callback = callbacks.get(i);
						Object callbackContext = callbackContexts.get(i);
						
						callback.onSubmit(callbackContext, information);
					}
				} catch (JSONException e) {
					common.showShortToast(e.getMessage());
					common.showShortToast("Could not add the deal!");
				}
			}
    	});
    	
    	
    }
}
