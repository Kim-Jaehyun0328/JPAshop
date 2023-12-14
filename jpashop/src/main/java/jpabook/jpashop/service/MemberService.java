package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기 전용이다. 아래 메소드에서 변경이 있는 거만 따로 하면(트랜잭셔널 어노테이션 붙히면) 성능 조금 좋아짐
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
//    @Autowired //필드 주입(근데 생성자 주입을 사용하자 ex) Required.... 어노테이션), 테스트를 할 때 못 바꿈.
//    MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicateMember(Member member) {  //멀티쓰레드를 쓰면 더 안전
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

}
