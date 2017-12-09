package com.repository;

import com.entity.StaredDirectory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaredRepository extends JpaRepository<StaredDirectory,Long> {

    List<StaredDirectory> findAllByUseridAndDeleteflag(int userid,int deleteflag);

    StaredDirectory findByUseridAndFilepathAndDeleteflag(int userid,String filepath,int deleteflag);
}
