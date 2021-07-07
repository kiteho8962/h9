package org.zerock.h9.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/all")
    public String[] doALl() {
        return new String[] {"AAA", "AAA", "AAA"};
    }
    @GetMapping("/member")
    public String[] doMember() {
        return new String[] {"BBB", "BBB", "BBB"};
    }
    @GetMapping("/admin")
    public String[] doAdmin() {
        return new String[] {"CCC", "CCC", "CCC"};
    }
    @GetMapping("/Login")
    public String[] doLogin() {
        return new String[] {"DDD", "DDD", "DDD"};
    }
}
