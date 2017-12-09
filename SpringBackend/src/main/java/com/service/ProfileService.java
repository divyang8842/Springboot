package com.service;

import com.entity.Profile;
import com.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    public Profile getUserProfile(int userid){
        return profileRepository.findByUserid(userid);
    }

    public void saveProfile(Profile profile){
        profileRepository.save(profile);
    }

}
