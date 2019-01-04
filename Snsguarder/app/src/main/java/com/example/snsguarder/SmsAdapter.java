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

public class SmsAdapter extends BaseAdapter {
	private LayoutInflater minflater;
	private Context context;
	private List<SmsBean>itemlist;
 

    public SmsAdapter(Context context,List<SmsBean> itemlist ) {
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
			
			convertView=minflater.inflate(R.layout.inlistadpx,parent, false);
	 	    viewholder.content=(TextView) convertView.findViewById(R.id.inbox_adp_content);
   		    viewholder.time=(TextView) convertView.findViewById(R.id.inbox_adp_date);
   		    viewholder.name=(TextView) convertView.findViewById(R.id.inbox_adp_num);
 			convertView.setTag(viewholder);
		}else {
			viewholder=(viewHolder) convertView.getTag();
		}
			
		 

          if (itemlist!=null&&itemlist.size()>0){
        	  
        		  viewholder.content.setTextColor(Color.GREEN);
  			 
        	   viewholder.content.setText(itemlist.get(arg0).body+"");
        	  
        	  viewholder.time.setText(itemlist.get(arg0).date+"");
        	  viewholder.name.setText((arg0+1)+"。"+itemlist.get(arg0).address+"");
          }
        	  return convertView;
			
	}
 class viewHolder{
	 TextView content;
	 TextView name;
	 TextView time;
 }
}
