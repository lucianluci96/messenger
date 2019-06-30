package org.myproject.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.myproject.messenger.database.DatabaseClass;
import org.myproject.messenger.exception.DataNotFoundException;
import org.myproject.messenger.model.Message;

public class MessageService {

	private Map<Long,Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L,new Message (1,"Test Luci",new Date(),"Lucian"));
		messages.put(2L,new Message (2,"Test Luci2",new Date(),"Lucian2"));
		messages.put(3L,new Message (3,"Test Luci3",new Date(),"Lucian3"));
		
		
	}
	
	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
			
	}
	
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messageForYear = new ArrayList<> ();
		Calendar calendar = Calendar.getInstance();
		for(Message message : messages.values()) {
			calendar.setTime(message.getCreated());
			if(calendar.get(Calendar.YEAR) == year) {
				messageForYear.add(message);	
			}
		}
		return messageForYear;
		
	}

	public List<Message> getAllMessagesPaginated(int start,int size) {
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if(start+ size > list.size()) return new ArrayList<Message>();
		return list.subList(start,start+size);
	}
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if (message == null) {
			throw new DataNotFoundException("Message with id " + id + " not found");	
		}
		return message;
	}
	public Message addMessage(Message message) {		
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
		
	}
	public Message updateMessage(Message message) {
		if(message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	public Message removeMessage(long id) {
		return messages.remove(id);
		
	}
	
}
