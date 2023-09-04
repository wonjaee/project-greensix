package com.coding.cho.faq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommentController {

	@Autowired
	private FaqCommentService faqCommentService;
	
	//댓글작성
	@ResponseBody
	@PostMapping("/faq/board/{bno}/comment")
	public void save(Authentication authentication,  @PathVariable("bno") long no, String commentContent) {
		
		faqCommentService.commentSave(authentication.getName(), no, commentContent);
	}
	//댓글 보이기
	@ResponseBody
	@GetMapping("/faq/board/{bno}/comment")
	public ModelAndView save( @PathVariable("bno") long bno) {
		ModelAndView mv=new ModelAndView("user/cscenter/faq/comment");
		List<FaqCommentEntity> result=faqCommentService.findAllByBoardNo(bno);
		mv.addObject("list", result);
		return mv;
	}
	//댓글 수정
	@PostMapping("/faq/board/{bno}/comment/{no}")
	public String editComment(@PathVariable("no") long no,
								@PathVariable("bno") long bno,FaqCommentUpdateDTO dto) {
		faqCommentService.updateProcess(no,dto);
		
		return "redirect:/faq/board/detail/{bno}";
	}	
	//댓글 삭제
	@GetMapping("/faq/board/{bno}/comment/{no}")
	public String commentDelete(Authentication authentication, 
					@PathVariable("no") long no,@PathVariable("bno") long bno) {
		boolean isOwner = faqCommentService.isOwner(authentication.getName(), no);
		if(isOwner) { 
			faqCommentService.commentDelete(no);
			  return "redirect:/faq/board/detail/{bno}";
		  } else {
			  return "redirect:/faq/board/detail/{bno}";
		  }
	}
	
	
}
