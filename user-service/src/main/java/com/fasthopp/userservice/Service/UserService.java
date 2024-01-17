package com.fasthopp.userservice.Service;

import com.fasthopp.userservice.Dto.ProfileGetDto;
import com.fasthopp.userservice.Repository.UserRepository;
import com.fasthopp.userservice.Entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public ProfileGetDto getUserProfile(String userEmail) {
        Optional<User> optionalUser = userRepo.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            ProfileGetDto userProfileDto = new ProfileGetDto();
            userProfileDto.setUserId(user.getId());
            userProfileDto.setRole(user.getRole().name());
            userProfileDto.setCompanyName(user.getCompanyName().getName());
            userProfileDto.setJoinedAt(user.getJoinedAt());
            BeanUtils.copyProperties(user, userProfileDto);
            return userProfileDto;
        }
        return null;
    }

    public ProfileGetDto updateUserProfile(String userEmail, ProfileGetDto userDto) {
        Optional<User> optionalUser = userRepo.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            BeanUtils.copyProperties(userDto, user);
            User updatedUser = userRepo.save(user);
            ProfileGetDto updatedUserDto = new ProfileGetDto();
            updatedUserDto.setUserId(updatedUser.getId());
            updatedUserDto.setRole(user.getRole().name());
            updatedUserDto.setCompanyName(user.getCompanyName().getName());
            updatedUserDto.setJoinedAt(user.getJoinedAt());
            BeanUtils.copyProperties(updatedUser, updatedUserDto);

            return updatedUserDto;
        }
        return null;
    }
}
