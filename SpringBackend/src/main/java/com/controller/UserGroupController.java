package com.controller;

import com.entity.UserGroup;
import com.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/group") // This means URL's start with /demo (after Application path)
public class UserGroupController {

    @Autowired
    UserGroupService userGroupService;

    @PostMapping(path = "/add",consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> saveUserGroup(@RequestBody UserGroup userGroup){

        userGroupService.addGroup(userGroup);
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping(path = "/get", consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>   getUserGroups(){

        List<UserGroup> list= userGroupService.getUserGroups();
        return new ResponseEntity(list,HttpStatus.OK);
    }





}
