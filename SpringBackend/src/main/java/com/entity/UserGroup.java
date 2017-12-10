package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String groupname;
    int createdby;
    Date createdon;
    int deletedby;
    int deleteflag;
    String userids;

    public String getUserids() {
        return userids;
    }

    public void setUserids(String userids) {
        this.userids = userids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public int getCreatedby() {
        return createdby;
    }

    public void setCreatedby(int createdby) {
        this.createdby = createdby;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public int getDeletedby() {
        return deletedby;
    }

    public void setDeletedby(int deletedby) {
        this.deletedby = deletedby;
    }

    public int getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(int deleteflag) {
        this.deleteflag = deleteflag;
    }
}
