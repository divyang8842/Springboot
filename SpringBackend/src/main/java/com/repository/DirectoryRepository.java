package com.repository;


import com.entity.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    List<Directory> findByParentIdAndDeleteflag(int parentid, int deleteflag);

    Directory findByFilepathAndDeleteflag(String filepath, int deleteflag);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="UPDATE directory d SET d.deleteflag = 1 WHERE d.parent_id =:parentId",nativeQuery = true)
    void deleteChildDir(@Param("parentId") String parentId);
}

