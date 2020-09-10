package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    //엔티티 매니저를 통한 모든 데이터 변경은 트랜잭션 안에서!
    @Test
    @Transactional // test에 이 어노테이션이 있으면 테스트 끝나고 롤백해버림 test아닌곳에선 정상적으로 동
    @Rollback(false) // 롤백 취소
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");
        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); // 같은 영속성 컨텍스트에서 식별자가 같으면 같은 엔티티로 인식!
        // 영속성 컨텍스트에 1차 캐시에서 가져오니까 똑같을 수밖에 
    }
}