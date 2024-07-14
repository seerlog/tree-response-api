package com.example.treeresponseapi.domain.epqtMenu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EqptMenuRepository extends JpaRepository<EqptMenu, Long>{
}
