package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.dto.ReplyDTO;
import mk.ukim.finki.blogbusterbackend.service.ReplyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
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
    public String addReply(@RequestBody ReplyDTO replyDTO)
    {
        replyService.addReply(replyDTO);
        return "redirect:/api/replies";
    }

    @PostMapping("/edit/{replyId}")
    public String editReply(@RequestBody ReplyDTO replyDTO, @PathVariable Long replyId) throws Exception {
        replyService.editReply(replyDTO,replyId);
        return "redirect:/api/replies";
    }

    @PostMapping("/delete/{replyId}")
    public String deleteReply(@PathVariable Long replyId) throws Exception {
        replyService.deleteReply(replyId);
        return "redirect:/api/replies";
    }
}
