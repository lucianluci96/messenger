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

import org.myproject.messenger.model.Profile;
import org.myproject.messenger.service.ProfileService;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResources {

	private ProfileService profileService = new ProfileService();
	
	@GET
	public List<Profile> getProfiles() {
		return profileService.getAllProfiles();
		
	}	
	
	@POST
	public Response addProfile(Profile profile) {
		Profile newProfile = profileService.addProfile(profile);	
		return Response.status(Status.CREATED)
				.entity(newProfile)
				.build();	 
	}
	
	@PUT
	@Path("/{profileName}")
	public Response updateProfile(@PathParam ("profileName")String profileName,Profile profile) {
		profile.setProfileName(profileName);
		Profile updateProfile =  profileService.updateProfile(profile);	
		if(updateProfile == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(updateProfile)
					.build();
		} else {
			return Response.status(Status.ACCEPTED)
					.entity(updateProfile)
					.build();
		}
	}
	
	@DELETE
	@Path("/{profileName}")
	public Response deleteProfile(@PathParam ("profileName") String profileName) {
		Profile profile = profileService.removeProfile(profileName);
		if(profile == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(profile)
					.build();
		} else {
			return Response.status(Status.NO_CONTENT)
					.entity(profile)
					.build();
		}
	}	
	
	@GET
	@Path("/{profileName}")
	public Response getProfile(@PathParam ("profileName") String profileName) {
		Profile profile = profileService.getProfile(profileName);
		if(profile == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(profile)
					.build();
		} else {
			return Response.status(Status.NO_CONTENT)
					.entity(profile)
					.build();
		}
	}
	
	
}
