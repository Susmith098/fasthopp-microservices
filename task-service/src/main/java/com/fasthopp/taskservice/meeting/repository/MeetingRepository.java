package com.fasthopp.taskservice.meeting.repository;

import com.fasthopp.taskservice.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Optional<Meeting> findByRoomId(String roomId);

    List<Meeting> findByCompanyName(String companyName);

}
