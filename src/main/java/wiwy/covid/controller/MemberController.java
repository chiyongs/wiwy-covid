package wiwy.covid.controller;


import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/member/{memberId}")
    public String member(@PathVariable Long memberId, Model model) {
        Member findMember = memberService.findOne(memberId);
        model.addAttribute("member", findMember);
        return "member/memberInfo";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "member/signIn";
    }

    @GetMapping("/joinForm")
    public String joinForm() { return "member/signUp"; };

    @PostMapping("/addMember")
    public String postMember(Member member, RedirectAttributes redirectAttributes) {
        Long memberId = memberService.join(member);
        redirectAttributes.addAttribute("memberId",memberId);
        return "redirect:/co";

    }


    @GetMapping("/signup")
    public String getMemberForm() {
        return "member/signUp";
    }

    @PostMapping("/signup")
    public String postMemberForm(Member member) {
        memberService.join(member);
        return "redirect:/login";
    }

    @GetMapping("/denied")
    @ResponseBody
    public String deniedView() {
        return "denied";
    }

}
