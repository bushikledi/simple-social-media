package com.media.social.dto;

import com.media.social.model.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "about")
    private String about;

    @Column(name = "image_url")
    private String imageUrl;

    public UserDTO userToDTO(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.location = user.getLocation();
        this.about = user.getAbout();
        this.imageUrl = user.getImageUrl();
        return this;
    }
}
