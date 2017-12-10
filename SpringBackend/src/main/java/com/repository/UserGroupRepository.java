package com.repository;

import com.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup,Long> {

    List<UserGroup> findAllByDeleteflag(int deleteflag);

    UserGroup findByGroupnameAndDeleteflag(String name,int deleteflag);

}
