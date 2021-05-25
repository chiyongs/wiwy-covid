package wiwy.covid.controller;


import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
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
    @ResponseBody
    public String postMemberForm(Member member, RedirectAttributes redirectAttributes) {
        Long memberId = memberService.join(member);
        redirectAttributes.addAttribute("memberId",memberId);

        return "redirect:/member/{memberId}";
    }

//    //로그인
//    @PostMapping("/login")
//    public String loginPage(Member member) {
//        member
//    }

//    //로그아웃
//    @GetMapping("/logout")
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/login";
//    }

}
