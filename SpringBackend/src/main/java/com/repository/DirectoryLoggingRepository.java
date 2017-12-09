package com.repository;

import com.entity.DirectoryLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  DirectoryLoggingRepository extends JpaRepository<DirectoryLogs,Long>{

    List<DirectoryLogs> findAllByUseridAndDeleteflag(int userid,boolean deleteflag);

}
