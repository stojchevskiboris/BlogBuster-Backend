package mk.ukim.finki.blogbusterbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.Comment;
import mk.ukim.finki.blogbusterbackend.model.Reply;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.ReplyDTO;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidCommentIdException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidReplyIdException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.blogbusterbackend.model.mappers.ReplyMapper;
import mk.ukim.finki.blogbusterbackend.repository.CommentRepository;
import mk.ukim.finki.blogbusterbackend.repository.ReplyRepository;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import mk.ukim.finki.blogbusterbackend.service.ReplyService;
import mk.ukim.finki.blogbusterbackend.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<ReplyDTO> getAllReplies() {
        return ReplyMapper.MapToListViewModel(this.replyRepository.findAll());
    }

    @Override
    public ReplyDTO getReplyById(Long replyId) {
        return ReplyMapper.MapToViewModel(this.replyRepository.findById(replyId).
                orElseThrow(InvalidReplyIdException::new));
    }

    @Override
    public List<ReplyDTO> getAllRepliesByAuthorId(Long userId) {
        User user=this.userRepository.findById(userId).orElseThrow(InvalidUserIdException::new);
        return ReplyMapper.MapToListViewModel(user.getReplies());
    }

    @Override
    public List<ReplyDTO> getAllRepliesByCommentId(Long commentId) {
        Comment comment=this.commentRepository.findById(commentId).orElseThrow(InvalidCommentIdException::new);
        return ReplyMapper.MapToListViewModel(comment.getReplies());
    }

    @Transactional
    @Override
    public Reply addReply(ReplyDTO replyDTO) {
        Comment comment=this.commentRepository.findById(replyDTO.getCommentId()).orElseThrow(InvalidCommentIdException::new);
        User user=this.userRepository.findUserByUsername(UserUtils.getLoggedUsername()).orElseThrow(InvalidUserIdException::new);
        Reply reply=new Reply(replyDTO.getContent(),user,comment);
        reply.setReply_date(LocalDateTime.now());
        return this.replyRepository.save(reply);
    }

    @Transactional
    @Override
    public Reply editReply(ReplyDTO replyDTO, Long replyId) throws Exception {
        Reply reply=this.replyRepository.findById(replyId)
                .orElseThrow(InvalidReplyIdException::new);
        User user=this.userRepository.findUserByUsername(UserUtils.getLoggedUsername()).orElseThrow(InvalidUserIdException::new);
        if (!reply.getAuthor().getUsername().equals(user.getUsername())) {
            throw new Exception("Reply not allowed to change");
        }
        reply.setContent(replyDTO.getContent());
        return this.replyRepository.save(reply);
    }

    @Transactional
    @Override
    public Reply deleteReply(Long replyId) throws Exception {
        Reply reply=this.replyRepository.findById(replyId)
                .orElseThrow(InvalidReplyIdException::new);
        User user=userRepository.findUserByUsername(UserUtils.getLoggedUsername()).orElseThrow(InvalidUserIdException::new);
        if (!reply.getAuthor().getReplies().equals(user.getUsername())) {
            throw new Exception("Reply not allowed to change");
        }
        this.replyRepository.delete(reply);
        return reply;
    }
}
