package com.fasthopp.taskservice.service.serviceImpl;

import com.fasthopp.taskservice.dto.CommentDto;
import com.fasthopp.taskservice.entity.Card;
import com.fasthopp.taskservice.entity.Comment;
import com.fasthopp.taskservice.repository.CardRepository;
import com.fasthopp.taskservice.repository.CommentRepository;
import com.fasthopp.taskservice.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CardRepository cardRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CommentDto> getCommentsByCardId(Long cardId) {
        return commentRepository.findByCardId(cardId).stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto postComment(Long cardId, CommentDto commentDto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + cardId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setCard(card);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDto.class);
    }
}
