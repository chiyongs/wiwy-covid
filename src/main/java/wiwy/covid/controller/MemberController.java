package wiwy.covid.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wiwy.covid.domain.Member;
import wiwy.covid.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private MemberService memberService;

    // 회원 조회 매핑
    @GetMapping("/member/{memberId}")
    public String member(@PathVariable Long memberId, Model model) {
        Member findMember = memberService.findOne(memberId);
        model.addAttribute("member", findMember);
        return "member";
    }

    @GetMapping("/member/add")
    public String getMemberForm() {
        return "member/addForm";
    }

    @PostMapping("/member/add")
    public String postMemberForm(Member member, RedirectAttributes redirectAttributes) {
        Long memberId = memberService.join(member);
        redirectAttributes.addAttribute("memberId",memberId);

        return "redirect:/member/{memberId}";
    }

}
