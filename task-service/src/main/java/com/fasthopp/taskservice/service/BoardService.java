package com.fasthopp.taskservice.service;

import com.fasthopp.taskservice.dto.BoardDto;

import java.util.List;

public interface BoardService {

    List<BoardDto> getAllBoardsByCompanyName(String companyName);

    BoardDto getBoardById(Long boardId);

    BoardDto createBoard(BoardDto boardDTO);

    BoardDto updateBoard(Long boardId, BoardDto boardDTO);

    void deleteBoard(Long boardId);

}
