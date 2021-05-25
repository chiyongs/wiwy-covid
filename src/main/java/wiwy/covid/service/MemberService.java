package wiwy.covid.service;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Member;
import wiwy.covid.repository.MemberRepository;


import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        member.setPassword(encoder.encode(member.getPassword()));
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 로그인 절차 검증 , 비밀번호 와 닉네임 일치 확인
    public boolean validateUsernameAndPassword (Member member) {
        List<Member> members = memberRepository.findByName(member.getUserName());
        Member noone = members.get(0);
        if(noone != null) {
            if (noone.getPassword() == member.getPassword()) {
                return true;
            }
        }
        return false;

    }


    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getUserName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Long deleteMember(Long memberId) {
        return memberRepository.delete(memberId);
    }
}
