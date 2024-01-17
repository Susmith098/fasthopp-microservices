package com.fasthopp.taskservice.contoller;

import com.fasthopp.taskservice.dto.CommentDto;
import com.fasthopp.taskservice.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task/comments")
@Tag(name = "Comment Endpoints")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            description = "Get endpoint for get all comments in a card by card ID.",
            summary = "Get all comments in a card by card ID.",
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
    @GetMapping("/card/{cardId}")
    public List<CommentDto> getCommentsByCardId(@PathVariable Long cardId) {
        return commentService.getCommentsByCardId(cardId);
    }

    @Operation(
            description = "Post endpoint for add/create new comment in a existing card.",
            summary = "Add/create new comment in a existing card.",
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
    @PostMapping("/card/{cardId}")
    public CommentDto postComment(@PathVariable Long cardId, @RequestBody CommentDto commentDto) {
        return commentService.postComment(cardId, commentDto);
    }
}
