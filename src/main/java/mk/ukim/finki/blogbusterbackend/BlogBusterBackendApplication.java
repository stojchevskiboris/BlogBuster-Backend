package mk.ukim.finki.blogbusterbackend;

import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.enumerations.Role;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BlogBusterBackendApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogBusterBackendApplication.class, args);
    }

    public void run(String...args) {
        User adminAccount = userRepository.findByRole(Role.ROLE_ADMIN);
        if(null == adminAccount) {
            User user = new User();

            user.setEmail("admin@gmail.com");
            user.setFirstname("admin");
            user.setLastname("admin");
            user.setRole(Role.ROLE_ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }

}
