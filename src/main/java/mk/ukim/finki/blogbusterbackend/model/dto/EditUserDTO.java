package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class EditUserDTO {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    public EditUserDTO(String currentPassword, String newPassword, String confirmNewPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }
}
