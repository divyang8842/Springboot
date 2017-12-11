package com.service;

import com.entity.Directory;
import com.entity.DirectoryLogs;
import com.repository.DirectoryRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class DirectoryService {

    public static String FILE_ROOT_PATH ="./";


    @Autowired
    private DirectoryRepository directoryRepository;

    @Autowired
    private DirectoryLoggingService directoryLoggingService;


    Iterable<Directory> getAllDirectory() {
        return directoryRepository.findAll();
    }

    public List<Directory> getByParentId(int parentId) {
        return directoryRepository.findByParentIdAndDeleteflag(parentId, 0);
    }

    public Directory getDirectoryFromPath(String filepath) {
        return directoryRepository.findByFilepathAndDeleteflag(filepath, 0);
    }

    public void deleteDirectory(Directory directory){
        final Directory save = directoryRepository.save(directory);
        directoryRepository.deleteChildDir(String.valueOf(save.getId()));
        File file = new File(FILE_ROOT_PATH+"/"+directory.getFilepath());


        if(file.exists()){
            if(file.isDirectory()){
                try {
                    FileUtils.deleteDirectory(file);
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }else{
                 file.delete();
            }
            DirectoryLogs directoryLogs = new DirectoryLogs();
            directoryLogs.setFilaname(save.getFilename());
            directoryLogs.setFilepath(save.getFilepath());
            directoryLogs.setDate(new Date());
            directoryLogs.setDirectoryid(save.getId());
            directoryLogs.setOperation(DirectoryLoggingService.DELETE);
            directoryLogs.setUserid(save.getCreatedBy());
            directoryLoggingService.addLog(directoryLogs);

        }
    }

    public void addDirectory(Directory directory) {

        Directory oldDirectory = getDirectoryFromPath(directory.getFilepath()+"/"+directory.getFilename());
        if(oldDirectory==null ||  oldDirectory.getDeleteflag()==1){
            final Directory save = directoryRepository.save(directory);
            DirectoryLogs directoryLogs = new DirectoryLogs();
            directoryLogs.setFilaname(save.getFilename());
            directoryLogs.setFilepath(save.getFilepath());
            directoryLogs.setDate(new Date());
            directoryLogs.setDirectoryid(save.getId());
            directoryLogs.setOperation(DirectoryLoggingService.ADD);
            directoryLogs.setUserid(save.getCreatedBy());
            directoryLoggingService.addLog(directoryLogs);
        }
    }




    public boolean createDirectory(String strName, String path, int nTrUserId) {
        File file = null;
        if ("".equalsIgnoreCase(path)) {
            file = new File(FILE_ROOT_PATH + "/" + strName);
        } else {
            file = new File(FILE_ROOT_PATH + "/" + path + "/" + strName);
        }

        if (!file.exists()) {
            if (file.mkdirs()) {
                return createDirectoryEntry(strName, path, false, nTrUserId);
            }
        }
        return false;
    }

    public boolean createDirectoryEntry(String strFileName, String strPath, boolean isFile, int nTrUserId) {
        Directory directory = new Directory();
        directory.setCreatedBy(nTrUserId);
        directory.setCreatedOn(new Date());
        directory.setDeleteflag(0);
        directory.setFile(isFile);
        directory.setFilename(strFileName);
        if ("".equalsIgnoreCase(strPath)) {
            directory.setFilepath(strFileName);
            //Directory parent = directoryService.getDirectoryFromPath(strPath);
            directory.setParentId(-1);
        } else {
            directory.setFilepath(strPath + "/" + strFileName);
            Directory parent = getDirectoryFromPath(strPath);
            directory.setParentId(parent.getId());
        }
        addDirectory(directory);
        return true;
    }

    public InputStream getIStreamFromFileName(String strFileName){
        InputStream inputStream = null;
        try{
            File file = new File(FILE_ROOT_PATH+"/"+strFileName);
            if(file.exists() && file.isFile()){
                inputStream = new FileInputStream(file);
            }
        }catch(Exception ex){

        }
        return inputStream;
    }

    public long getContentLength(String strFileName){
        InputStream inputStream = null;
        try{
            File file = new File(FILE_ROOT_PATH+"/"+strFileName);
            if(file.exists() && file.isFile()){
                return file.length();
            }
        }catch(Exception ex){

        }
        return 0;
    }
}
