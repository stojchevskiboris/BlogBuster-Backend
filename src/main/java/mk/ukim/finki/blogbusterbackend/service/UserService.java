package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.UserDTO;
import mk.ukim.finki.blogbusterbackend.model.enumerations.Role;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import mk.ukim.finki.blogbusterbackend.utils.EmailValidator;
import mk.ukim.finki.blogbusterbackend.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDTO userDTO, String password) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }
        if (userDTO.getPassword().equals((userDTO.getMatchingPassword()))) {
            throw new RuntimeException("Passwords do not match");
        }
        EmailValidator emailValidator = new EmailValidator();
        if(!emailValidator.isValid(userDTO.getEmail()))
            throw new RuntimeException("Invalid email");
        PasswordValidator passwordValidator = new PasswordValidator();
        if(!passwordValidator.isValid(userDTO.getPassword()))
            throw new RuntimeException("Password needs to be at least 8 characters long, containing at least one special character," +
                    " at least one uppercase letter and  at least one digit");

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.ROLE_USER);

        return userRepository.save(user);
    }

}
