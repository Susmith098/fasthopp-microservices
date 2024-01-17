package com.fasthopp.userservice.Service;

import com.fasthopp.userservice.Dto.ProfileGetDto;
import com.fasthopp.userservice.Dto.RegisterRequest;
import com.fasthopp.userservice.Dto.UserDto;
import com.fasthopp.userservice.Entity.CompanyName;
import com.fasthopp.userservice.Repository.CompanyNameRepository;
import com.fasthopp.userservice.Repository.UserRepository;
import com.fasthopp.userservice.Entity.Role;
import com.fasthopp.userservice.Entity.User;
import com.fasthopp.userservice.Token.Token;
import com.fasthopp.userservice.Token.TokenRepository;
import com.fasthopp.userservice.Token.TokenType;
import com.fasthopp.userservice.event.UserRegisterEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ManagerService {

    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final CompanyNameRepository companyNameRepository;

    private final KafkaTemplate<String, UserRegisterEvent> kafkaTemplate;

    public User postUser(User user){
        return userRepo.save(user);
    }

    public UserDto register(RegisterRequest request, Role role) {

        // Get or create the CompanyName entity
        CompanyName companyName = getOrCreateCompanyName(request.getCompanyName());

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .companyName(companyName)
                .build();

        // Save the CompanyName entity first
        companyNameRepository.save(companyName);

        var savedUser = userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        var userDto = UserDto.builder()
                .name(savedUser.getName())
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().toString())
                .companyName(savedUser.getCompanyName().getName())
                .build();

        sendUserRegisteredMessage(userDto);

        return userDto;

    }

    private void sendUserRegisteredMessage(UserDto userDto) {
        try {
            UserRegisterEvent userRegisterEvent = new UserRegisterEvent(
                    userDto.getName(),
                    userDto.getEmail(),
                    userDto.getCompanyName(),
                    LocalDateTime.now()
            );

            kafkaTemplate.send("notificationTopic", userRegisterEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CompanyName getOrCreateCompanyName(String companyName) {
        Optional<CompanyName> existingCompanyName = companyNameRepository.findByName(companyName);
        return existingCompanyName.orElseGet(() -> {
            // Create a new CompanyName if it doesn't exist
            CompanyName newCompanyName = CompanyName.builder()
                    .name(companyName)
                    .build();
            return companyNameRepository.save(newCompanyName);
        });
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public List<ProfileGetDto> getAllParticipants(){
        String loggedInManagerCompanyName = getLoggedInUserCompanyName();
        return userRepo.findAllByCompanyName_Name(loggedInManagerCompanyName)
                .stream()
                .filter(user -> !user.isDeleted())
                .map(User::getProfileDto)
                .collect(Collectors.toList());
    }


    public List<UserDto> getAllUsers(){
        String loggedInManagerCompanyName = getLoggedInUserCompanyName();
        return userRepo.findAllByCompanyName_NameAndRole(loggedInManagerCompanyName, Role.USER)
                .stream()
                .filter(user -> !user.isDeleted())
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getAllManagers(){
        String loggedInManagerCompanyName = getLoggedInUserCompanyName();
        return userRepo.findAllByCompanyName_NameAndRole(loggedInManagerCompanyName, Role.MANAGER)
                .stream()
                .filter(user -> !user.isDeleted())
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(int id){

        userRepo.findById(id).ifPresent(user -> {
            if (user.getCompanyName().getName().equals(getLoggedInUserCompanyName())) {
                user.setDeleted(true);
                userRepo.save(user);
            }
        });
    }

    public void blockUser(int userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        optionalUser.ifPresent(user -> {
            if (user.getCompanyName().getName().equals(getLoggedInUserCompanyName())) {
                user.setBlocked(true);
                userRepo.save(user);
            }
        });
    }

    public void unblockUser(int userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        optionalUser.ifPresent(user -> {
            if (user.getCompanyName().getName().equals(getLoggedInUserCompanyName())) {
                user.setBlocked(false);
                userRepo.save(user);
            }
        });
    }


    public UserDto findById(int id){
        String loggedInManagerCompanyName = getLoggedInUserCompanyName();
        return userRepo.findById(id)
                .filter(user -> user.getCompanyName().getName().equals(loggedInManagerCompanyName))
                .map(user -> UserDto.builder()
                        .userId(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole().toString())
                        .build())
                .orElse(null);
    }


    public UserDto updateUser(int userId, UserDto userDto) {
        String loggedInManagerCompanyName = getLoggedInUserCompanyName();
        Optional<User> optionalUser = userRepo.findById(userId);
        return optionalUser.map(user -> {
            // Check if the user belongs to the same company as the logged-in manager
            if (user.getCompanyName().getName().equals(loggedInManagerCompanyName)) {
                BeanUtils.copyProperties(userDto, user);
                user.setRole(Role.valueOf(userDto.getRole()));
                User updatedUser = userRepo.save(user);

                return UserDto.builder()
                        .userId(updatedUser.getId())
                        .name(updatedUser.getName())
                        .email(updatedUser.getEmail())
                        .role(updatedUser.getRole().toString())
                        .build();
            } else {
                // User does not belong to the same company, return null or handle accordingly
                return null;
            }
        }).orElse(null);
    }

    private String getLoggedInUserCompanyName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((User) authentication.getPrincipal()).getCompanyName().getName();
    }

}
