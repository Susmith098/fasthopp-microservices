package com.fasthopp.taskservice.repository;

import com.fasthopp.taskservice.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
    Optional<BoardColumn> findFirstByBoardIdOrderByColumnOrderAsc(Long boardId);

}
