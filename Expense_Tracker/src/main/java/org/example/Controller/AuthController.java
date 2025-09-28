package org.example.Controller;

import lombok.Data;
import org.example.Entities.RefreshToken;
import org.example.Entities.UsersEntity;
import org.example.Response.JwtResponseDto;
import org.example.Service.JWTService;
import org.example.Service.RefreshTokenService;
import org.example.Service.UserDetailServiceImpl;
import org.example.model.UsereEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Data
@Component
@RequestMapping
@Controller
public class AuthController {



    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailServiceImpl userDetailService;


    @PostMapping("auth/v1/signup")
    public ResponseEntity Signup(@RequestBody UsereEntityDto user){
        try {
            Boolean isSignUped = userDetailService.signup(user);

            if(Boolean.FALSE.equals(isSignUped)){
                return new ResponseEntity<>("Already Exist" , HttpStatus.BAD_REQUEST);
            }

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
            String jwtToken = jwtService.GenerateToken(user.getUsername());

            return new ResponseEntity<>(JwtResponseDto.builder().acessToken(jwtToken).token(refreshToken.getToken()).build(),
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Exception in User Service" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("auth/v1/ping")
    public ResponseEntity<String> ping(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            String userId = userDetailService.loadUserByUsername(authentication.getName()).getUsername();
            if(Objects.nonNull(userId)){
                return ResponseEntity.ok(userId);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");

    }
}
