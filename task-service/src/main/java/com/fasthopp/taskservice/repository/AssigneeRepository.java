package com.fasthopp.taskservice.repository;

import com.fasthopp.taskservice.entity.Assignee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssigneeRepository extends JpaRepository<Assignee, Long> {
    List<Assignee> findByCard_Id(Long cardId);
}
