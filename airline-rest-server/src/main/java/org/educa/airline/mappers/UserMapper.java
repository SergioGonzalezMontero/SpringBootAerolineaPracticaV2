package org.educa.airline.mappers;


import org.educa.airline.dto.UserDTO;
import org.educa.airline.entity.User;
import org.educa.airline.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de mapear entre objetos Passenger y PassengerDTO.
 */
@Component
public class UserMapper {
    private final SecurityUtil securityUtil;

    @Autowired
    UserMapper(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }


    /**
     * Convierte un objeto PassengerDTO a un objeto Passenger.
     *
     * @param userDTO Objeto PassengerDTO a convertir.
     * @return Objeto Passenger resultante.
     */

    public User toEntity(UserDTO userDTO) throws NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidKeyException {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(securityUtil.createHash(userDTO.getPassword()));
        user.setNif(securityUtil.crypt(userDTO.getNif()));
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(securityUtil.crypt(userDTO.getEmail()));
        user.setRoles(userDTO.getRoles());
        return user;
    }

    /**
     * Convierte un objeto Passenger a un objeto PassengerDTO.
     *
     * @param user Objeto Passenger a convertir.
     * @return Objeto PassengerDTO resultante.
     */


    public UserDTO toDTO(User user) throws NullPointerException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(null);
        userDTO.setNif(securityUtil.decrypt(user.getNif()));
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(securityUtil.decrypt(user.getEmail()));
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

    /**
     * Convierte una lista de objetos Passenger a una lista de objetos PassengerDTO.
     *
     * @param userList Lista de objetos user a convertir.
     * @return Lista de objetos PassengerDTO resultante.
     */
    public List<UserDTO> toDTOs(List<User> userList) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : userList) {
            UserDTO usertDTO = toDTO(user);
            userDTOs.add(usertDTO);
        }
        return userDTOs;
    }
}