package org.myproject.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.myproject.messenger.model.Comment;
import org.myproject.messenger.service.CommentService;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResources {
	
	private CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId){
		return commentService.getAllComments(messageId);
	}
	
	@POST
	public Response addMessage(@PathParam("messageId") long messageId, Comment comment) {
		Comment newComment =  commentService.addComment(messageId, comment);
		if(newComment == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(newComment)
					.build();
		} else {
			return Response.status(Status.ACCEPTED)
					.entity(newComment)
					.build();
		}
	}
	
	@PUT
	@Path("/{commentId}")
	public Response updateMessage(@PathParam("messageId") long messageId, @PathParam("commentId") long id, Comment comment) {
		comment.setId(id);
		Comment updateComment =  commentService.updateComment(messageId, comment);	
		if(updateComment == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(updateComment)
					.build();
		} else {
			return Response.status(Status.ACCEPTED)
					.entity(updateComment)
					.build();
		}
	}
	
	@DELETE
	@Path("/{commentId}")
	public Response deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		Comment comment = commentService.removeComment(messageId, commentId);	
		if(comment == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(comment)
					.build();
		} else {
			return Response.status(Status.NO_CONTENT)
					.entity(comment)
					.build();
		}
	}
	
	@GET
	@Path("/{commentId}")
	public Response getMessage(@PathParam("messageId") long messageId,@PathParam("commentId") long commentId){
		Comment comment = commentService.getComment(messageId,commentId);
		if(comment == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(comment)
					.build();
		} else {
			return Response.status(Status.NO_CONTENT)
					.entity(comment)
					.build();
		}
	}
	

}
