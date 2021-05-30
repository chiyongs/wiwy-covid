package wiwy.covid.controller;


import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wiwy.covid.domain.Member;
import wiwy.covid.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 조회 매핑
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/member/{memberId}")
    public String member(@PathVariable Long memberId, Model model) {
        Member findMember = memberService.findOne(memberId);
        model.addAttribute("member", findMember);
        return "member";
    }

    @GetMapping("/login")
    public String loginView() {
        return "member/signIn";
    }

    @GetMapping("/addMember")
    public String getMemberForm() {
        return "member/signUp";
    }

    @PostMapping("/addMember")
    @ResponseBody
    public String postMemberForm(Member member, RedirectAttributes redirectAttributes) {
        Long memberId = memberService.join(member);
        redirectAttributes.addAttribute("memberId",memberId);

        return "redirect:/member/{memberId}";
    }

    @GetMapping("/denied")
    @ResponseBody
    public String deniedView() {
        return "denied";
    }

}
