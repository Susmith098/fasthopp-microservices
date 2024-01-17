package com.fasthopp.taskservice.service.serviceImpl;

import com.fasthopp.taskservice.dto.AssigneeDto;
import com.fasthopp.taskservice.dto.AssigneeInviteDto;
import com.fasthopp.taskservice.dto.AssigneeResponseDto;
import com.fasthopp.taskservice.entity.Assignee;
import com.fasthopp.taskservice.entity.Card;
import com.fasthopp.taskservice.repository.AssigneeRepository;
import com.fasthopp.taskservice.repository.CardRepository;
import com.fasthopp.taskservice.service.AssigneeService;
import com.fasthopp.taskservice.service.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssigneeServiceImpl implements AssigneeService {

    private final AssigneeRepository assigneeRepository;
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AssigneeServiceImpl(AssigneeRepository assigneeRepository, CardRepository cardRepository, ModelMapper modelMapper) {
        this.assigneeRepository = assigneeRepository;
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
    }

//    @Override
//    public List<AssigneeResponseDto> addAssignee(Long cardId, List<String> userEmails) {
//        Card card = cardRepository.findById(cardId)
//                .orElseThrow(() -> new RuntimeException("Card not found with id: " + cardId));
//
//        List<AssigneeResponseDto> savedAssignees = new ArrayList<>();
//
//        for (String userEmail : userEmails) {
//            Assignee assignee = new Assignee();
//            assignee.setUserEmail(userEmail);
//            assignee.setCard(card);
//
//            Assignee savedAssignee = assigneeRepository.save(assignee);
//            savedAssignees.add(modelMapper.map(savedAssignee, AssigneeResponseDto.class));
//        }
//
//        return savedAssignees;
//    }

    @Override
    public AssigneeResponseDto addAssignee(AssigneeInviteDto assigneeInviteDto) {
        List<AssigneeResponseDto> assigneeResponseDtos = addAssignee(Arrays.asList(assigneeInviteDto));
        return assigneeResponseDtos.isEmpty() ? null : assigneeResponseDtos.get(0);
    }

    private List<AssigneeResponseDto> addAssignee(List<AssigneeInviteDto> assigneeInviteDtos) {
        if (assigneeInviteDtos != null && !assigneeInviteDtos.isEmpty()) {
            List<Assignee> assignees = assigneeInviteDtos.stream()
                    .map(assigneeInviteDto -> {
                        Assignee assignee = new Assignee();

                        // Check if userEmails is provided
                        if (assigneeInviteDto.getUserEmails() != null && !assigneeInviteDto.getUserEmails().isEmpty()) {
                            // Assuming you want to take the first email from the list
                            assignee.setUserEmail(assigneeInviteDto.getUserEmails().get(0));
                        }

                        // Use CardRepository to get the card by ID
                        Card card = cardRepository.findById(assigneeInviteDto.getCardId())
                                .orElseThrow(() -> new RuntimeException("Card not found with id: " + assigneeInviteDto.getCardId()));

                        assignee.setCard(card);
                        return assignee;
                    })
                    .collect(Collectors.toList());

            List<Assignee> savedAssignees = assigneeRepository.saveAll(assignees);

            return savedAssignees.stream()
                    .map(assignee -> AssigneeResponseDto.builder()
                            .id(assignee.getId())
                            .userEmail(assignee.getUserEmail())
                            .cardId(assignee.getCard().getId())
                            .build())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }




    @Override
    public void deleteAssignee(Long assigneeId) {
        Assignee assignee = assigneeRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("Assignee not found with id: " + assigneeId));

        assigneeRepository.delete(assignee);
    }

    @Override
    public List<AssigneeResponseDto> getAllAssigneesForCard(Long cardId) {
        List<Assignee> assignees = assigneeRepository.findByCard_Id(cardId);
        return assignees.stream()
                .map(assignee -> modelMapper.map(assignee, AssigneeResponseDto.class))
                .collect(Collectors.toList());
    }
}
