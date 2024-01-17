package com.fasthopp.taskservice.service.serviceImpl;

import com.fasthopp.taskservice.dto.BoardColumnDto;
import com.fasthopp.taskservice.entity.Board;
import com.fasthopp.taskservice.entity.BoardColumn;
import com.fasthopp.taskservice.repository.BoardColumnRepository;
import com.fasthopp.taskservice.repository.BoardRepository;
import com.fasthopp.taskservice.service.BoardColumnService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardColumnServiceImpl implements BoardColumnService {

    private final BoardColumnRepository boardColumnRepository;

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BoardColumnServiceImpl(BoardColumnRepository boardColumnRepository, ModelMapper modelMapper, BoardRepository boardRepository) {
        this.boardColumnRepository = boardColumnRepository;
        this.modelMapper = modelMapper;
        this.boardRepository = boardRepository;
    }


    public BoardColumnDto updateBoardColumn(Long columnId, BoardColumnDto updatedColumnDto) {
        // Check if the BoardColumn exists
        BoardColumn existingColumn = boardColumnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("BoardColumn not found with id: " + columnId));

        // Update the properties with non-null values from the DTO
        if (updatedColumnDto.getName() != null) {
            existingColumn.setName(updatedColumnDto.getName());
        }

        if (updatedColumnDto.getColumnOrder() != null) {
            existingColumn.setColumnOrder(updatedColumnDto.getColumnOrder());
        }

        // Save the updated BoardColumn
        BoardColumn updatedColumn = boardColumnRepository.save(existingColumn);

        // Map the updated BoardColumn back to DTO for response
        return modelMapper.map(updatedColumn, BoardColumnDto.class);
    }

    @Override
    public List<BoardColumnDto> getColumnsByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));

        List<BoardColumn> columns = board.getBoardColumns();// Assuming columns is the relationship property in Board

        return columns.stream()
                .map(column -> modelMapper.map(column, BoardColumnDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BoardColumnDto createBoardColumn(BoardColumnDto columnDto) {
        // Ensure the board is available
        if (columnDto.getBoardId() != null) {
            // Find the Board by ID
            Board board = boardRepository.findById(columnDto.getBoardId())
                    .orElseThrow(() -> new RuntimeException("Board not found with id: " + columnDto.getBoardId()));

            BoardColumn boardColumn = modelMapper.map(columnDto, BoardColumn.class);
            boardColumn.setBoard(board);

            BoardColumn savedColumn = boardColumnRepository.save(boardColumn);

            return modelMapper.map(savedColumn, BoardColumnDto.class);
        } else {
            throw new RuntimeException("Board ID is required to create a BoardColumn");
        }
    }

    @Override
    public void deleteBoardColumn(Long columnId) {
        boardColumnRepository.deleteById(columnId);
    }
}
