package com.fasthopp.taskservice.service;

import com.fasthopp.taskservice.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getCommentsByCardId(Long cardId);
    CommentDto postComment(Long cardId, CommentDto commentDto);
}
