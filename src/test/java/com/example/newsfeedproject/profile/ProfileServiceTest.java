package com.example.newsfeedproject.profile;

import com.example.newsfeedproject.dto.profiledto.ProfileRequestDto;
import com.example.newsfeedproject.dto.profiledto.ProfileResponseDto;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.UserRepository;
import com.example.newsfeedproject.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.newsfeedproject.entity.UserRole.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProfileServiceTest {

    private UserRepository userRepository;
    private ProfileService profileService;

    @BeforeEach
    void init() {
        userRepository = mock(UserRepository.class);
        profileService = new ProfileService(userRepository);
    }

    @DisplayName("프로필 조회 테스트")
    @Test
    void testGetProfile_WhenProfileExists_ReturnsProfileResponseDto() {
        // Arrange
        Long userId = 1L;
        String username = "testus12";
        String email = "test@example.com";
        String password = "PassWo12";
        String intro = "Test intro";

        var mockUser = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(USER)
                .intro(intro)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        // Act
        ProfileResponseDto result1 = profileService.getProfile(userId);
        // Assert
        assertEquals(username, result1.getUsername());
        assertEquals(email, result1.getEmail());
        assertEquals(intro, result1.getIntro());
    }

    @DisplayName("프로필 업데이트 테스트")
    @Test
    void test() {
        // Update profile
        Long userId = 1L;
        var username = "AgoodMan";
        var intro = "this is tested";
        var email = "qiz05@gmail.com";
        var password = "1234";

        var mockUser = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(USER)
                .intro(intro)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        ProfileRequestDto profileRequestDto = new ProfileRequestDto(username, intro, email);
        // Act - Get updated profile
        profileService.updateProfile(userId, profileRequestDto);
        ProfileResponseDto result2 = profileService.getProfile(userId);
        // Assert updated profile data
        assertEquals(username, result2.getUsername());
        assertEquals(email, result2.getEmail());
        assertEquals(intro, result2.getIntro());
    }
}

