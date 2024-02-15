package org.scaler.userservicemwfeve.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.scaler.userservicemwfeve.models.Token;
import org.scaler.userservicemwfeve.models.User;
import org.scaler.userservicemwfeve.repositories.TokenRepository;
import org.scaler.userservicemwfeve.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public User signUp(String name, String email, String password) {
        User u = new User();
        u.setEmail(email);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));
        u.setName(name);
        User user = userRepository.save(u);
        return user;
    }

    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            //User not exist exception
            return null;
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            // password not match exception
            return null;
        }

        LocalDate today = LocalDate.now();

        // Add 30 days to today
        LocalDate thirtyDaysLater = today.plusDays(30);

        // Convert LocalDate to Date using `toDate()`
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(expiryDate);

        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        //Change above token to JWT token

        Token savedToken = tokenRepository.save(token);
        return savedToken;
    }


    public void logout(String token) {
        Optional<Token> token1 = tokenRepository.findByValueAndDeleted(token, false);
        if(token1.isEmpty()) {
            //throw TokenNotExistOrAlreadyExpiredException()
            return;
        }
        Token tkn = token1.get();
        tkn.setDeleted(true);
        tokenRepository.save(tkn);
        return;
    }

    public User validateToken(String token) {
        Optional<Token> token1 = tokenRepository.findByValueAndDeletedEqualsAndExpiryAtGreaterThan(token, false, new Date());
        if(token1.isEmpty()) {
            //throw TokenNotExistOrAlreadyExpiredException()
            return null;
        }
        Token tkn = token1.get();

        //Instead of validating via Db, as the token is JWT token validate using JWT

        return tkn.getUser();
    }
}
