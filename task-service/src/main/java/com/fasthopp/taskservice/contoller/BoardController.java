package com.fasthopp.taskservice.contoller;

import com.fasthopp.taskservice.dto.BoardDto;
import com.fasthopp.taskservice.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task/board")
@Tag(name = "Kanban Boards Endpoints")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Operation(
            description = "Get endpoint for get all boards in a workspace.",
            summary = "Get all boards in workspace.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/all-boards")
    public List<BoardDto> getAllBoardsByCompanyName(@RequestParam String companyName) {
        return boardService.getAllBoardsByCompanyName(companyName);
    }

    @Operation(
            description = "Get endpoint for get board by board ID in a workspace.",
            summary = "Get board by board ID in a workspace.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/{boardId}")
    public BoardDto getBoardById(@PathVariable Long boardId) {
        return boardService.getBoardById(boardId);
    }

    @Operation(
            description = "Post endpoint for creating a new board in a workspace.",
            summary = "Create a new board in a workspace.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/create")
    public BoardDto createBoard(@RequestBody BoardDto boardDto) {
        return boardService.createBoard(boardDto);
    }

    @Operation(
            description = "Put endpoint for updating a board in a workspace.",
            summary = "Update a board in a workspace.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PutMapping("update/{boardId}")
    public BoardDto updateBoard(@PathVariable Long boardId, @RequestBody BoardDto boardDTO) {
        return boardService.updateBoard(boardId, boardDTO);
    }

    @Operation(
            description = "Delete endpoint for deleting a board in a workspace.",
            summary = "Delete a board in a workspace.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) {
        try {
            boardService.deleteBoard(boardId);
            return ResponseEntity.ok("Board deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Board not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
