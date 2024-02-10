package com.solomon.nkwor.solomon.bank.Converter;

import com.solomon.nkwor.solomon.bank.DTO.GetUserDTO;
import com.solomon.nkwor.solomon.bank.DTO.UserDTO;
import com.solomon.nkwor.solomon.bank.Model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    private ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public GetUserDTO convertUserToDTO(User user){
        return modelMapper.map(user, GetUserDTO.class);
    }
}

