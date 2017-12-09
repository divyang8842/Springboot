package com.controller;

import com.entity.*;
import com.service.DirectoryService;
import com.service.StaredService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.StringTokenizer;

@Controller    // This means that this class is a Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/star") // This means URL's start with /directory (after Application path)
public class StaringController {

    @Autowired
    StaredService staredService;

    @Autowired
    DirectoryService directoryService;

    @PostMapping(path="/get",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>
    getStartedDirectory(@RequestBody StaredDirectory staredDirectory,
                        HttpSession httpSession){
        String userid = String.valueOf(httpSession.getAttribute("id"));
        System.out.println("userid is "+ userid);
        List<StaredDirectory> list = staredService.getAllStaredDirectoryForUser(Integer.parseInt(userid));
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping(path="/unstar")
    public @ResponseBody void stareFile(@RequestBody StaredDirectory staredDirectory, HttpSession httpSession){

        String userid = String.valueOf(httpSession.getAttribute("id"));
        StaredDirectory oldStaredDirectory =  staredService.getStaredDirectory(Integer.parseInt(userid),staredDirectory.getFilepath());
        if (oldStaredDirectory != null) {
            oldStaredDirectory.setDeleteflag(1);
            staredService.starDirectory(oldStaredDirectory);
        }
    }

    @PostMapping(path="/star")
    public @ResponseBody void unstarFile(@RequestBody StaredDirectory staredDirectory, HttpSession httpSession){
        Directory directory =  directoryService.getDirectoryFromPath(staredDirectory.getFilepath());
        String userid = String.valueOf(httpSession.getAttribute("id"));
        if (directory != null) {
            staredDirectory.setDirectoryid(directory.getId());
            staredDirectory.setFilename(directory.getFilename());
            staredDirectory.setUserid(Integer.parseInt(userid));
            staredDirectory.setFilepath(directory.getFilepath());
            staredService.starDirectory(staredDirectory);
        }
    }
}
