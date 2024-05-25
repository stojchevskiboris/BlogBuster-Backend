package mk.ukim.finki.blogbusterbackend.model.mappers;

import mk.ukim.finki.blogbusterbackend.model.Comment;
import mk.ukim.finki.blogbusterbackend.model.Reply;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.ReplyDTO;

import java.util.ArrayList;
import java.util.List;

public class ReplyMapper {
    public static ReplyDTO MapToViewModel(Reply reply){
        return new ReplyDTO(
                reply.getId(),
                reply.getContent(),
                reply.getAuthor().getUsername(),
                reply.getComment().getId(),
                reply.getAuthor().getFirstname(),
                reply.getAuthor().getLastname(),
                reply.getReply_date(),
                reply.getAuthor().getId().toString()
        );
    }

    public static List<ReplyDTO> MapToListViewModel(List<Reply> replies){
        List<ReplyDTO> repliesVM = new ArrayList<>();
        for (var rep : replies){
            repliesVM.add(MapToViewModel(rep));
        }
        return repliesVM;
    }

}
