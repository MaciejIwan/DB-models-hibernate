package com.github.maciejiwan.investmens_tracking.repositories;

import com.github.maciejiwan.investmens_tracking.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);
}
