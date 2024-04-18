package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.Reply;
import mk.ukim.finki.blogbusterbackend.model.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {
    List<Reply> getAllReplies();
    Reply getReplyById(Long replyId);
    List<Reply> getAllRepliesByAuthorId(Long userId);
    List<Reply> getAllRepliesByCommentId(Long commentId);
    Reply addReply(ReplyDTO replyDTO);
    Reply editReply(ReplyDTO replyDTO) throws Exception;
    Reply deleteReply(Long replyId) throws Exception;
}
