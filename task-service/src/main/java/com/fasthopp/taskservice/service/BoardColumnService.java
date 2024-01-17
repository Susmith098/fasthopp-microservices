package com.fasthopp.taskservice.service;

import com.fasthopp.taskservice.dto.BoardColumnDto;

import java.util.List;

public interface BoardColumnService {


    List<BoardColumnDto> getColumnsByBoardId(Long boardId);

    BoardColumnDto createBoardColumn(BoardColumnDto columnDto);

    BoardColumnDto updateBoardColumn(Long columnId, BoardColumnDto updatedColumnDto);

    void deleteBoardColumn(Long columnId);
}
