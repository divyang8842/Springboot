package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DirectoryPermission
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    int directoryid;
    int permissiontype;
    int permitid;
    int deleteflag;
    String filename;
    String filepath;
    boolean isFile;

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDirectoryid() {
        return directoryid;
    }

    public void setDirectoryid(int directoryid) {
        this.directoryid = directoryid;
    }

    public int getPermissiontype() {
        return permissiontype;
    }

    public void setPermissiontype(int permissiontype) {
        this.permissiontype = permissiontype;
    }

    public int getPermitid() {
        return permitid;
    }

    public void setPermitid(int permitid) {
        this.permitid = permitid;
    }

    public int getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(int deleteflag) {
        this.deleteflag = deleteflag;
    }
}