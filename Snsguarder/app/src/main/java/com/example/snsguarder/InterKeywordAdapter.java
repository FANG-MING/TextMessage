package com.example.snsguarder;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;
 
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InterKeywordAdapter extends BaseAdapter {
	private LayoutInflater minflater;
	private Context context;
	private List<String>itemlist;
 

    public InterKeywordAdapter(Context context,List<String> itemlist ) {
		this.context=context;
		this.itemlist=itemlist;
		minflater=LayoutInflater.from(context);
    	
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		 
			return itemlist.size();
		 
		
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return itemlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup parent) {
		viewHolder viewholder=null;
		 if (viewholder==null) {
			viewholder=new viewHolder();
			
			convertView=minflater.inflate(R.layout.keywadpx ,parent, false);
	 	    viewholder.word=(TextView) convertView.findViewById(R.id.fil_keyword_adp_tv);
	 	   viewholder.num=(TextView) convertView.findViewById(R.id. fil_keywnum_adp_tv);
 			convertView.setTag(viewholder);
		}else {
			viewholder=(viewHolder) convertView.getTag();
		}
			
		 

          if (itemlist!=null&&itemlist.size()>0){
        	  
        		  viewholder.word.setTextColor(Color.GREEN);
  			 
        		  viewholder.num.setText((arg0+1)+":");
        	   viewholder.word.setText(itemlist.get(arg0)+"");
         
          }
        	  return convertView;
			
	}
 class viewHolder{
	 TextView word;
	  TextView num;
	 
 }
}
