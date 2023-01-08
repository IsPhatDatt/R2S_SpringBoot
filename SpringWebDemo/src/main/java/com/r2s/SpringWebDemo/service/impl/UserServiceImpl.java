package com.r2s.SpringWebDemo.service.impl;

import com.r2s.SpringWebDemo.dto.request.CreateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.request.UpdateUserRequestDTO;
import com.r2s.SpringWebDemo.dto.response.ProductResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateProductResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UpdateUserResponseDTO;
import com.r2s.SpringWebDemo.dto.response.UserResponseDTO;
import com.r2s.SpringWebDemo.entity.Product;
import com.r2s.SpringWebDemo.entity.User;
import com.r2s.SpringWebDemo.repository.ProductRepository;
import com.r2s.SpringWebDemo.repository.UserRepository;
import com.r2s.SpringWebDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.r2s.SpringWebDemo.constants.Constants.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserResponseDTO> getAllUser(Integer page, Integer size) {
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        List<User> users = userRepository.getAllUser(page, size);
        for (User user : users) {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(user.getId());
            userResponseDTO.setFirstName(user.getFirstName());
            userResponseDTO.setLastName(user.getLastName());
            userResponseDTO.setUsername(user.getUsername());
            userResponseDTOList.add(userResponseDTO);
        }

        return userResponseDTOList;
    }

    @Override
    public UserResponseDTO getUserById(Integer userId) {

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        try {
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent() && user.get().getIsDeleted() == UserIsDeleteFalse) {
                userResponseDTO.setId(user.get().getId());
                userResponseDTO.setFirstName(user.get().getFirstName());
                userResponseDTO.setLastName(user.get().getLastName());
                userResponseDTO.setUsername(user.get().getUsername());
            } else {
                throw new NoSuchElementException("Can't find userId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) {

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        User user = new User();

        try {
            if(createUserRequestDTO.getUsername().isEmpty()) {
                throw new Exception("Username is required!");
            }
            if(this.userRepository.existsByUsername(createUserRequestDTO.getUsername())) {
                throw new Exception("Product name is existed!");
            }
            if(createUserRequestDTO.getPassword().isEmpty()) {
                throw new Exception("Password is required!");
            }
            else {
                user.setFirstName(createUserRequestDTO.getFirstName());
                user.setLastName(createUserRequestDTO.getLastName());
                user.setUsername(createUserRequestDTO.getUsername());
                user.setPassword(createUserRequestDTO.getPassword());
                if(user.getCreatedDate() == null) {
                    user.setCreatedDate(new Date());
                }
                if(user.getUpdatedDate() == null) {
                    user.setUpdatedDate(new Date());
                }
                user.setIsDeleted(ProductIsDeleteFalse);
                userResponseDTO.setId(user.getId());
                userResponseDTO.setFirstName(user.getFirstName());
                userResponseDTO.setLastName(user.getLastName());
                userResponseDTO.setUsername(user.getUsername());

                userRepository.save(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return userResponseDTO;
    }

    @Override
    public UpdateUserResponseDTO updateUser(Integer userId, UpdateUserRequestDTO updateUserRequestDTO) {

        UpdateUserResponseDTO updateUserResponseDTO = new UpdateUserResponseDTO();
        Optional<User> user = this.userRepository.findById(userId);

        try {
            if(user.isEmpty() || user.get().getIsDeleted() == UserIsDeleteTrue) {
                throw new Exception("Can't find userId");
            }
            if(updateUserRequestDTO.getUsername().isEmpty()) {
                throw new Exception("Username is required!");
            }
            if(this.userRepository.existsByUsername(updateUserRequestDTO.getUsername())) {
                throw new Exception("Username is existed!");
            }
            else {
                user.get().setId(userId);
                if(!user.get().getUsername().equals(updateUserRequestDTO.getUsername())) {
                    user.get().setUsername(updateUserRequestDTO.getUsername());
                }
                if(!user.get().getFirstName().equals(updateUserRequestDTO.getFirstName())) {
                    user.get().setFirstName(updateUserRequestDTO.getFirstName());
                }
                if(!user.get().getLastName().equals(updateUserRequestDTO.getLastName())) {
                    user.get().setLastName(updateUserRequestDTO.getLastName());
                }
                user.get().setUpdatedDate(new Date());
                updateUserResponseDTO.setId(user.get().getId());
                updateUserResponseDTO.setFirstName(user.get().getFirstName());
                updateUserResponseDTO.setLastName(user.get().getLastName());
                updateUserResponseDTO.setUsername(user.get().getUsername());

                userRepository.save(user.get());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return updateUserResponseDTO;
    }

    @Override
    public Boolean deleteUser(Integer userId) {

        try {
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()) {
                this.userRepository.deleteById(userId);
                return true;
            } else {
                throw new NoSuchElementException("Can't find userId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
