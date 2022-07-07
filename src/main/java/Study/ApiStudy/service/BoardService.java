package Study.ApiStudy.service;

import Study.ApiStudy.dto.BoardDto;
import Study.ApiStudy.entity.Board;
import Study.ApiStudy.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Study.ApiStudy.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 전체 게시물 조회
    @Transactional(readOnly = true)
    public List<BoardDto> getBoards(){
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        boards.forEach(s -> boardDtos.add(BoardDto.toDto(s)));
        return boardDtos;
    }

    // 개별 게시물 조회
    @Transactional(readOnly = true)
    public BoardDto getBoard(int id){
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("Not Found Board Id");
        });
        BoardDto boardDto = BoardDto.toDto(board);
        return boardDto;
    }

    // 게시물 작성
    @Transactional
    public BoardDto write(BoardDto boardDto, User user){
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setUser(user);
        boardRepository.save(board);
        return BoardDto.toDto(board);
    }

    // 게시물 수정
    @Transactional
    public BoardDto update(int id, BoardDto boardDto){
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("Not Found Board Id");
        });

        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());

        return BoardDto.toDto(board);
    }

    // 게시물 삭제
    @Transactional
    public void delete(int id){
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("Not Found Board Id");
        });

        boardRepository.deleteById(id);
    }
}
