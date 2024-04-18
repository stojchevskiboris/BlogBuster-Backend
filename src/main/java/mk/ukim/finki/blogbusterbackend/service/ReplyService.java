package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.Reply;
import mk.ukim.finki.blogbusterbackend.model.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {
    List<ReplyDTO> getAllReplies();
    ReplyDTO getReplyById(Long replyId);
    List<ReplyDTO> getAllRepliesByAuthorId(Long userId);
    List<ReplyDTO> getAllRepliesByCommentId(Long commentId);
    Reply addReply(ReplyDTO replyDTO);
    Reply editReply(ReplyDTO replyDTO, Long replyId) throws Exception;
    Reply deleteReply(Long replyId) throws Exception;
}
