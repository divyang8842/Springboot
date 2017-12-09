package com.service;

import com.entity.StaredDirectory;
import com.repository.StaredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaredService {

    @Autowired
    StaredRepository staredRepository;

    public void starDirectory(StaredDirectory staredDirectory){
        staredRepository.save(staredDirectory);
    }

    public List<StaredDirectory> getAllStaredDirectoryForUser(int userid){
        return staredRepository.findAllByUseridAndDeleteflag(userid,0);
    }

    public StaredDirectory getStaredDirectory(int userid,String filepath){
        return staredRepository.findByUseridAndFilepathAndDeleteflag(userid,filepath,0);
    }
}
