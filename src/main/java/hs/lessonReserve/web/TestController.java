package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TestController {

    @GetMapping("/principal")
    public String principal(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(principalDetails.getAuthorities());
        System.out.println(principalDetails.getUser().getName());
        ArrayList<GrantedAuthority> authorities = (ArrayList<GrantedAuthority>) principalDetails.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            System.out.println(authority.getAuthority().toString());
        }
        return "principal";
    }

}
