package com.service;

import com.entity.GroupMapping;
import com.entity.User;
import com.entity.UserGroup;
import com.repository.GroupMappingRepository;
import com.repository.UserGroupRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.StringTokenizer;

@Service
public class UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    GroupMappingRepository groupMappingRepository;

    @Autowired
    UserRepository userRepository;

    public void addGroup(UserGroup userGroup){

        UserGroup oldGroup = userGroupRepository.findByGroupnameAndDeleteflag(userGroup.getGroupname(),0);
        String userids = userGroup.getUserids();
        if(oldGroup!=null){
            userGroup.setId(oldGroup.getId());
            groupMappingRepository.deleteAllByGroupid(oldGroup.getId());
        }

        userGroup = userGroupRepository.save(userGroup);

        StringTokenizer sTk = new StringTokenizer(userids);
        while(sTk.hasMoreElements()){
            GroupMapping groupMapping = new GroupMapping();
            groupMapping.setGroupid(userGroup.getId());
            User user = userRepository.findByEmail(sTk.nextToken());
            groupMapping.setUserid(user.getId());
            groupMappingRepository.save(groupMapping);
        }
    }

    public List<UserGroup> getUserGroups(){
        return userGroupRepository.findAllByDeleteflag(0);
    }


}
