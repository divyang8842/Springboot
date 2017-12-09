package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class DirectoryLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    int directoryid;
    int operation;
    String filaname;
    String filepath;
    Date date;
    int userid;
    boolean deleteflag;

    public String getFilaname() {
        return filaname;
    }

    public void setFilaname(String filaname) {
        this.filaname = filaname;
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

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public boolean isDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(boolean deleteflag) {
        this.deleteflag = deleteflag;
    }
}
