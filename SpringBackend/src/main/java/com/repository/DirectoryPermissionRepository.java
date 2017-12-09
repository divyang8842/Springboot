package com.repository;

import com.entity.DirectoryPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectoryPermissionRepository extends JpaRepository<DirectoryPermission,Long> {

    public static final int USER = 0;
    public static final int GROUP = 1;
    public static final int LINK = 2;

    List<DirectoryPermission> findAllByPermissiontypeAndPermitid(int permissiontype,int permitid);



}
