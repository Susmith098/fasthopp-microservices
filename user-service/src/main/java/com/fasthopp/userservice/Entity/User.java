package com.fasthopp.userservice.Entity;

import com.fasthopp.userservice.Dto.ProfileGetDto;
import com.fasthopp.userservice.Dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.access.method.P;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty
    private String name;

    @Email(message = "Please provide a valid email address")
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private String address;

    private String designation;

    @ManyToOne
    @JoinColumn(name = "company_name_id")
    private CompanyName companyName;

//    private String secret;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isDeleted;

    private boolean blocked;

    @CreationTimestamp
    private LocalDateTime joinedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUserId(id);
        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setRole(role.name());
        userDto.setCompanyName(companyName.getName());

//        userDto.setAddress(address);
//        userDto.setDesignation(designation);
//        userDto.setJoinedAt(joinedAt);
//        userDto.setIsDeleted(isDeleted);
//        userDto.setBlocked(blocked);

        return userDto;
    }

    public ProfileGetDto getProfileDto(){
        ProfileGetDto getUsersDto = new ProfileGetDto();
        getUsersDto.setUserId(id);
        getUsersDto.setName(name);
        getUsersDto.setEmail(email);
        getUsersDto.setRole(role.name());
        getUsersDto.setCompanyName(companyName.getName());
        getUsersDto.setAddress(address);
        getUsersDto.setDesignation(designation);
        getUsersDto.setJoinedAt(joinedAt);
        getUsersDto.setDeleted(isDeleted);
        getUsersDto.setBlocked(blocked);

        return getUsersDto;
    }

}
