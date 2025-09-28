package org.example.Service;

import org.example.Entities.RefreshToken;
import org.example.Entities.UsersEntity;
import org.example.Repository.RefreshTokenRepo;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepo refreshTokenRepo;

    @Autowired
    UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        UsersEntity userinfo = userRepository.findByUsername(username);
        RefreshToken refreshToken = RefreshToken.builder()
                .usersEntity(userinfo)
                .token(UUID.randomUUID().toString())
                .expireData(Instant.now().plusMillis(600000))
                .build();

        return refreshTokenRepo.save(refreshToken);

    }


    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpireData().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh toke is Expired plz login");
        }

        return token;
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepo.findByToken(token);
    }



}
