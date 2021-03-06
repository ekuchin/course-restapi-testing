package ru.ekuchin.courserestapitesting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ekuchin.courserestapitesting.idp.IdentityProvider;
import ru.ekuchin.courserestapitesting.jwt.JWTRefreshRequest;
import ru.ekuchin.courserestapitesting.jwt.JWTAuthRequest;
import ru.ekuchin.courserestapitesting.jwt.JWTToken;
import ru.ekuchin.courserestapitesting.jwt.JWTTokenPairResponse;

@RestController
public class IdentityProviderController {

    @Autowired
    private IdentityProvider identityProvider;

    @Autowired
    private JWTToken jwtToken;

    @PostMapping(value = "/auth")
    public ResponseEntity<?> authUser (@RequestBody JWTAuthRequest authRequest) {

            if (identityProvider.validateUser(authRequest.getUsername(),authRequest.getPassword())){
                return generateTokenPair(authRequest.getUsername());
            }
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User or password is incorrect");
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refreshTokenPair(@RequestBody JWTRefreshRequest oldRefreshToken){

        jwtToken.setToken(oldRefreshToken.getToken());
        if (jwtToken.isValid()){
            String username = jwtToken.getUserName();
            return generateTokenPair(username);
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");

    }

    private ResponseEntity<?> generateTokenPair(String username){
        jwtToken.generate(true, username);
        String accessToken = jwtToken.getToken();
        jwtToken.generate(false,username);
        String refreshToken=jwtToken.getToken();
        return ResponseEntity.ok(new JWTTokenPairResponse(accessToken,refreshToken));
    }

}