package org.zerock.h9.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.h9.user.dto.MemberDTO;
import org.zerock.h9.user.entity.Member;
import org.zerock.h9.user.repository.MemberRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("===========================================");
        log.info("username"+username);

        Optional<Member> op = memberRepository.findByEmail(username);
        if (op.isPresent()) {
            Member member = op.get();
            UserDetails result = new MemberDTO(
                    username,
                    member.getMpw(),
                    member.getMemberRoleSet().stream().map(memberRole ->
                        new SimpleGrantedAuthority("ROLE_"+memberRole.name())
                    ).collect(Collectors.toList()));
            return result;
        }

        return null;
    }
}
