package com.fasthopp.taskservice.service.serviceImpl;

import com.fasthopp.taskservice.dto.AssigneeDto;
import com.fasthopp.taskservice.dto.AssigneeResponseDto;
import com.fasthopp.taskservice.dto.BoardDto;
import com.fasthopp.taskservice.dto.CardDto;
import com.fasthopp.taskservice.entity.Assignee;
import com.fasthopp.taskservice.entity.Board;
import com.fasthopp.taskservice.entity.BoardColumn;
import com.fasthopp.taskservice.entity.Card;
import com.fasthopp.taskservice.repository.AssigneeRepository;
import com.fasthopp.taskservice.repository.BoardColumnRepository;
import com.fasthopp.taskservice.repository.BoardRepository;
import com.fasthopp.taskservice.repository.CardRepository;
import com.fasthopp.taskservice.service.CardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final BoardColumnRepository boardColumnRepository;

    private final AssigneeRepository assigneeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, ModelMapper modelMapper, BoardColumnRepository boardColumnRepository, AssigneeRepository assigneeRepository) {
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
        this.boardColumnRepository = boardColumnRepository;
        this.assigneeRepository = assigneeRepository;
    }

    @Override
    public CardDto getCardById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + cardId));
        return modelMapper.map(card, CardDto.class);
    }

    @Override
    public CardDto createCard(CardDto cardDTO) {
        Card card = modelMapper.map(cardDTO, Card.class);

        Long boardId = cardDTO.getBoardId();
        BoardColumn firstColumn = null;

        if (boardId != null) {
            firstColumn = boardColumnRepository.findFirstByBoardIdOrderByColumnOrderAsc(boardId)
                    .orElseThrow(() -> new RuntimeException("No columns found for the board."));
        } else {
            throw new RuntimeException("BoardId is null in the CardDTO.");
        }

        // Set the first column for the new card
        card.setBoardColumn(firstColumn);

        // Save the card without assignees first
        Card savedCard = cardRepository.save(card);

        // Now, call a method to handle assignees
        createAssignees(savedCard, cardDTO.getAssignee());

        return modelMapper.map(savedCard, CardDto.class);
    }

    private void createAssignees(Card card, List<AssigneeDto> assigneeDtos) {
        if (assigneeDtos != null) {
            List<Assignee> assignees = assigneeDtos.stream()
                    .map(assigneeDto -> {
                        Assignee assignee = new Assignee();
                        assignee.setUserEmail(assigneeDto.getUserEmail());
                        assignee.setCard(card);
                        return assignee;
                    })
                    .collect(Collectors.toList());

            List<Assignee> savedAssignees = assigneeRepository.saveAll(assignees);

            // Map Assignee entities to AssigneeResponseDtos
            List<AssigneeResponseDto> assigneeResponseDtos = savedAssignees.stream()
                    .map(assignee -> AssigneeResponseDto.builder()
                            .id(assignee.getId())
                            .userEmail(assignee.getUserEmail())
                            .cardId(assignee.getCard().getId())
                            .build())
                    .toList();

            // Set the savedAssignees in the card
            card.setAssignee(savedAssignees);
        }
    }




    @Override
    public CardDto updateCard(Long cardId, CardDto cardDTO) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            // Map the updated details to the existing card entity
            Card existingCard = optionalCard.get();
            if (cardDTO.getTitle() != null) {
                existingCard.setTitle(cardDTO.getTitle());
            }

            if (cardDTO.getDescription() != null) {
                existingCard.setDescription(cardDTO.getDescription());
            }

            if (cardDTO.getMaxMembers() != null) {
                existingCard.setMaxMembers(cardDTO.getMaxMembers());
            }

            // Save the updated card
            Card updatedCard = cardRepository.save(existingCard);

            // Map the updated card back to DTO for response
            return modelMapper.map(updatedCard, CardDto.class);
        } else {
            throw new RuntimeException("Card not found with id: " + cardId);
        }
    }

    @Override
    public CardDto updateCardBoardColumn(Long cardId, Long newBoardColumnId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();

            // Update the boardColumnId
            BoardColumn newBoardColumn = boardColumnRepository.findById(newBoardColumnId)
                    .orElseThrow(() -> new RuntimeException("BoardColumn not found with id: " + newBoardColumnId));

            card.setBoardColumn(newBoardColumn);

            // Save the updated card
            Card updatedCard = cardRepository.save(card);

            // Map the updated card back to DTO for response
            return modelMapper.map(updatedCard, CardDto.class);
        } else {
            throw new RuntimeException("Card not found with id: " + cardId);
        }
    }

    @Override
    public void deleteCard(Long cardId) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            cardRepository.deleteById(cardId);
        } else {

            throw new EntityNotFoundException("Card with ID " + cardId + " not found");
        }
    }

    @Override
    public List<CardDto> getAllCards(Long boardId) {
        List<Card> cards = cardRepository.findByBoardId(boardId);
        return cards.stream()
                .map(card -> {
                    CardDto cardDto = modelMapper.map(card, CardDto.class);
                    cardDto.setBoardId(card.getBoard().getId());
                    cardDto.setBoardColumnId(card.getBoardColumn().getId());
                    return cardDto;
                })
                .collect(Collectors.toList());
    }
}
