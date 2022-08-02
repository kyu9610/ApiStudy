package Study.ApiStudy.service;

import Study.ApiStudy.dto.CommentDto;
import Study.ApiStudy.entity.Board;
import Study.ApiStudy.entity.Comment;
import Study.ApiStudy.entity.User;
import Study.ApiStudy.repository.BoardRepository;
import Study.ApiStudy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 작성하기
    @Transactional
    public CommentDto writeComment(int boardId, CommentDto commentDto, User user){
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());

        // 게시판 번호로 게시글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(()->{
            return new IllegalArgumentException("게시판을 찾을 수 없습니다,");
        });

        comment.setBoard(board);
        comment.setUser(user);
        commentRepository.save(comment);

        return commentDto.toDto(comment);
    }

    // 댓글 불러오기(1개)
    @Transactional(readOnly = true)
    public CommentDto getComment(int boardId){
        Comment comment = commentRepository.findById(boardId).orElseThrow(()->{
            return new IllegalArgumentException("댓글이 존재하지 않습니다.");
        });

        return CommentDto.toDto(comment);
    }

    // 글에 해당하는 전체 댓글 불러오기
    @Transactional(readOnly = true)
    public List<CommentDto> getComments(int boardId){
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        List<CommentDto> commentDtos = new ArrayList<>();

        comments.forEach(s -> commentDtos.add(CommentDto.toDto(s)));
        return commentDtos;
    }

    // 댓글 삭제하기
    @Transactional
    public String deleteComment(int commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->{
            return new IllegalArgumentException("댓글 id를 찾을 수 없습니다.");
        });
        commentRepository.deleteById(commentId);
        return "삭제 완료";
    }
}
