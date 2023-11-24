package com.example.newsfeedproject.profile;

import com.example.newsfeedproject.dto.profiledto.ProfileRequestDto;
import com.example.newsfeedproject.dto.profiledto.ProfileResponseDto;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserRoleEnum;
import com.example.newsfeedproject.repository.UserRepository;
import com.example.newsfeedproject.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProfileServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;


    @Test
    void testGetProfile_WhenProfileExists_ReturnsProfileResponseDto() {
        // Arrange
        Long userId = 1L;
        String username = "testus12";
        String email = "test@example.com";
        String password = "PassWo12";
        String intro = "Test intro";

        User mockUser = new User(username, password, email, UserRoleEnum.USER, intro);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        ProfileResponseDto result1 = profileService.getProfile(userId);

        // Assert
        assertEquals(username, result1.getUsername());
        assertEquals(email, result1.getEmail());
        assertEquals(intro, result1.getIntro());


        //fix
        username = "AgoodMan";
        intro = "this is tested";
        email = "qiz05@gmail.com";
        ProfileRequestDto profileRequestDto = new ProfileRequestDto(username,intro,email);
        // Act
        profileService.updateProfile(userId,profileRequestDto);
        ProfileResponseDto result2 = profileService.getProfile(userId);
        // Assert
        assertEquals(username, result2.getUsername());
        assertEquals(email, result2.getEmail());
        assertEquals(intro, result2.getIntro());
    }
}

