package com.fasthopp.taskservice.service;

import com.fasthopp.taskservice.dto.CardDto;

import java.util.List;

public interface CardService {

    CardDto getCardById(Long cardId);

    CardDto createCard(CardDto cardDTO);

    CardDto updateCard(Long cardId, CardDto cardDTO);

    void deleteCard(Long cardId);

    List<CardDto> getAllCards(Long boardId);

    CardDto updateCardBoardColumn(Long cardId, Long newBoardColumnId);
}
