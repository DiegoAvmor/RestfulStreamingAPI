package com.api.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.api.streaming.exception.AccessDeniedException;
import com.api.streaming.exception.NotFoundException;
import com.api.streaming.model.User;
import com.api.streaming.model.dto.TokenDto;
import com.api.streaming.model.request.LoginUserRequest;
import com.api.streaming.repository.UserRepository;
import com.api.streaming.service.impl.UserServiceImpl;
import com.api.streaming.util.JwtTokenUtil;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import com.api.streaming.util.RolesConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
class UserServiceImplTests {
	
	@Autowired
    UserServiceImpl userServiceImpl;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @BeforeEach
    public void setupBefore(){
        LoginUserRequest request = new LoginUserRequest();
        //request.setEmail("Diego@outlook.es");
        //request.setPassword("Diegopass");
        request.setEmail("josefina@outlook.es");
        request.setPassword("josefa123");
        TokenDto token = userServiceImpl.loadUser(request);
        try {
            JwtConsumer jwtConsumer = new JwtConsumerBuilder().setSkipSignatureVerification().build();
            JwtClaims claims = jwtConsumer.processToClaims(token.getToken());
            Optional<User> usuario = userRepository.findById(Integer.valueOf(claims.getJwtId()));

            if(!usuario.isPresent()){
                return;
            }

            User user = usuario.get();
            boolean isValid = jwtUtil.validateToken(token.getToken(), user);
            if (!isValid) {
                return;
            }
    
            //Se añade las autorizaciones/roles
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(RolesConstants.ROLE_AUTHORITY_PREFIX+ user.getRole().getName()));
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            return;
        }
    }
	/*
	@Test
    @DisplayName("Test existing recomendation list")
    public void testRecomendation(){
        Integer a = 4;
        assertEquals(3, userServiceImpl.getRecommendations(a).size());
    }*/

	@Test
    @DisplayName("Test empty recomendation list")
    public void testEmptyRecomendation(){
        Integer a = 1;
        assertThrows(NotFoundException.class, ()->userServiceImpl.getRecommendations(a));
    }
}
