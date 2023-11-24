package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.CommentDto;
import com.example.newsfeedproject.dto.CreateCommentRequest;
import com.example.newsfeedproject.dto.PageDto;
import com.example.newsfeedproject.dto.UpdateCommentRequest;
import com.example.newsfeedproject.entity.Comment;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.exception.NotFoundEntityException;
import com.example.newsfeedproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserStatusService userStatusManager;

    public CommentDto createComment(CreateCommentRequest req) {
        var loginUser = userStatusManager.getLoginUser();

        Comment comment = commentRepository.saveAndFlush(Comment.builder()
                .content(req.content())
                .user(loginUser)
                .post(Post.foreign(req.postId()))
                .build());
        return CommentDto.of(comment);
    }

    public CommentDto updateComment(UpdateCommentRequest req) {
        var loginUser = userStatusManager.getLoginUser();

        Comment comment = commentRepository.findById(req.id())
                .orElseThrow(NotFoundEntityException::new);

        if (!comment.getAuthor().equals(loginUser.getUsername())) {
            throw new AccessDeniedException("댓글 수정 권한이 없습니다.");
        }
        comment.update(req);
        return CommentDto.of(comment);
    }

    public void deleteComment(Long id) {
        var loginUser = userStatusManager.getLoginUser();
        Comment comment = commentRepository.findById(id)
                .orElseThrow(NotFoundEntityException::new);

        if (!comment.getAuthor().equals(loginUser.getUsername())) {
            throw new AccessDeniedException("댓글 삭제 권한이 없습니다.");
        }
        commentRepository.deleteById(id);
    }

    public PageDto getComments(Pageable pageable, Long postId) {
        Page<Comment> result = commentRepository.findByPostId(postId, pageable);
        var data = result.getContent().stream()
                .map(CommentDto::of)
                .toList();

        return new PageDto(data,
                result.getTotalElements(),
                result.getTotalPages(),
                pageable.getPageNumber(),
                data.size()
        );

    }
}
