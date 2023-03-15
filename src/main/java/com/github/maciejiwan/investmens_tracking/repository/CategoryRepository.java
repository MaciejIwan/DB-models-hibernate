package com.github.maciejiwan.investmens_tracking.repository;

import com.github.maciejiwan.investmens_tracking.entity.Category;
import com.github.maciejiwan.investmens_tracking.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
