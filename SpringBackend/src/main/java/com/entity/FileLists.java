package com.entity;

import java.util.List;

public class FileLists {

    List<Directory> filelist;
    String status;
    List<DirectoryPermission> sharedlist;
    List<StaredDirectory> staredlist;
    List<DirectoryLogs> directoryLogs;

    public List<DirectoryLogs> getDirectoryLogs() {
        return directoryLogs;
    }

    public void setDirectoryLogs(List<DirectoryLogs> directoryLogs) {
        this.directoryLogs = directoryLogs;
    }

    public List<Directory> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<Directory> filelist) {
        this.filelist = filelist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DirectoryPermission> getSharedlist() {
        return sharedlist;
    }

    public void setSharedlist(List<DirectoryPermission> sharedlist) {
        this.sharedlist = sharedlist;
    }

    public List<StaredDirectory> getStaredlist() {
        return staredlist;
    }

    public void setStaredlist(List<StaredDirectory> staredlist) {
        this.staredlist = staredlist;
    }
}
