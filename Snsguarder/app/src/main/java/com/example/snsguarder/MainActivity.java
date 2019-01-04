package com.example.snsguarder;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
 
import android.R.array;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.text.method.KeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupWindow.OnDismissListener;


public class MainActivity extends Activity {
      private LinearLayout welLayout,mainLayout;
      private Button boxBtn,numBtn,keywBtn,setBtn;
      
      private ListView inboxListview,keywListview,bwListview;
      private SmsSql sql;
      private SmsReicervier reicever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
             window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
          
         }
        setContentView(R.layout.activity_main);
        initView();
        clickEvent() ;
        backThread() ;
        getHW() ;
        
        reicever=new SmsReicervier();
        IntentFilter  filter=new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(reicever,filter);
    }
   private void initView() {
	//初始化 view
	   mainLayout=(LinearLayout) findViewById(R.id.ex_layout);
       welLayout=(LinearLayout) findViewById(R.id.wel_layout);
       boxBtn=(Button) findViewById(R.id.sns_box_btn);
       numBtn=(Button) findViewById(R.id.sns_black_btn);
       keywBtn=(Button) findViewById(R.id.sns_text_btn);
       setBtn=(Button) findViewById(R.id.sns_set_btn);
       
       sql=new SmsSql(this);
       
       View mSnsView = LayoutInflater.from(this).inflate(R.layout.inboxx, null);  
 		inboxListview=(ListView) mSnsView.findViewById(R.id.in_sns_listview);
 		
 		 View mKeywView = LayoutInflater.from(this).inflate(R.layout.keywordx, null); 
	 		keywListview=(ListView) mKeywView.findViewById(R.id.keyw_listv);
	 		
	 		
	 	   	  View mAddkeywView = LayoutInflater.from(this).inflate(R.layout.addkeywordx, null);  

	  	 		addKeywEdit=(EditText) mAddkeywView.findViewById(R.id.add_keyw_edit);
	  	 		
	  	 	  View mBwView = LayoutInflater.from(this).inflate(R.layout.blawfilx, null);  
	  	 	  
	   	 		bwListview =(ListView) mBwView.findViewById(R.id.fil_num_listv);
	   	 	 blaBtn= (Button) mBwView.findViewById(R.id. fil_black_btn);
	 	     whiBtn= (Button) mBwView.findViewById(R.id. fil_white_btn);
	   	 	 View mbwView = LayoutInflater.from(this).inflate(R.layout.addbwnumx, null);  

	   	 	    addBwEdit=(EditText)mbwView.findViewById(R.id.add_blw_edit);
	   	 	    
    }
     private void clickEvent() {
		//  点击事件
         boxBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				snsWindow();
			}
		});
         
         
         numBtn.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				AddressWindow();
 			}
 		});
         
         keywBtn.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				KeyWordWindow();
 			}
 		});
         
         setBtn.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				SetWindow();
 			}
 		});
	}
     

     private void  SetWindow( ) {
  		//  
      	  View mPopView = LayoutInflater.from(this).inflate(R.layout.setx, null);  
  	 		final PopupWindow mPopWindow = new PopupWindow(mPopView, mWidth-20,  
  	 				mHeight/3, true);  
  	 		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
  	 			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
  	 		int popupWidth = mPopView.getMeasuredWidth();  
  	 		int popupHeight = mPopView.getMeasuredHeight();  
  	 		  mPopWindow.showAtLocation(setBtn, Gravity.BOTTOM, 0, 0);
  	 		  mPopWindow.update(); 
  	 		  mPopWindow.setOutsideTouchable(false);
  	 		  
  	       
  	          mPopView.findViewById(R.id.about_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					        Toast.makeText(MainActivity.this, "短信卫士2.0", Toast.LENGTH_SHORT).show();
					 
 							mPopWindow.dismiss();
 							
				}
			}) ;
  	    
  			 
           mPopView.findViewById(R.id.exit_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 
					finish();
					
					mPopWindow.dismiss();
				}
			}) ;
      
         
  	        }
     
     private ArrayList<SmsBean> snsList=new ArrayList<SmsBean>();
     private void snsWindow() {
 		// 加密window
     	  View mPopView = LayoutInflater.from(this).inflate(R.layout.inboxx, null);  
 	 		final PopupWindow mPopWindow = new PopupWindow(mPopView, mWidth,  
 	 				mHeight, true);  
 	 		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
 	 			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
 	 		int popupWidth = mPopView.getMeasuredWidth();  
 	 		int popupHeight = mPopView.getMeasuredHeight();  
 	 		  mPopWindow.showAtLocation(setBtn, Gravity.BOTTOM, 0, 0);
 	 		  mPopWindow.update(); 
 	 		  mPopWindow.setOutsideTouchable(false);
 	 		  
 	      
 	 		inboxListview=(ListView) mPopView.findViewById(R.id.in_sns_listview);
 	    
 	    
// 	 		 mPopView.findViewById(R.id. ).setOnClickListener(new OnClickListener() {
// 				
// 				@Override
// 				public void onClick(View v) {
// 					// TODO Auto-generated method stub String k=interEdit.getText().toString();
// 				 
// 				}
// 			});
 	         
 	        isGetInBox=true;
 	 		 
 	        
        inboxListview.setOnItemClickListener(new OnItemClickListener() {

 			@Override
 			public void onItemClick(AdapterView<?> parent, View view,
 					int position, long id) {
 				// TODO Auto-generated method stub
 				Toast.makeText(MainActivity.this, snsList.get(position).address+"\n"+snsList.get(position).body+"\n"+snsList.get(position).date,Toast.LENGTH_SHORT).show();
 			}
 	        	});
        
         inboxListview.setOnItemLongClickListener(new OnItemLongClickListener() {

 			@Override
 			public boolean onItemLongClick(AdapterView<?> parent, View view,
 					int position, long id) {
 				//恢复操作
 				 
//  		        EncryptSetWindow(view, snsList.get(position).date, snsList.get(position).body,
//  		        		snsList.get(position).address, "1"); 
  		        inboxSetWindow(snsList.get(position).date, snsList.get(position).body,snsList.get(position).address,snsList.get(position).type);
  				return true;
 			}
        	
 		});
        
        
     
        
 	        }
  
     private void inboxSetWindow(final String date ,final String body,final String address ,final String type) {
  		// 收件箱设置
      	  View mPopView = LayoutInflater.from(this).inflate(R.layout.inboxsetx, null);  
  	 		final PopupWindow mPopWindow = new PopupWindow(mPopView, mWidth,  
  	 				mHeight/3, true);  
  	 		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
  	 			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
  	 		int popupWidth = mPopView.getMeasuredWidth();  
  	 		int popupHeight = mPopView.getMeasuredHeight();  
  	 		  mPopWindow.showAtLocation(setBtn, Gravity.BOTTOM, 0, 0);
  	 		  mPopWindow.update(); 
  	 		  mPopWindow.setOutsideTouchable(false);
  	 		  
  	       
  	          mPopView.findViewById(R.id.inbox_recover_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 恢复短信
					 Uri uri = Uri.parse("content://sms"); 
	                   
                     ContentValues values = new ContentValues();  
                     values.put("address", address);  
                     values.put("date",  date);  
                     values.put("type",  type);  
                     values.put("body",  body);  
                 
							try {
								getContentResolver().insert(uri, values);
								Toast.makeText(MainActivity.this, "已恢复到短信", Toast.LENGTH_SHORT).show();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
 							sql.cancelMEvent(date, body);
 							isGetInBox=true;
 							mPopWindow.dismiss();
 							
				}
			}) ;
  	    
  			 
           mPopView.findViewById(R.id.inbox_delete_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sql.cancelMEvent(date, body);
					   Toast.makeText(MainActivity.this, "已删除短信",Toast.LENGTH_SHORT).show();
					isGetInBox=true;
					mPopWindow.dismiss();
				}
			}) ;
      
         
  	        }
     
     
     
     private void KeyWordWindow( ) {
  		//  添加过滤词
      	  View mPopView = LayoutInflater.from(this).inflate(R.layout.keywordx, null);  
  	 		final PopupWindow mPopWindow = new PopupWindow(mPopView, mWidth,  
  	 				mHeight-25, true);  
  	 		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
  	 			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
  	 		int popupWidth = mPopView.getMeasuredWidth();  
  	 		int popupHeight = mPopView.getMeasuredHeight();  
  	 		  mPopWindow.showAtLocation(setBtn, Gravity.BOTTOM, 0, 0);
  	 		  mPopWindow.update(); 
  	 		  mPopWindow.setOutsideTouchable(false);
  	 		 
  	 		keywListview=(ListView) mPopView.findViewById(R.id.keyw_listv);
  	         isGetKeyw=true;
  	         
  	          mPopView.findViewById(R.id.fil_keyword_addbtn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//  
					addKeyWordWindow();
					  
				}
			}) ;
  	    
  	        keywListview.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					//  
					CancelWindow(0, keyWordList,whiteAddressList,position);
					
					return false;
				}
			});
       
         
  	        }
     
     
     private EditText addKeywEdit;
     private void addKeyWordWindow( ) {
  		//  添加过滤词
      	  View mPopView = LayoutInflater.from(this).inflate(R.layout.addkeywordx, null);  
  	 		final PopupWindow mPopWindow = new PopupWindow(mPopView, mWidth-20,  
  	 				mHeight/2, true);  
  	 		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
  	 			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
  	 		int popupWidth = mPopView.getMeasuredWidth();  
  	 		int popupHeight = mPopView.getMeasuredHeight();  
  	 		  mPopWindow.showAtLocation(setBtn, Gravity.BOTTOM, 0, 0);
  	 		  mPopWindow.update(); 
  	 		  mPopWindow.setOutsideTouchable(false);
 
  	 		addKeywEdit=(EditText) mPopView.findViewById(R.id.add_keyw_edit);
  	 	 
  	       
  	         
  	          mPopView.findViewById(R.id.add_keyw_add_btn ).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//  
					 String s=addKeywEdit.getText().toString();
					 if (s.equals("")|s==null) {
						Toast.makeText(MainActivity.this,"请输入正确关键词",Toast.LENGTH_SHORT).show();
					}else {
						sql.addInterKeywordMEvent(s);
						 isGetKeyw=true;
						 mPopWindow.dismiss();
					}
					
					 
					 
				}
			}) ;
  	    
  	         
       
         
  	        }
      private boolean isBlackAddre=true;
      private Button blaBtn,whiBtn;
     private void AddressWindow( ) {
   		//  添加黑白名单
    	 
       	  View mPopView = LayoutInflater.from(this).inflate(R.layout.blawfilx, null);  
   	 		final PopupWindow mPopWindow = new PopupWindow(mPopView, mWidth,  
   	 				mHeight-25, true);  
   	 		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
   	 			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
   	 		int popupWidth = mPopView.getMeasuredWidth();  
   	 		int popupHeight = mPopView.getMeasuredHeight();  
   	 		  mPopWindow.showAtLocation(setBtn, Gravity.BOTTOM, 0, 0);
   	 		  mPopWindow.update(); 
   	 		  mPopWindow.setOutsideTouchable(false);
   	 	 
    	 	     bwListview =(ListView) mPopView.findViewById(R.id.fil_num_listv);
    	 	     blaBtn= (Button) mPopView.findViewById(R.id. fil_black_btn);
    	 	     whiBtn= (Button) mPopView.findViewById(R.id. fil_white_btn);
    	         isGetBla =true;
   	         
   	          mPopView.findViewById(R.id. fil_num_addbtn).setOnClickListener(new OnClickListener() {
 				
 				@Override
 				public void onClick(View v) {
 					//  
 				 addAddressWindow();
 				}
 			}) ;
   	       
   	        blaBtn .setOnClickListener(new OnClickListener() {
 				
 				@Override
 				public void onClick(View v) {
 					//   
 					isGetBla=true;
 				   isBlackAddre=true;
 				   blaBtn.setTextSize(20);
 				   blaBtn.setTextColor(Color.RED);
 				   
 				  whiBtn.setTextSize(11);
 				 whiBtn.setTextColor(Color.GRAY);
 				   
 				}
 			}) ;
   	       
   	        whiBtn .setOnClickListener(new OnClickListener() {
 				
 				@Override
 				public void onClick(View v) {
 					//  
 					isGetBla=true;
 					isBlackAddre=false;
 					blaBtn.setTextSize(11);
  				   blaBtn.setTextColor(Color.GRAY);
  				   
  				 whiBtn.setTextSize(20);
  				whiBtn.setTextColor(Color.RED);
 				}
 			}) ;
   	          
   	       bwListview.setOnItemLongClickListener(new OnItemLongClickListener() {

 				@Override
 				public boolean onItemLongClick(AdapterView<?> parent,
 						View view, int position, long id) {
 					// 
 					if (isBlackAddre) {
 	 					CancelWindow(1, keyWordList,blackAddressList ,position);

					}else if (!isBlackAddre) {
 	 					CancelWindow(2, keyWordList,whiteAddressList,position);

					}
 					
 					return false;
 				}
 			});
        
          
   	        }
     private EditText addBwEdit; 
     private void addAddressWindow( ) {
   		//  添加黑白名单
    	 
       	  View mPopView = LayoutInflater.from(this).inflate(R.layout.addbwnumx, null);  
   	 		final PopupWindow mPopWindow = new PopupWindow(mPopView, mWidth-20,  
   	 				mHeight/2, true);  
   	 		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
   	 			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
   	 		int popupWidth = mPopView.getMeasuredWidth();  
   	 		int popupHeight = mPopView.getMeasuredHeight();  
   	 		  mPopWindow.showAtLocation(setBtn, Gravity.BOTTOM, 0, 0);
   	 		  mPopWindow.update(); 
   	 		  mPopWindow.setOutsideTouchable(false);
   	 		  
 
   	 	      addBwEdit=(EditText)mPopView.findViewById(R.id.add_blw_edit);
    	  
   	       
   	          mPopView.findViewById(R.id.add_blw_add_btn).setOnClickListener(new OnClickListener() {
 				
 				@Override
 				public void onClick(View v) {
 					//
 					String s=addBwEdit.getText().toString();
 					if (s.length()==11) {
 						if (isBlackAddre) {
 							sql.addEncryptEvent(s);
 						}	else {
							sql.addWhiteEncryptEvent(s);
						}
 						isGetBla=true;
 						mPopWindow.dismiss();
						Toast.makeText(MainActivity.this, "添加成功",Toast.LENGTH_SHORT).show();

					}else {
						Toast.makeText(MainActivity.this, "请输入正确手机号码",Toast.LENGTH_SHORT).show();
					}
 					
 					
 					
 					 
 				}
 			}) ;
   	        
   	          
   	      
   	        }
     private void CancelWindow(final int whitch,final ArrayList<String> listkeyword,final ArrayList<String> listaddress ,final int position) {
   		//   
       	  View mPopView = LayoutInflater.from(this).inflate(R.layout.cancelx, null);  
   	 		final PopupWindow mPopWindow = new PopupWindow(mPopView, mWidth-20,  
   	 				mHeight/3, true);  
   	 		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
   	 			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
   	 		int popupWidth = mPopView.getMeasuredWidth();  
   	 		int popupHeight = mPopView.getMeasuredHeight();  
   	 		  mPopWindow.showAtLocation(setBtn, Gravity.BOTTOM, 0, 0);
   	 		  mPopWindow.update(); 
   	 		  mPopWindow.setOutsideTouchable(false);
  
   	 		 
   	       
   	         
   	          mPopView.findViewById(R.id.item_cancel_btn ).setOnClickListener(new OnClickListener() {
 				
 				@Override
 				public void onClick(View v) {
 					//  
 					     if (whitch==0) {
							sql.cancelInterKeywordMEvent(listkeyword.get(position));
							isGetKeyw=true;
						}else if (whitch==1) {
							sql.cancelEncryptEvent(listaddress.get(position));
							isGetBla=true;
						}else if (whitch==2) {
							sql.cancelWhiteEncryptEvent(listaddress.get(position));
							isGetBla=true;
						   }
 					     Toast.makeText(MainActivity.this, "已删除",Toast.LENGTH_SHORT).show();
 						 
 						 mPopWindow.dismiss();
 					 
 					
 					 
 					 
 				}
 			}) ;
   	    
   	         
        
          
   	        }
     // 动画特效
     private void viewAnim(LinearLayout v) {
   	  LayoutAnimationController lac=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.alpha));
   	   	lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
   	   	v.setLayoutAnimation(lac);
   	   	v.startLayoutAnimation();
        
       }  
     private boolean isRun=true,isFirstEnd=true;
     private int time=0;
     private boolean isGetInBox=false,isGetKeyw=false,isGetBla=false;
     
     private ArrayList<String> keyWordList=new ArrayList<String>();
     private ArrayList<String> blackAddressList=new ArrayList<String>();
     private ArrayList<String>whiteAddressList=new ArrayList<String>();
     
     private void backThread() {
		//  新线程
    	  final Handler mHandler=new Handler(){
    		  @Override
    		public void handleMessage(Message msg) {
    			 
    			 if (msg.what==0x100) {
					welLayout.setVisibility(View.GONE);
					viewAnim(mainLayout);
				    }
    			 if (msg.what==0x101) {
    				 inboxListview.setAdapter(new  SmsAdapter(MainActivity.this, snsList));
				}
    			 
    			 if (msg.what==0x102) {
					 keywListview.setAdapter(new InterKeywordAdapter(MainActivity.this,keyWordList));
				 }
    			 if (msg.what==0x103) {
    				 if (isBlackAddre) {
        				 bwListview.setAdapter(new InterKeywordAdapter(MainActivity.this,blackAddressList)); 

					}else {
       				 bwListview.setAdapter(new InterKeywordAdapter(MainActivity.this,whiteAddressList)); 

					}
				}
    			  super.handleMessage(msg);
    		}
    	  };

    	  
    	        new Thread(new Runnable() {
					
					@Override
					public void run() {
					 
						while (isRun) {
							if (isGetBla) {
								isGetBla=false;
								if (isBlackAddre) {
									 if (blackAddressList!=null) {
											blackAddressList.clear();
										}
									 
										Cursor b=sql.getEncrypts(null, null);
										while (b.moveToNext()) {
										  blackAddressList.add(b.getString(b.getColumnIndex("address")));
											
										}
										b.close();
										mHandler.sendEmptyMessage(0x103);
								}else {
									
									if (whiteAddressList!=null) {
										whiteAddressList.clear();
									}
									
									Cursor w=sql.getWhiteEncrypts(null, null);
									while ( w.moveToNext()) {
										 whiteAddressList.add(w.getString(w.getColumnIndex("address")));
										
									}
									w.close();
									mHandler.sendEmptyMessage(0x103);
								}
								
									
									
								
							}
							
							
							
							 if (isGetKeyw) {
								 isGetKeyw=false;
								 if (keyWordList!=null) {
										keyWordList.clear();
									}
								    Cursor k=sql.getInterKeywordMs(null, null);
									while (k.moveToNext()) {
									  keyWordList.add(k.getString(k.getColumnIndex("inword")));
										
									}
									k.close();
									
								 mHandler.sendEmptyMessage(0x102);
							}
							if (isFirstEnd) {
								isFirstEnd=false;
								 if (keyWordList!=null) {
									keyWordList.clear();
								}
								 if (blackAddressList!=null) {
									blackAddressList.clear();
								}
								if (whiteAddressList!=null) {
									whiteAddressList.clear();
								}
								
								Cursor k=sql.getInterKeywordMs(null, null);
								while (k.moveToNext()) {
								  keyWordList.add(k.getString(k.getColumnIndex("inword")));
									
								}
								k.close();
								
								Cursor b=sql.getEncrypts(null, null);
								while (b.moveToNext()) {
								  blackAddressList.add(b.getString(b.getColumnIndex("address")));
									
								}
								b.close();
								
								Cursor w=sql.getWhiteEncrypts(null, null);
								while ( w.moveToNext()) {
									 whiteAddressList.add(w.getString(w.getColumnIndex("address")));
									
								}
								w.close();
								
								
								
							}
							
							time=time+100;
						  if (time==1300) {
							mHandler.sendEmptyMessage(0x100);
						    }
							
						  
						  
							
						  if (isGetInBox) {
							 isGetInBox=false;
							 
							 
							 
						       
					 	     if (snsList!=null) {
					 	    	snsList.clear();
					 		     }
					 	 		Cursor c=sql.getMs(null, null);
					 			while (c.moveToNext()) {
					 				
					 				 SmsBean bean =new  SmsBean();
					 				 bean.address=c.getString(c.getColumnIndex("address"));
					 				 
					 				 bean.body=c.getString(c.getColumnIndex("body"));
					 				 bean.date=c.getString(c.getColumnIndex("date"));
					 				 bean.type=c.getString(c.getColumnIndex("type"));
					 				 
					 				 snsList.add(bean);
					 				 
					 			   }
					 			c.close();
					 			 
					 			
							 mHandler.sendEmptyMessage(0x101);
						}
						  
						  
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
						
						
					}
				}).start();
	}
     private int mWidth,mHeight;
	  // 获得屏幕宽高
private void getHW() {
		DisplayMetrics ds=new DisplayMetrics();
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
	    wm.getDefaultDisplay().getMetrics(ds);
	  
		mHeight=(int) ((ds.heightPixels));
		mWidth=(int)(ds.widthPixels);

	}

public class SmsReicervier extends BroadcastReceiver{
     @Override
public void onReceive(Context context, Intent intent) {
	// TODO Auto-generated method stub
    	  if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			
    	  Log.e("接收到了",intent.toString());
    	 
	Object[]sms=(Object[]) intent.getExtras().get("pdus");
	for (int i = 0; i < sms.length; i++) {
		byte[]data=(byte[]) sms[i];
		SmsMessage message=SmsMessage.createFromPdu(data);
		String address=message.getOriginatingAddress();
		 String content=message.getMessageBody();
		  
		 Date date=new Date(message.getTimestampMillis());
		 SimpleDateFormat formar=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String sendTime=formar.format(date);
		  Log.e("受到短信",content+address+sendTime);
		  
		  for (int j = 0; j < whiteAddressList.size(); j++) {
			      if (address.contains(whiteAddressList.get(j))) {
					
			    	  return;
				    }
	        	}
		  
		  for (int j = 0; j < blackAddressList.size(); j++) {
			   
			    if (address.contains(blackAddressList.get(j))) {
			    	Uri uri = Uri.parse("content://sms");  
					if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
						 try {
							getContentResolver().delete(uri, "address= "+"'"+address+"'"+" and date = "+"'"+date+"'", null);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					  Log.d("拦截删除", address);
					 }
					sql.addMEvent(content, address,"1", sendTime);
					 
					Toast.makeText(MainActivity.this, "黑名单拦截一条信息*", Toast.LENGTH_SHORT).show();
					isGetInBox=true;
					return;
				}
		      }
		  for (int j = 0; j <keyWordList.size(); j++) {
			  if (address.contains(keyWordList.get(j))) {
			    	Uri uri = Uri.parse("content://sms");  
					if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
						 try {
							getContentResolver().delete(uri, "address= "+"'"+address+"'"+" and date = "+"'"+date+"'", null);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					  Log.d("拦截删除", address);
					 }
					sql.addMEvent(content, address,"1", sendTime);
					isGetInBox=true;
					Toast.makeText(MainActivity.this, "过滤词拦截一条信息*"+keyWordList.get(j), Toast.LENGTH_SHORT).show();
					return;
				}
		}
		   
 
			
 
		 
		}
	}
}
}
@Override
protected void onDestroy() {
   isRun=false;
   unregisterReceiver(reicever);
	super.onDestroy();
}
}
