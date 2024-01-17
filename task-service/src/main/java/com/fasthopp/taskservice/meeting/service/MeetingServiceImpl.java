package com.fasthopp.taskservice.meeting.service;

import com.fasthopp.taskservice.meeting.dto.MeetingDto;
import com.fasthopp.taskservice.meeting.entity.Meeting;
import com.fasthopp.taskservice.meeting.repository.MeetingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MeetingServiceImpl(MeetingRepository meetingRepository, ModelMapper modelMapper) {
        this.meetingRepository = meetingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MeetingDto> getMeetingsByCompanyName(String companyName) {
        List<Meeting> meetings = meetingRepository.findByCompanyName(companyName);
        return meetings.stream()
                .map(meeting -> modelMapper.map(meeting, MeetingDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MeetingDto getMeetingByRoomId(String roomId) {
        Optional<Meeting> meetingOptional = meetingRepository.findByRoomId(roomId);
        return meetingOptional.map(meeting -> modelMapper.map(meeting, MeetingDto.class)).orElse(null);
    }

    @Override
    public MeetingDto createMeeting(MeetingDto meetingDto) {
//        LocalDateTime startingTime = LocalDateTime.now();
//        LocalDateTime expirationTime = startingTime.plusMinutes(2);

        Meeting meeting = modelMapper.map(meetingDto, Meeting.class);

//        meeting.setStartingTime(startingTime);
//        meeting.setExpirationTime(expirationTime);

        Meeting savedMeeting = meetingRepository.save(meeting);
        return modelMapper.map(savedMeeting, MeetingDto.class);
    }

    @Override
    public boolean deleteMeetingById(Long id) {
        Optional<Meeting> meetingOptional = meetingRepository.findById(id);

        if (meetingOptional.isPresent()) {
            meetingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
