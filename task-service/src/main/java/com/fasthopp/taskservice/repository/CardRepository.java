package com.fasthopp.taskservice.repository;

import com.fasthopp.taskservice.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

//    @Query("SELECT c FROM Card c WHERE (:boardId IS NULL OR c.board.id = :boardId) AND (:columnId IS NULL OR c.boardColumn.id = :columnId) AND (:memberEmail IS NULL OR c.memberEmails LIKE :memberEmail)")
//    List<Card> filterCards(Long boardId, Long columnId, String memberEmail);
//
//    @Query("SELECT c FROM Card c WHERE c.title LIKE %:keyword% OR c.description LIKE %:keyword%")
//    List<Card> searchCards(String keyword);

    List<Card> findByBoardId(Long boardId);
}
