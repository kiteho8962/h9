package org.zerock.h9.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.h9.user.entity.Member;
import org.zerock.h9.user.entity.MemberRole;
import org.zerock.h9.user.repository.MemberRepository;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreate() {
        IntStream.rangeClosed(1, 100).forEach(value -> {
            Member member = Member.builder()
                    .email("kiteho"+value+"@google.com")
                    .mpw(passwordEncoder.encode("1111"))
                    .mname("서연호"+value)
                    .build();
            member.addMemberRole(MemberRole.USER);
            if (value > 50) {
                member.addMemberRole(MemberRole.MEMBER);
            }
            if (value > 80) {
                member.addMemberRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }

    @Test
    public void testRead() {
        String email = "kiteho30@google.com";
        Optional<Member> result = memberRepository.findByEmail(email);
        result.ifPresent(member -> {
            log.info(member);
        });
    }

}
