package com.fasthopp.taskservice.repository;

import com.fasthopp.taskservice.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByCompanyName(String companyName);
}
