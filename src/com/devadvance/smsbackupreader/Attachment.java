package com.devadvance.smsbackupreader;

public class Attachment {
	private String mimeType;
	private String base64Data;
	private String fileName;
	
	public Attachment(String _mimeType, String _base64Data, String _fileName){
		mimeType = _mimeType;
		base64Data = _base64Data;
		fileName = _fileName;
	}
	
	public String getMimeType(){
		return mimeType;
	}
	
	public void setMimeType(String input){
		mimeType = input;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String input){
		fileName = input;
	}
	
	public String getBase64Data(){
		return base64Data;
	}
	
	public void setBase64Data(String input){
		base64Data = input;
	}

	public String getBaseType(){
		return mimeType.substring(0, mimeType.indexOf('/'));
	}
	
	public String toHtml(){
		String tempString;
		
		//tempString = "<a href=\"" + base64Data + "\">" + fileName + "</a>";
		tempString = "<a href=\"" + fileName + "\">" + fileName + "</a>";
		
		return tempString;
	}

	@Override
	public String toString(){
		String tempString;
		
		tempString = "\n";
		tempString += "\tAttachment Name: " + fileName + "\n";
		tempString += "\tAttachment Type: " + mimeType;
		
		return tempString;
	}
}
