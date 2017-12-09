package com.repository;

import com.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Long>{

    Profile findByUserid(int userid);

}
