package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.CreateCommentRequest;
import com.example.newsfeedproject.dto.UpdateCommentRequest;
import com.example.newsfeedproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    public ResponseEntity<?> createComment(CreateCommentRequest request){
        var response = commentService.createComment(request);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updateComment(UpdateCommentRequest request){
        var response = commentService.updateComment(request);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteComment(){
        return null;
    }
}
