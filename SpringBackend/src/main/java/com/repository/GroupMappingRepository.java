package com.repository;

import com.entity.GroupMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;


public interface GroupMappingRepository extends JpaRepository<GroupMapping,Long> {

    List<GroupMapping> findAllByGroupidAndDeleteflag(int groupid, int deleteflag);

    void deleteAllByGroupid(int groupid);

}
