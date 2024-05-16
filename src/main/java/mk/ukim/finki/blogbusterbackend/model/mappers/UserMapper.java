package mk.ukim.finki.blogbusterbackend.model.mappers;

import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDTO MapToViewModel(User user){
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
        );
    }
    public static List<UserDTO> MapToListViewModel(List<User> users){
        List<UserDTO> userVM = new ArrayList<>();
        for (var u : users){
            userVM.add(MapToViewModel(u));
        }
        return userVM;
    }
}
