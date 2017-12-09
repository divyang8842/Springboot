package com.controller;

import com.service.DirectoryService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/files") // This means URL's start with /files (after Application path)

public class UploadController {
    @Autowired
    DirectoryService directoryService;

    @PostMapping(path = "/upload")
    public ResponseEntity<?> uploadfile(@RequestPart("myFile") MultipartFile file, @RequestPart("path") String strPath, HttpSession session) {
        String strFileName = null;
        boolean bReturn = false;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes(); // converting file into bytes
                Path path = Paths.get(DirectoryService.FILE_ROOT_PATH + "/" + strPath + "/" + file.getOriginalFilename()); // giving path
                Files.write(path, bytes);  // this line will create a file from the request
                strFileName = file.getOriginalFilename();// getting file name
                bReturn = directoryService.createDirectoryEntry(strFileName, strPath, true, 1);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("status", "210");
        obj.put("uploaded", String.valueOf(bReturn));
        return new ResponseEntity(obj.toString(), HttpStatus.CREATED);
    }
}


