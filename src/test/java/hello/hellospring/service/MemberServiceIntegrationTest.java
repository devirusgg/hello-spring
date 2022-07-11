package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 통합 테스트 애노테이션
@Transactional  // 테스트케이스에 이게 존재하면 테스트시 트랜젝션 먼저실행하고 insert쿼리 후 테스트 종료시 롤백해서 bd를 원래대로 돌려줌 (정확히는 db에 반영이 안됨)
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService; // 테스트에선 constructor injection 보다 편하게 autowired를 애용한다
    @Autowired MemberRepository memberRepository;

    @Test
    void join회원가입() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long id = memberService.join(member);

        // then
        Member findMember = memberService.findOne(id).get();
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    public void join중복회원예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}