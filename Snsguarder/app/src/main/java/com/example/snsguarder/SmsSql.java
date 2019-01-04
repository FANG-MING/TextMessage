package com.example.snsguarder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
 
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SmsSql extends SQLiteOpenHelper{
	private static final String DATABASE_NAME="smslist.db";//数据库名称
	private static final int SCHEMA_VERSION=1; 
	public SmsSql(Context context 
	 ) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
  // 创建数据库表
  	   db.execSQL("CREATE TABLE  IF NOT EXISTS smss (body TEXT,address TEXT,type TEXT,date TEXT);");
  	  // db.execSQL("CREATE TABLE  IF NOT EXISTS intersmss (body TEXT,address TEXT,type TEXT,date TEXT);");
   	   db.execSQL("CREATE TABLE  IF NOT EXISTS interceptwords (inword TEXT);");
  	  // db.execSQL("CREATE TABLE  IF NOT EXISTS sensitiveword (inword TEXT,replaceword TEXT);");
  	 
  	   
  	  db.execSQL("CREATE TABLE  IF NOT EXISTS encrypt (address TEXT);");
	  db.execSQL("CREATE TABLE  IF NOT EXISTS whiteencrypt (address TEXT);");//白名单
	  
 	//  db.execSQL("CREATE TABLE  IF NOT EXISTS encryptsms (body TEXT,address TEXT,type TEXT,date TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
 	 
	   public void addEncryptSmsEvent(String body,String address,String type,String date) {
			//添加用户信息
	    	ContentValues cv=new ContentValues();
			SQLiteDatabase db = this.getWritableDatabase(); 	
			
			 cv.put("body", body);
			 cv.put("address", address);
			 cv.put("type", type);
			  cv.put("date",date);
			 db.insert("encryptsms",null,cv);
		} 
	   
	   
	    
	   
	  public void cancelEncryptSmsEvent(String date,String body) {
			SQLiteDatabase db = this.getWritableDatabase(); 
	             
	    	db.execSQL("delete from encryptsms where date="+"'"+date+"'"+" and "+"body="+"'"+body+"'");
		}
		public Cursor getEncryptSms(String where, String orderBy) { 
			//查询 用户信息
			StringBuilder buf=new StringBuilder("SELECT * FROM encryptsms");
			SQLiteDatabase db = this.getWritableDatabase(); 
			if (where!=null) {
				buf.append(" WHERE ");
				buf.append(where);
			}
			
			if (orderBy!=null) {
				buf.append(" ORDER BY ");
				buf.append(orderBy);
			}
			
			return(db.rawQuery(buf.toString(), null));
		}
		   public void deleteallEncryptSms() {
		    	SQLiteDatabase db = this.getWritableDatabase(); 

		    	db.execSQL("delete from encryptsms");

			}
		   
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	   public void addEncryptEvent( String address ) {
			//添加用户信息
	    	ContentValues cv=new ContentValues();
			SQLiteDatabase db = this.getWritableDatabase(); 	
			  
			 cv.put("address", address);
			 
			   
			 db.insert("encrypt",null,cv);
		} 
	   
	    
	   
	  public void cancelEncryptEvent(String address ) {
			SQLiteDatabase db = this.getWritableDatabase(); 
	             
	    	db.execSQL("delete from encrypt where address="+"'"+address+"'" );
		}
		public Cursor getEncrypts(String where, String orderBy) { 
			//查询 用户信息
			StringBuilder buf=new StringBuilder("SELECT * FROM encrypt");
			SQLiteDatabase db = this.getWritableDatabase(); 
			if (where!=null) {
				buf.append(" WHERE ");
				buf.append(where);
			}
			
			if (orderBy!=null) {
				buf.append(" ORDER BY ");
				buf.append(orderBy);
			}
			
			return(db.rawQuery(buf.toString(), null));
		}
		   public void deleteallEncrypt() {
		    	SQLiteDatabase db = this.getWritableDatabase(); 

		    	db.execSQL("delete from encrypt");

			}
		   
	
	
	
	
	
		   

			
		   public void addWhiteEncryptEvent( String address ) {
				//添加白名单用户信息
		    	ContentValues cv=new ContentValues();
				SQLiteDatabase db = this.getWritableDatabase(); 	
				  
				 cv.put("address", address);
				 
				   
				 db.insert("whiteencrypt",null,cv);
			} 
		   
		    
		   
		  public void cancelWhiteEncryptEvent(String address ) {
				SQLiteDatabase db = this.getWritableDatabase(); 
		             
		    	db.execSQL("delete from whiteencrypt where address="+"'"+address+"'" );
			}
			public Cursor getWhiteEncrypts(String where, String orderBy) { 
				//查询 用户信息
				StringBuilder buf=new StringBuilder("SELECT * FROM whiteencrypt");
				SQLiteDatabase db = this.getWritableDatabase(); 
				if (where!=null) {
					buf.append(" WHERE ");
					buf.append(where);
				}
				
				if (orderBy!=null) {
					buf.append(" ORDER BY ");
					buf.append(orderBy);
				}
				
				return(db.rawQuery(buf.toString(), null));
			}
			   public void deleteallWhiteEncrypt() {
			    	SQLiteDatabase db = this.getWritableDatabase(); 

			    	db.execSQL("delete from whiteencrypt");

				}
			   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
	
	
	
	
	
	
	
	
	
			   public void addMEvent(String body,String address,String type,String date) {
					//添加用户信息
			    	ContentValues cv=new ContentValues();
					SQLiteDatabase db = this.getWritableDatabase(); 	
					
					 cv.put("body", body);
 					 cv.put("address", address);
					 cv.put("type", type);
 					  cv.put("date",date);
					 db.insert("smss",null,cv);
				} 
			   
			    public void updateMEvent(String body,String address,String type,String date,String oldTime,String oldContent) {
					//  更新备忘
  			    	ContentValues cv=new ContentValues();
					SQLiteDatabase db = this.getWritableDatabase(); 	
					
					 cv.put("body", body);
					  
					 cv.put("type", type);
					 
						 cv.put("date",date);
					  
  					 String s="time= "+"'"+oldTime+"'"+" and "+"content= "+"'"+oldContent+"'";
 					  
 					 String[] args={date,body};
					  db.update("smss", cv, s, null);
 
				}
			    
			   
			  public void cancelMEvent(String date,String body) {
					SQLiteDatabase db = this.getWritableDatabase(); 
			             
			    	db.execSQL("delete from smss where date="+"'"+date+"'"+" and "+"body="+"'"+body+"'");
				}
				public Cursor getMs(String where, String orderBy) { 
					//查询 用户信息
					StringBuilder buf=new StringBuilder("SELECT * FROM smss");
					SQLiteDatabase db = this.getWritableDatabase(); 
					if (where!=null) {
						buf.append(" WHERE ");
						buf.append(where);
					}
					
					if (orderBy!=null) {
						buf.append(" ORDER BY ");
						buf.append(orderBy);
					}
					
					return(db.rawQuery(buf.toString(), null));
				}
				   public void deleteallMemos() {
				    	SQLiteDatabase db = this.getWritableDatabase(); 

				    	db.execSQL("delete from smss");

					}
				   
				   
				   
//				   
//				   public void addInterMEvent(String body,String address,String type,String date) {
//						//添加用户信息
//				    	ContentValues cv=new ContentValues();
//						SQLiteDatabase db = this.getWritableDatabase(); 	
//						
//						 cv.put("body", body);
//	 					 cv.put("address", address);
//						 cv.put("type", type);
//	 					  cv.put("date",date);
//						 db.insert("intersmss",null,cv);
//					} 
//				   
//				    public void updateInterMEvent(String body,String address,String type,String date,String oldTime,String oldContent) {
//						//  更新备忘
//	  			    	ContentValues cv=new ContentValues();
//						SQLiteDatabase db = this.getWritableDatabase(); 	
//						
//						 cv.put("body", body);
//						  
//						 cv.put("type", type);
//						 
//							 cv.put("date",date);
//						  
//	  					 String s="time= "+"'"+oldTime+"'"+" and "+"content= "+"'"+oldContent+"'";
//	 					  
//	 					 String[] args={date,body};
//						  db.update("intersmss", cv, s, null);
//	 
//					}
//				    
//				   
//				  public void cancelInterMEvent(String date,String body) {
//						SQLiteDatabase db = this.getWritableDatabase(); 
//				             
//				    	db.execSQL("delete from intersmss where date="+"'"+date+"'"+" and "+"body="+"'"+body+"'");
//					}
//					public Cursor getInterMs(String where, String orderBy) { 
//						//查询 用户信息
//						StringBuilder buf=new StringBuilder("SELECT * FROM intersmss");
//						SQLiteDatabase db = this.getWritableDatabase(); 
//						if (where!=null) {
//							buf.append(" WHERE ");
//							buf.append(where);
//						}
//						
//						if (orderBy!=null) {
//							buf.append(" ORDER BY ");
//							buf.append(orderBy);
//						}
//						
//						return(db.rawQuery(buf.toString(), null));
//					}
//					   public void deleteallInterMemos() {
//					    	SQLiteDatabase db = this.getWritableDatabase(); 
//
//					    	db.execSQL("delete from intersmss");
//
//						}
//				   
//					   
					   
					   
					   
					   public void addInterKeywordMEvent(String inword) {
							//添加用户信息
					    	ContentValues cv=new ContentValues();
							SQLiteDatabase db = this.getWritableDatabase(); 	
							
							 cv.put("inword", inword);
		 				 
							 
							 db.insert("interceptwords",null,cv);
						} 
					 
					    
//					    public void updateInterKeywordMEvent(String inword,String address,String newword,String newaddress) {
//							//  更新备忘
//		  			    	ContentValues cv=new ContentValues();
//							SQLiteDatabase db = this.getWritableDatabase(); 	
//							
//							 cv.put("inword", newword);
//							  
//							 cv.put("address", newaddress);
//							 
//								 
//							  
//		  					 String s="inword= "+"'"+inword+"'"+" and "+"address= "+"'"+address+"'";
//		 					  
//		 					 String[] args={inword,address};
//							  db.update("interceptwords", cv, s, null);
//		 
//						}
//					   
					  public void cancelInterKeywordMEvent( String inword) {
							SQLiteDatabase db = this.getWritableDatabase(); 
 					    	db.execSQL("delete from interceptwords where inword"+"="+"'"+inword+"'");
						    }
						public Cursor getInterKeywordMs(String where, String orderBy) { 
							//查询 用户信息
							StringBuilder buf=new StringBuilder("SELECT * FROM interceptwords");
							SQLiteDatabase db = this.getWritableDatabase(); 
							if (where!=null) {
								buf.append(" WHERE ");
								buf.append(where);
							}
							
							if (orderBy!=null) {
								buf.append(" ORDER BY ");
								buf.append(orderBy);
							}
							
							return(db.rawQuery(buf.toString(), null));
						}
						   public void deleteallInterKeywordMemos() {
						    	SQLiteDatabase db = this.getWritableDatabase(); 

						    	db.execSQL("delete from interceptwords");

							}	   
					   
					   
					   
					   
					   
					   
					   
					   
					   
//					   
//					  public void cancelSensitiwordMEvent( String inword) {
//							SQLiteDatabase db = this.getWritableDatabase(); 
// 					    	db.execSQL("delete from sensitiveword where inword="+"'"+inword+"'");
//						    }
//						public Cursor getSensitiwordMs(String where, String orderBy) { 
//							//查询 用户信息
//							StringBuilder buf=new StringBuilder("SELECT * FROM sensitiveword");
//							SQLiteDatabase db = this.getWritableDatabase(); 
//							if (where!=null) {
//								buf.append(" WHERE ");
//								buf.append(where);
//							}
//							
//							if (orderBy!=null) {
//								buf.append(" ORDER BY ");
//								buf.append(orderBy);
//							}
//							
//							return(db.rawQuery(buf.toString(), null));
//						}
//						   public void deleteallSensitiwordMemos() {
//						    	SQLiteDatabase db = this.getWritableDatabase(); 
//
//						    	db.execSQL("delete from sensitiveword");
//
//							}	   
//					    
//						   public void addSensitiwordMEvent(String inword,String replaceword) {
//								//添加用户信息
//						    	ContentValues cv=new ContentValues();
//								SQLiteDatabase db = this.getWritableDatabase(); 	
//								
//								 cv.put("inword", inword);
//								 cv.put("replaceword", replaceword);
//								 
//								 db.insert("sensitiveword",null,cv);
//							} 
//						   
				
					   
    public void releaseEvent() {
		SQLiteDatabase db = this.getWritableDatabase(); 
		db.close();
	}  
     
  
}
