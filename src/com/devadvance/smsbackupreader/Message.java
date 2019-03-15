package com.devadvance.smsbackupreader;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class Message implements Comparable<Message> {
	private String messageText;
	private int messageType;
	private BigInteger messageDate;
	private BigInteger messageAddress;
	private Date messageDateFormat;
	private List<Attachment> messageAttachments;

	public Message() {
		messageType = -1;
		messageDate = BigInteger.valueOf(-1);
		messageText = "";
		messageAddress = BigInteger.valueOf(-1);
		messageDateFormat = new Date();
	}

	/**
	 * Constructor to create a message with specified parameters.
	 * @param address address of the message.
	 * @param date date of the message.
	 * @param body text of the message;
	 * @param type Type of message, 1 is received, 0 is sent.
	 * @param offset Offset if it is a received message.
	 */
	public Message(BigInteger address,BigInteger date,String body,int type, BigInteger offset) {
		messageType = type;

		messageDate = date;
		// If it is a received message, add the offset
		if (messageType == 1)
			messageDate = messageDate.add(offset);
		messageText = body;
		messageAddress = address;
		messageDateFormat = new Date(messageDate.longValue());
	}

	/**
	 * Constructor to create a message with specified parameters.
	 * @param address address of the message.
	 * @param date date of the message.
	 * @param body text of the message;
	 * @param type Type of message, 1 is received, 0 is sent.
	 * @param offset Offset if it is a received message.
	 * @param attachments Attachments from MMS message.
	 */
	public Message(BigInteger address,BigInteger date,String body,int type, BigInteger offset, List<Attachment> attachments) {
		messageType = type;

		messageDate = date;
		// If it is a received message, add the offset
		if (messageType == 1)
			messageDate = messageDate.add(offset);
		messageText = body;
		messageAddress = address;
		messageDateFormat = new Date(messageDate.longValue());
		
		messageAttachments = attachments;
	}
	
	@Override
	public int compareTo(Message msg2) { 

		return (this.getMessageDate().compareTo(msg2.getMessageDate())); 
	}

	public void setMessageText(String input) {
		messageText = input;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageType(int input) {
		messageType = input;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageDate(BigInteger input) {
		messageDate = input;
		messageDateFormat = new Date(messageDate.longValue());
	}

	public BigInteger getMessageDate() {
		return messageDate;
	}

	public void setMessageAddress(BigInteger input) {
		messageAddress = input;
	}

	public BigInteger getMessageAddress() {
		return messageAddress;
	}

	public String toHtml() {
		String tempString;
		String fromTo;
		
		tempString = "<div class=\"message-";
		
		if (messageType == 2)
			fromTo = "to\">";
		else
			fromTo = "from\">";
		//tempString += messageDateFormat.toString() + ":  ";
		tempString += fromTo + messageText;
		
		if(messageAttachments != null && messageAttachments.size() > 0){
			for(int i = 0; i < messageAttachments.size(); i++){
				tempString += messageAttachments.get(i).toHtml();
			}
		}
		
		tempString += "<div class=\"date-" + fromTo + messageDateFormat.toString() + "</div>";
		tempString += "</div>";
		
		return tempString;
	}
	
	@Override
	public String toString() {
		String tempString;
		if (messageType == 2)
			tempString = "Sent: ";
		else
			tempString = "Received: ";
		tempString += messageDateFormat.toString() + ":  ";
		tempString += messageText;
		
		if(messageAttachments != null && messageAttachments.size() > 0){
			for(int i = 0; i < messageAttachments.size(); i++){
				tempString += messageAttachments.get(i).toString();
			}
		}
		
		return tempString;
	}
}
