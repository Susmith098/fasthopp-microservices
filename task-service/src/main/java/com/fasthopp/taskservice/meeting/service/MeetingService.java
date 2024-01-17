package com.fasthopp.taskservice.meeting.service;


import com.fasthopp.taskservice.meeting.dto.MeetingDto;

import java.util.List;

public interface MeetingService {

    MeetingDto getMeetingByRoomId(String roomId);

    List<MeetingDto> getMeetingsByCompanyName(String companyName);

    MeetingDto createMeeting(MeetingDto meetingDto);

    boolean deleteMeetingById(Long id);
}
