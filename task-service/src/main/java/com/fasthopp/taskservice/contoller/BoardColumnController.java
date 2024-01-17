package com.fasthopp.taskservice.contoller;

import com.fasthopp.taskservice.dto.BoardColumnDto;
import com.fasthopp.taskservice.service.BoardColumnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task/column")
@Tag(name = "Kanban Board's Column Endpoints")
public class BoardColumnController {

    private final BoardColumnService columnService;

    public BoardColumnController(BoardColumnService columnService) {
        this.columnService = columnService;
    }

    @Operation(
            description = "Get endpoint for get all columns of a board by board ID.",
            summary = "Get all columns of a board by board ID.",
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
    public List<BoardColumnDto> getBoardColumnById(@PathVariable Long boardId) {
        return columnService.getColumnsByBoardId(boardId);
    }

    @Operation(
            description = "Patch endpoint for update a column by column ID.",
            summary = "Update a column by column ID.",
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
    @PatchMapping("/update/{columnId}")
    public BoardColumnDto updateBoardColumn(@PathVariable Long columnId, @RequestBody BoardColumnDto updatedColumnDto) {
        return columnService.updateBoardColumn(columnId, updatedColumnDto);
    }

    @Operation(
            description = "Post endpoint for creating a new column for a existing board.",
            summary = "Create a new column for a existing board.",
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
    public BoardColumnDto createBoardColumn(@RequestBody BoardColumnDto columnDTO) {
        return columnService.createBoardColumn(columnDTO);
    }

    @Operation(
            description = "Delete endpoint for delete a column for a existing board.",
            summary = "Delete a column for a existing board.",
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
    @DeleteMapping("/{columnId}")
    public void deleteBoardColumn(@PathVariable Long columnId) {
        columnService.deleteBoardColumn(columnId);
    }
}
