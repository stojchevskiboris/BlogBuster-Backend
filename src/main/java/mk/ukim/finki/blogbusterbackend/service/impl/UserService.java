package mk.ukim.finki.blogbusterbackend.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.UserDTO;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.blogbusterbackend.model.mappers.UserMapper;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements mk.ukim.finki.blogbusterbackend.service.UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }


        };

    }
    @Override
    public List<UserDTO> discoverPeople(Long userId) {
        User currentUser = userRepository.findById(userId).orElseThrow(InvalidUserIdException::new);
        List<User> nonFollowers = userRepository.findAll().stream().
                filter(user -> !user.equals(currentUser))
                .filter(user -> !user.getFollowingUsers().contains(currentUser))
                .limit(10)
                .collect(Collectors.toList());
        return UserMapper.MapToListViewModel(nonFollowers);
    }

}
