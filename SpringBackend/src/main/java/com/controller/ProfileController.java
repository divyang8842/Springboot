package com.controller;

import com.entity.Profile;
import com.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller    // This means that this class is a Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/profile") // This means URL's start with /directory (after Application path)
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProfile(@RequestBody Profile profile, HttpSession session) {
        String userid =  String.valueOf(session.getAttribute("id"));

        Profile oldProfile = profileService.getUserProfile(Integer.parseInt(userid));
        if(oldProfile!=null){
            profile.setId(oldProfile.getId());
        }
        profile.setUserid(Integer.parseInt(userid));
        profileService.saveProfile(profile);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/get", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfile( HttpSession session) {
        String userid =  String.valueOf(session.getAttribute("id"));
        Profile oldProfile = profileService.getUserProfile(Integer.parseInt(userid));
        if(oldProfile==null){
            oldProfile = new Profile();
            oldProfile.setUserid(Integer.parseInt(userid));
        }
        return new ResponseEntity(oldProfile,HttpStatus.OK);
    }
}
