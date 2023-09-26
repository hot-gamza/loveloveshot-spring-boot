package com.loveloveshot.user.command.application.controller;

import com.loveloveshot.user.command.application.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userCommandService;

//    @GetMapping
//    public ApiResponse getUser() {
//        org.springframework.security.core.userdetails.User principal =
//                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//
//        Member member = userCommandService.getUser(principal.getAuthorities()., principal.getUsername());
//
//        return ApiResponse.success("user", member);
//    }
}
