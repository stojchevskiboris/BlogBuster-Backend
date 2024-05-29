package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.dto.ReplyDTO;
import mk.ukim.finki.blogbusterbackend.model.mappers.ReplyMapper;
import mk.ukim.finki.blogbusterbackend.service.ReplyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
@CrossOrigin(origins = "https://blog-buster-fronted.vercel.app/")

public class ReplyRestController {
    private final ReplyService replyService;

    public ReplyRestController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping(value = {"","/"})
    public List<ReplyDTO> listReplies()
    {
        return replyService.getAllReplies();
    }

    @GetMapping("/{commentId}")
    public List<ReplyDTO> getAllRepliesByCommentId(@PathVariable Long commentId)
    {
        return replyService.getAllRepliesByCommentId(commentId);
    }

    @PostMapping("/add")
    public ReplyDTO addReply(@RequestBody ReplyDTO replyDTO)
    {
        ReplyDTO newReplyDto = ReplyMapper.MapToViewModel(replyService.addReply(replyDTO));
        return newReplyDto;
    }

    @PostMapping("/edit/{replyId}")
    public boolean editReply(@RequestBody ReplyDTO replyDTO, @PathVariable Long replyId) throws Exception {
        replyService.editReply(replyDTO,replyId);
        return true;
    }

    @PostMapping("/delete/{replyId}")
    public boolean deleteReply(@PathVariable Long replyId) throws Exception {
        replyService.deleteReply(replyId);
        return true;
    }
}
