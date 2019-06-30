package org.myproject.messenger.resources;


import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.myproject.messenger.model.Message;
import org.myproject.messenger.resources.beans.MessageFilterBean;
import org.myproject.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResources {

	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart() > 0 && filterBean.getSize() >0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo){
		Message newMessage = messageService.addMessage(message);
		return Response.status(Status.CREATED)
				.entity(newMessage)
				.build();	 
	}
	
	@PUT
	@Path("/{messageId}")
	public Response updateMessage(@PathParam ("messageId")long id,Message message) {
		message.setId(id);
		Message updateMessage = messageService.updateMessage(message);	
		if(updateMessage == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(updateMessage)
					.build();
		} else {
			return Response.status(Status.ACCEPTED)
					.entity(updateMessage)
					.build();
		}
	}
	
	@DELETE
	@Path("/{messageId}")
	public Response deleteMessage(@PathParam ("messageId")long id) {
		Message message = messageService.removeMessage(id);
		if(message == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(message)
					.build();
		} else {
			return Response.status(Status.NO_CONTENT)
					.entity(message)
					.build();
		}
	}	
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam ("messageId")long id , @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo,message),"self");
		message.addLink(getUriForProfile(uriInfo,message),"profile");
		message.addLink(getUriForComments(uriInfo,message),"comments");
		return message;
		
		
	//	if(message == null) {
	//		return Response.status(Status.NOT_FOUND)
	//				.entity(message)
	//				.build();
	//	}
	//	return Response.status(Status.FOUND)
	//			.entity(message)
	//			.build();
	}
	
	private String getUriForComments(UriInfo uriInfo, Message message) { 
		URI uri = uriInfo.getBaseUriBuilder()
				.path(MessageResources.class)
				.path(MessageResources.class,"getCommentResource")
				.path(CommentResources.class)
				.resolveTemplate("messageId",message.getId())
				.build();
			return uri.toString();	
		
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
							.path(CommentResources.class)
							.path(message.getAuthor())
							.build();
			return uri.toString();		
	}
	
	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResources.class)
				.path(Long.toString(message.getId()))
				.build()
				.toString();
		return uri;
	}
	
	
	@Path("/{messageId}/comments")
	public CommentResources getCommentResource() {
		return new CommentResources();	
	}
	
}
