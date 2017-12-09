package com.controller;

import com.entity.Directory;
import com.entity.DirectoryLogs;
import com.entity.FileLists;
import com.service.DirectoryLoggingService;
import com.service.DirectoryPermissionService;
import com.service.DirectoryService;
import com.service.StaredService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller    // This means that this class is a Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/directory") // This means URL's start with /directory (after Application path)


public class DirectoryController {
    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private DirectoryLoggingService directoryLoggingService;

    @Autowired
    private StaredService staredService;

    @Autowired
    private DirectoryPermissionService directoryPermissionService;

    @PostMapping(path = "/getChildDir", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    FileLists getChildDir(@RequestBody Directory directory,HttpSession session) {

        String userid = String.valueOf(session.getAttribute("id")) ;
        JSONObject jsonObject = new JSONObject(directory);
        System.out.println("in get child dir " + jsonObject.toString());
        directory = directoryService.getDirectoryFromPath(jsonObject.getString("filepath"));
        jsonObject = new JSONObject(directory);

        FileLists fileList = new FileLists();
        fileList.setStatus("200");
        fileList.setFilelist(directoryService.getByParentId(jsonObject.getInt("id")));
        fileList.setSharedlist(directoryPermissionService.getPermissionForUser(Integer.parseInt(userid)));
        fileList.setStaredlist(staredService.getAllStaredDirectoryForUser(Integer.parseInt(userid)));
        System.out.println(fileList.getSharedlist().toString());
        // returnObj.put("filelist",);
        return fileList;
    }


    @PostMapping(path = "/mkdir", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    FileLists cerateDirectory(@RequestBody Directory directory, HttpSession session) {
        /*JSONObject jsonObject = new JSONObject(directory);
        System.out.println("in mkdir " + jsonObject.toString());
        */
        String userid = String.valueOf(session.getAttribute("id")) ;
        //System.out.println("in mkdir : userid is " + userid);
        directoryService.createDirectory(directory.getFilename(), directory.getFilepath(), Integer.parseInt(userid));
       // directoryService.createDirectory(String.valueOf(user.getId()), "", -1);
        FileLists fileList = new FileLists();
        fileList.setStatus("200");
        //fileList.setFilelist(directoryService.getByParentId(directory.getParentId()));
        // returnObj.put("filelist",);
        return fileList;
    }

    @PostMapping(path="/delDir",consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    void deleteDirectory(@RequestBody Directory directory){

        Directory oldDirectory = directoryService.getDirectoryFromPath(directory.getFilepath()+"/"+directory.getFilename());
        oldDirectory.setDeleteflag(1);
        directoryService.deleteDirectory(oldDirectory);

    }

    @PostMapping(path="/download",consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<InputStreamResource> getFile(@RequestBody Directory directory,
                                                HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        long contentLength = 0;
        InputStreamResource inputStreamResource = null;
        try {
            // get your file as InputStream
            InputStream inputStream = directoryService.getIStreamFromFileName(directory.getFilepath());


            // copy it to response's OutputStream

            inputStreamResource = new InputStreamResource(inputStream);

            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            contentLength = directoryService.getContentLength(directory.getFilepath());

        } catch (Exception ex) {
           // log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            //throw new RuntimeException("IOError writing file to output stream");
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(contentLength)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(inputStreamResource);
    }


    @PostMapping(path="getUserLogs",consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    FileLists getUSerLogs(@RequestBody String data){
        JSONObject jsonObject = new JSONObject(data);
        int userid = Integer.parseInt( jsonObject.getString("userid"));
        System.out.println("userid is "+ userid);
        FileLists fileLists =  new FileLists();
        fileLists.setStatus("200");
        fileLists.setDirectoryLogs(directoryLoggingService.getAllUserLogs(userid));
        System.out.println(fileLists.getDirectoryLogs().toString());
        return fileLists;
    }
}