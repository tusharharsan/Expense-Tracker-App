package org.example.Controller;

import lombok.Data;
import org.example.Entities.RefreshToken;
import org.example.Entities.UsersEntity;
import org.example.Request.AuthRequestDto;
import org.example.Request.RefreshTokenDto;
import org.example.Response.JwtResponseDto;
import org.example.Service.JWTService;
import org.example.Service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@Data
@RequestMapping
public class TokenColtroller {



    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("auth/v1/login")
    public ResponseEntity Authenticateandgettoken(@RequestBody AuthRequestDto req){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername() , req.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(req.getUsername());
            return new ResponseEntity<>(JwtResponseDto.builder().acessToken(jwtService.GenerateToken(req.getUsername())).token(refreshToken.getToken()).build() , HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Exception in User Service" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("auth/v1/refreshToken")
    public JwtResponseDto refreshtoken(@RequestBody RefreshTokenDto refreshTokenDto){
        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByToken(refreshTokenDto.getToken());

        if(optionalRefreshToken.isPresent()){
            RefreshToken refreshToken = refreshTokenService.verifyExpiration(optionalRefreshToken.get());

            UsersEntity user = refreshToken.getUsersEntity();

            String accessToken = jwtService.GenerateToken(user.getUsername());

            return JwtResponseDto.builder().acessToken(accessToken).token(refreshToken.getToken()).build();


        }else{
            throw new RuntimeException("Refresh Token is not in DB");
        }
    }
}
