package com.controller;

import com.entity.Directory;
import com.entity.DirectoryPermission;
import com.entity.User;
import com.service.DirectoryPermissionService;
import com.service.DirectoryService;
import com.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/permission") // This means URL's start with /directory (after Application path)
public class DirectoryPermissionController {

    @Autowired
    DirectoryPermissionService directoryPermissionService;

    @Autowired
    DirectoryService directoryService;

    @Autowired
    UserService userService;

    @PostMapping(path="/add")
    public @ResponseBody void shareFile(@RequestBody String data, HttpSession session){

        JSONObject json = new JSONObject(data);
        StringTokenizer sTk = new StringTokenizer(json.getString("emails"));
        Directory directory =  directoryService.getDirectoryFromPath(json.getString("fileToShare"));

        while(sTk.hasMoreElements()){
            User user = userService.getUserByEmailId(sTk.nextToken());
            DirectoryPermission directoryPermission = new DirectoryPermission();
            directoryPermission.setDirectoryid(directory.getId());
            directoryPermission.setFilename(directory.getFilename());
            directoryPermission.setFilepath(directory.getFilepath());
            directoryPermission.setPermissiontype(0);
            directoryPermission.setPermitid(user.getId());
            directoryPermission.setFile(directory.isFile());
            directoryPermissionService.addPremission(directoryPermission);
        }

    }

}
