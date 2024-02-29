package org.educa.airline.controllers;


import jakarta.validation.Valid;
import org.educa.airline.dto.UserDTO;
import org.educa.airline.entity.User;
import org.educa.airline.exceptions.WhitOutPermissException;
import org.educa.airline.mappers.UserMapper;
import org.educa.airline.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * Controlador para gestionar los pasajeros en vuelos.
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Constructor del controlador.
     *
     * @param userService Servicio para la gestión de pasajeros.
     * @param userMapper  Mapeador de pasajeros.
     */
    @Autowired
    public UserController(UserService userService,
                          UserMapper userMapper) {

        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param userDTO Datos del pasajero a crear.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody @Valid UserDTO userDTO) {
        User user = null;
        try {
            user = userMapper.toEntity(userDTO);
            if (!userService.exitUser(user.getUsername())) {
                if (userService.create(user)) {
                    return ResponseEntity.status(201).build(); // Si se crea correctamente, devuelve 201 Created
                } else {
                    return ResponseEntity.status(409).build(); // Si ya existe un pasajero con ese DNI en el vuelo, devuelve 409 Conflict
                }
            } else {
                System.out.println("usuario duplicado no se crea");
                return ResponseEntity.status(409).build();
            }
        } catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | InvalidKeyException |
                 NoSuchAlgorithmException e) {
            return ResponseEntity.badRequest().build();
        }

    }


    /**
     * Verifica si un pasajero existe en un vuelo.
     *
     * @param username ID del usuario.
     * @return ResponseEntity con los detalles del pasajero si existe.
     *//*
    @GetMapping(path = "/exists/{username}")
    public ResponseEntity<Void> existUser(@PathVariable("username") String username) {
        // Intenta encontrar el pasajero en el vuelo dado
        if (userService.exitUser(username)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
    @GetMapping(path = "/{username}")
    public ResponseEntity<UserDTO> findUser(@PathVariable("username") String username) {
        try {
            if (userMapper.toDTO(userService.findUser(username)) != null) {
                return ResponseEntity.ok(userMapper.toDTO(userService.findUser(username)));
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException e) {
            return ResponseEntity.badRequest().build();
        } catch (WhitOutPermissException e) {
            return ResponseEntity.status(403).build();
        }

    }

    /**
     * Actualiza los detalles de un pasajero en un vuelo.
     *
     * @param username nombre usuario del usuario.
     * @param userDTO  Nuevos detalles del pasajero.
     * @return ResponseEntity con los detalles del pasajero actualizados.
     */
    @PutMapping(path = "/{username}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("username") String username, @RequestBody UserDTO userDTO) {
        User user = null;

        if (!username.isBlank()) {
            try {
                user = userMapper.toEntity(userDTO);
                // Intenta actualizar los detalles del pasajero en el vuelo dado
                if (userService.update(user, username)) {

                    // Si se actualiza correctamente, devuelve los detalles actualizados del usuario
                    return ResponseEntity.ok(userMapper.toDTO(user));
                } else {
                    return ResponseEntity.notFound().build(); // Si el pasajero no existe en el vuelo, devuelve 404 Not Found
                }
            } catch (NoSuchAlgorithmException e) {
                return ResponseEntity.badRequest().build();
            } catch (WhitOutPermissException e) {
                return ResponseEntity.status(403).build();
            } catch (Exception e) {
                return ResponseEntity.badRequest().build(); // Si ocurre algún otro error, devuelve 400 Bad Request
            }
        } else {
            return ResponseEntity.badRequest().build(); // Si el nombre no es válido, devuelve 400 Bad Request
        }
    }

    /**
     * Elimina un pasajero de un vuelo.
     *
     * @param username del usuario.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping(path = "/{username}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable("username") String username) {

        try {
            if (userService.delete(username)) {
                return ResponseEntity.ok().build(); // Si se elimina correctamente, devuelve 200 OK
            }else{
                return ResponseEntity.notFound().build();
            }
        } catch (WhitOutPermissException e) {
            return ResponseEntity.status(403).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
         // Si ocurre algún otro error, devuelve 400 Bad Request

    }

}


