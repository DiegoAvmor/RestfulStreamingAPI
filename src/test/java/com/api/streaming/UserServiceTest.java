package com.api.streaming;

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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserServiceImpl userServiceImpl;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @BeforeEach
    public void setupBefore(){
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("josefina@outlook.es");
        request.setPassword("josefa123");
        //request.setEmail("carlos@outlook.es");
        //request.setPassword("12345678");
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

    @Test
    @DisplayName("Test Sesion Function")
    public void testSesion(){
        assertTrue(userServiceImpl.testSesion());
    }

    @Test
    @DisplayName("Test User Not Found")
    public void testUserNotFound(){
        Integer a = 10;
        assertThrows(NotFoundException.class, ()->userServiceImpl.deleteUser(a));
    }

    @Test
    @DisplayName("Test User Not Allowed")
    public void testInValidUser(){
        Integer a = 4;
        assertThrows(AccessDeniedException.class, ()->userServiceImpl.deleteUser(a));
    }

    
    @Test
    @DisplayName("Test Delete new User")
    public void testDeleteNewUser(){
        Integer a = 1;
        assertNotNull(userServiceImpl.deleteUser(a));
    }
    
    /*
    @Test
    @DisplayName("Test Delete Old User")
    public void testDeleteOldUser(){
        Integer a = 2;
        assertNotNull(userServiceImpl.deleteUser(a));
    }*/
    
    // INSERT INTO users (users.id_user, users.name, users.email, users.password, users.role, users.created_at, users.updated_at) VALUES('1', 'Josefina', 'josefina@outlook.es', 'josefa123', '1', '2021-05-19 03:16:14', '2021-05-27 00:00:00')
}   
