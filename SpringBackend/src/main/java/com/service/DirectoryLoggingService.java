package com.service;

import com.entity.DirectoryLogs;
import com.repository.DirectoryLoggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoryLoggingService {

    public static final int ADD = 1;
    public static final int DELETE = 2;
    public static final int SHARED = 3;

    @Autowired
    DirectoryLoggingRepository directoryLoggingRepository;

    void addLog(DirectoryLogs directoryLogs){
        directoryLoggingRepository.save(directoryLogs);
    }

    public List<DirectoryLogs> getAllUserLogs(int userid){
        return directoryLoggingRepository.findAllByUseridAndDeleteflag(userid,false);
    }

}
