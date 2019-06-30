package org.myproject.messenger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.myproject.messenger.database.DatabaseClass;
import org.myproject.messenger.exception.DataNotFoundException;
import org.myproject.messenger.model.Profile;

public class ProfileService {

private Map<String,Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("Lucian", new Profile(1L,"Lucian","Lucian","Farcas",new Date()));
		profiles.put("Lucian2", new Profile(2L,"Lucian2","Lucian2","Farcas2",new Date()));
		profiles.put("Lucian3", new Profile(3L,"Lucian3","Lucian3","Farcas3",new Date()));
		
	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());
			
	}
	public Profile getProfile(String profileName) {	
		Profile profile = profiles.get(profileName);
		if (profile == null) {
			throw new DataNotFoundException("Profile with name " + profileName + " not found");
			
		}
		return profile;
		
	}
	public Profile addProfile(Profile profile) {		
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
		
	}
	public Profile updateProfile(Profile profile) {
		if(profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
		
	}
	
	
}
