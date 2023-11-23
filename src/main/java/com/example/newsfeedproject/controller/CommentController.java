package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.CreateCommentRequest;
import com.example.newsfeedproject.dto.MessageDto;
import com.example.newsfeedproject.dto.UpdateCommentRequest;
import com.example.newsfeedproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(CreateCommentRequest request){
        var response = commentService.createComment(request);
        return ResponseEntity.ok(response);
    }
    @PatchMapping
    public ResponseEntity<?> updateComment(UpdateCommentRequest request){
        var response = commentService.updateComment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getComments(@PathVariable Long postId, Pageable pageable){
        var response = commentService.getComments(pageable, postId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok(new MessageDto("댓글 삭제 성공"));
    }
}
