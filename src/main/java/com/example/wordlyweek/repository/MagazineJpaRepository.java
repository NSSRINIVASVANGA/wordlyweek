package com.example.wordlyweek.repository;

import com.example.wordlyweek.model.*;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MagazineJpaRepository extends JpaRepository<Magazine, Integer> {

}