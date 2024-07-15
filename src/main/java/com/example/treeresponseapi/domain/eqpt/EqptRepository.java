package com.example.treeresponseapi.domain.eqpt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EqptRepository extends JpaRepository<Eqpt, Long> {
    List<Eqpt> findByEqptMenuPrntMenuCdIn(List<String> prntMenuCd);
}
