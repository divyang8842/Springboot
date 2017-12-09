package com.service;

import com.entity.DirectoryPermission;
import com.repository.DirectoryPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoryPermissionService {

    @Autowired
    DirectoryPermissionRepository directoryPermissionRepository;

    public void addPremission(DirectoryPermission directoryPermission){
        directoryPermissionRepository.save(directoryPermission);
    }
    public List<DirectoryPermission> getPermissionForUser(int userid){
        return directoryPermissionRepository.findAllByPermissiontypeAndPermitid(DirectoryPermissionRepository.USER,userid);
    }
    public List<DirectoryPermission> getPermissionForGroup(int groupid){
        return directoryPermissionRepository.findAllByPermissiontypeAndPermitid(DirectoryPermissionRepository.GROUP,groupid);
    }
}
