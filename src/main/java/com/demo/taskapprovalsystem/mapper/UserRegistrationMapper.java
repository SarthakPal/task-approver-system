package com.demo.taskapprovalsystem.mapper;

import com.demo.taskapprovalsystem.entity.User;
import com.demo.taskapprovalsystem.request.CreateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {

    UserRegistrationMapper INSTANCE = Mappers.getMapper(UserRegistrationMapper.class);

    /**
     * Convert CreateUserRequest to User entity.
     * Static fields are mapped directly using @Mapping annotations.
     *
     * @param saveEventRequest the request object containing user data
     * @param loginId generated login ID
     * @param hashedPassword hashed password
     * @return User entity
     */
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    User createUserRequestToUser(CreateUserRequest saveEventRequest, String loginId, String hashedPassword);

    /**
     * Custom method to handle dynamic fields like loginId and hashedPassword.
     * This method will be called after the mapping process is completed.
     */
    default User mapToUserWithDynamicFields(CreateUserRequest saveEventRequest, String loginId, String hashedPassword) {
        User user = createUserRequestToUser(saveEventRequest, loginId, hashedPassword);
        user.setLoginId(loginId);
        user.setPassword(hashedPassword);
        return user;
    }
}
