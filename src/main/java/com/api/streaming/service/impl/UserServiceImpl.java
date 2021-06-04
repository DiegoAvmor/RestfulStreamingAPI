package com.api.streaming.service.impl;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import com.api.streaming.exception.NotFoundException;
import com.api.streaming.exception.AccessDeniedException;
import com.api.streaming.exception.AlreadyRegistered;
import com.api.streaming.model.User;
import com.api.streaming.model.UserPreferencesTags;
import com.api.streaming.model.UserRecommendation;
import com.api.streaming.model.dto.TokenDto;
import com.api.streaming.model.request.LoginUserRequest;
import com.api.streaming.model.request.RegisterUserRequest;
import com.api.streaming.repository.RecommendationRepository;
import com.api.streaming.repository.RolesRepository;
import com.api.streaming.repository.UserPreferencesTagsRepository;
import com.api.streaming.repository.UserRepository;
import com.api.streaming.service.UserService;
import com.api.streaming.util.JwtTokenUtil;
import com.api.streaming.util.UserUtil;
import com.api.streaming.model.Clasification;
import com.api.streaming.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository; 

    @Autowired
    private UserPreferencesTagsRepository userPreferencesTagsRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public TokenDto loadUser(LoginUserRequest request) {
        Optional<User> opt = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (opt.isPresent()) {
            User user = opt.get();
            //Se crea el token
            TokenDto token = new TokenDto();
            token.setToken(jwtTokenUtil.generateToken(user));
            return token;
        }
        throw new NotFoundException("El usuario no fue encontrado");
    }

    @Override
    public User getUser(Integer id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new NotFoundException();
    }

    @Override
    public User registerUser(RegisterUserRequest request){
        Optional<User> opt = userRepository.findByEmail(request.getEmail());
        if (!opt.isPresent()){
            //Para crear el nuevo usuario
            User nuevoUser = new User();
            nuevoUser.setName(request.getName());
            nuevoUser.setEmail(request.getEmail());
            nuevoUser.setPassword(request.getPassword());
            nuevoUser.setRole(rolesRepository.findByName("USER").get());
            nuevoUser = userRepository.save(nuevoUser);

            //Para agregar sus categorias preferidas
            UserPreferencesTags userTags = new UserPreferencesTags();
            userTags.setId(nuevoUser.getId());
            userTags.setUser(nuevoUser);
            //Solo meto la cadena con los enum
            userTags.setTag(request.getPreferences().toString());
            userTags = userPreferencesTagsRepository.save(userTags);
            return nuevoUser;
        }
        throw new AlreadyRegistered("Ya existe una cuenta asociada a ese correo");
    }

    @Override
    public List<UserRecommendation> getRecommendations(Integer id){

        Optional<List<UserRecommendation>> recommendations = recommendationRepository.findByIdUser(id);
        if(recommendations.isPresent()){
            //Solo devuelvo el string, no está como array
            return recommendations.get();
        }
        throw new NotFoundException("No se recommendaciones asociadas al usuario");
    }

    @Override
    public User deleteUser(Integer id){
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()){
            if(UserUtil.getActualSession().getEmail().equals(opt.get().getEmail())){
                userRepository.delete(opt.get());
                return opt.get();
            }
            throw new AccessDeniedException("No tiene permitido realizar esa instrucción");
        }
        throw new NotFoundException("El usuario no fue encontrado");
    }

    @Override
    public User editUser(Integer id, RegisterUserRequest request){
        Optional<User> opt = userRepository.findById(id);
        Optional<UserPreferencesTags> upt = userPreferencesTagsRepository.findById(id);

        if(opt.isPresent() && upt.isPresent()){
            if(UserUtil.getActualSession().getEmail().equals(opt.get().getEmail())){
                opt.get().setEmail(request.getEmail());
                opt.get().setName(request.getName());
                opt.get().setPassword(request.getPassword());
                userRepository.save(opt.get());
    
                upt.get().setTag(request.getPreferences().toString());
                userPreferencesTagsRepository.save(upt.get());
                
                return opt.get();
            }
            throw new AccessDeniedException("No tiene permitido realizar esa instrucción");
        }
        throw new NotFoundException("No se encontró información relacionada");
    }
       
}
