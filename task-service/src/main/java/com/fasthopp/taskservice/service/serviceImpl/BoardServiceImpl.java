package com.fasthopp.taskservice.service.serviceImpl;

import com.fasthopp.taskservice.dto.BoardDto;
import com.fasthopp.taskservice.entity.Board;
import com.fasthopp.taskservice.entity.BoardColumn;
import com.fasthopp.taskservice.repository.BoardColumnRepository;
import com.fasthopp.taskservice.repository.BoardRepository;
import com.fasthopp.taskservice.service.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    private final BoardColumnRepository boardColumnRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, ModelMapper modelMapper, BoardColumnRepository boardColumnRepository) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
        this.boardColumnRepository = boardColumnRepository;
    }

    @Override
    public List<BoardDto> getAllBoardsByCompanyName(String companyName) {
        List<Board> boards = boardRepository.findByCompanyName(companyName);
        return boards.stream()
                .map(board -> modelMapper.map(board, BoardDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BoardDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));
        return modelMapper.map(board, BoardDto.class);
    }

    @Override
    public BoardDto createBoard(BoardDto boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        Board savedBoard = boardRepository.save(board);
        createDefaultColumns(savedBoard);
        return modelMapper.map(savedBoard, BoardDto.class);
    }

    @Override
    public BoardDto updateBoard(Long boardId, BoardDto boardDTO) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            // Map the updated details to the existing board entity
            Board existingBoard = optionalBoard.get();
            existingBoard.setName(boardDTO.getName());
            existingBoard.setDescription(boardDTO.getDescription());

            // Save the updated board
            Board updatedBoard = boardRepository.save(existingBoard);

            // Map the updated board back to DTO for response
            return modelMapper.map(updatedBoard, BoardDto.class);
        } else {
            throw new RuntimeException("Board not found with id: " + boardId);
        }
    }

    @Override
    public void deleteBoard(Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();

            // Delete associated columns first
            List<BoardColumn> columns = board.getBoardColumns();
            for (BoardColumn column : columns) {
                boardColumnRepository.deleteById(column.getId());
            }

            // Then delete the board itself
            boardRepository.deleteById(boardId);
        } else {
            throw new RuntimeException("Board not found with id: " + boardId);
        }
    }

    private void createDefaultColumns(Board board) {
        List<String> defaultColumnNames = Arrays.asList("To-Do", "In-Progress", "Done");

        for (int i = 0; i < defaultColumnNames.size(); i++) {
            BoardColumn column = BoardColumn.builder()
                    .name(defaultColumnNames.get(i))
                    .columnOrder(i + 1) // You can adjust the order as needed
                    .board(board)
                    .build();

            boardColumnRepository.save(column);
        }
    }
}
