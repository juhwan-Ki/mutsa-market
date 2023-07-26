package com.mutsa.market.controller;

import com.mutsa.market.dto.CustomUserDetails;
import com.mutsa.market.dto.ResponseDTO;
import com.mutsa.market.dto.UserDTO;
import com.mutsa.market.exception.PasswordException;
import com.mutsa.market.jwt.JwtResponseDTO;
import com.mutsa.market.jwt.JwtTokenUtils;
import com.mutsa.market.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    // 회원가입
    @PostMapping("/join")
    public ResponseDTO join(@RequestBody UserDTO userDTO) {
        // 회원가입 로직
        if(userDTO.getPassword().equals(userDTO.getPasswordCheck())){
            userService.createUser(
                CustomUserDetails.builder()
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build());
        } else {
            throw new PasswordException("비밀번호가 일치하지 않습니다. 다시 입력하세요.");
        }
        ResponseDTO response = new ResponseDTO();
        response.setMessage("회원가입이 정상적으로 완료되었습니다!");
        return response;
    }

    // 로그인(jwt발급)
    @PostMapping("/login")
    public JwtResponseDTO login(@RequestBody UserDTO userDTO){
        UserDetails userDetails
                = userService.loadUserByUsername(userDTO.getUsername());

        if (!passwordEncoder.matches(userDTO.getPassword(), userDetails.getPassword()))
            throw new PasswordException("비밀번호가 일치하지 않습니다. 다시 입력하세요");
        // 토큰 발급
        JwtResponseDTO jwtResponse = new JwtResponseDTO();
        jwtResponse.setToken(jwtTokenUtils.generateToken(userDetails));
        return jwtResponse;
    }
}
