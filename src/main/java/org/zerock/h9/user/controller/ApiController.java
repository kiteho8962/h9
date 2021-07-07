package org.zerock.h9.user.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.h9.user.dto.MemberDTO;

@RestController
@RequestMapping("/api/board")
@Log4j2
public class ApiController {

    @GetMapping("/list")
    public String[] getList(@AuthenticationPrincipal MemberDTO memberDTO) {
        log.info("===============================");
        log.info(memberDTO.getUsername());
        log.info(memberDTO.getPassword());
        log.info("===============================");
        return new String[] {"List", "List", "List"};
    }

    @PreAuthorize("principal.username == #writer")
    @GetMapping("/delete")
    public String[] delete(Long bno, String writer) {

        log.info("delete: " + bno);
        log.info("writer: " + writer);

        return new String[]{"DEL","DEL","DEL"};
    }

}
