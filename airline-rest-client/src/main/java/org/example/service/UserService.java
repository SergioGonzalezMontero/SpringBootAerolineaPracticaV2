package org.example.service;

import org.example.api.ApiUserService;
import org.example.core.Client;
import org.example.dto.RoleDTO;
import org.example.dto.UserDTO;
import org.example.exception.NotFoundException;
import org.example.exception.NotLoggedCorretlyException;
import org.example.exception.NotPermissException;
import org.example.tools.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UserService {
    ApiUserService apiUserService = new ApiUserService();
    Tools tools = new Tools();

    private static boolean optionsSwitchUpload(Scanner scanner, String opcion, UserDTO userDTO) {
        switch (opcion) {
            case "1":
                System.out.println("Introduce el nuevo nombre de usuario:");
                String newUserName = scanner.nextLine().trim().toUpperCase();
                userDTO.setUsername(newUserName);
                break;
            case "2":
                System.out.println("Introduce la nueva contrasena:");
                String pass = scanner.nextLine().trim();
                userDTO.setPassword(pass);
                break;
            case "3":
                System.out.println("Introduce el nif:");
                String nif = scanner.nextLine().trim().toUpperCase();
                userDTO.setNif(nif);
                break;
            case "4":
                System.out.println("Introduce el nuevo nombre:");
                String nombre = scanner.nextLine().trim().toUpperCase();
                userDTO.setName(nombre);
                break;
            case "5":
                System.out.println("Introduce el nuevo apellido:");
                String surname = scanner.nextLine().trim().toUpperCase();
                userDTO.setSurname(surname);
                break;
            case "6":
                System.out.println("Introduce la nueva contrasena:");
                String email = scanner.nextLine().trim().toUpperCase();
                userDTO.setPassword(email);
                break;
            case "7":

                RoleDTO rol = null;
                boolean anadir = true;
                System.out.println("Elige una opcion: \n1. Anadir \n2. eliminar rol?");
                opcion = scanner.nextLine().toUpperCase().trim();


                switch (opcion) {
                    case "1":
                        System.out.println("Elegiste anadir rol");
                        anadir = true;
                        break;
                    case "2":
                        System.out.println("Elegiste eliminar rol");
                        anadir = false;
                        break;
                    default:
                        System.out.println("No escogiste una opción válida, no se hará el alta");

                }
                if ("1".equals(opcion) || "2".equals(opcion)) {
                    System.out.println("Elige el rol: \n1. Admin\n2. Personal\n3. Usuario");

                    opcion = scanner.nextLine();

                    switch (opcion) {
                        case "1":
                            System.out.println("Escogiste rol ADMIN");
                            rol = new RoleDTO("ROLE_admin", "admin", "Es un administrador");
                            break;
                        case "2":
                            System.out.println("Escogiste rol PERSONAL");
                            rol = new RoleDTO("ROLE_personal", "personal", "Es un personal");
                            break;
                        case "3":
                            System.out.println("Escogiste rol USUARIO");
                            rol = new RoleDTO("ROLE_usuario", "usuario", "Es un usuario");
                            break;
                        default:
                            System.out.println("No escogiste una opción válida, no se hará el alta");
                    }
                    if (rol != null) {
                        if (anadir) {

                            if (!userDTO.getRoles().contains(rol)) {
                                userDTO.getRoles().add(rol);
                            } else {
                                System.out.println("El usuario ya tiene ese rol");
                            }

                        } else {
                            userDTO.getRoles().remove(rol);
                        }

                    }
                }
                break;

            case "0":
                System.out.println("Volviendo sin modificar...");
                return true;
            default:
                System.out.println("Opción no válida");
        }
        return false;
    }

    private static String menuUpdate(Scanner scanner) {
        // Menú de opciones
        System.out.println("¿Qué deseas modificar?");
        System.out.println("1. UserName");
        System.out.println("2. Contrasena");
        System.out.println("3. NIF");
        System.out.println("4. Nombre");
        System.out.println("5. Apellido");
        System.out.println("6. Email");
        System.out.println("7. Rol");
        System.out.println("0. Volver sin modificar");

        return scanner.nextLine();
    }

    public void newUser(Scanner scanner) {
        System.out.println("Crear Usuario");
        System.out.println("Introduce Nombre de usuario: (min 1 caracter)");
        String username = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce contraseña de " + username + ": (min 4 caracteres)");
        String password = scanner.nextLine().trim();
        System.out.println("Introduce NIF de usuario: (min 1 caracter)");
        String nif = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce Nombre: (min 1 caracter)");
        String name = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce apellido: (min 1 caracter)");
        String surname = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce email: (min 1 caracter)");
        String email = scanner.nextLine().trim().toUpperCase();
        System.out.println("¿Qué rol tendrá " + username + "?\n1. Admin\n2. Personal\n3. Usuario");
        String opcion = scanner.nextLine().trim().toUpperCase();
        RoleDTO rol = null;
        switch (opcion) {
            case "1":
                System.out.println("Escogiste rol ADMIN");
                rol = new RoleDTO("ROLE_admin", "admin", "Es un administrador");
                break;
            case "2":
                System.out.println("Escogiste rol PERSONAL");
                rol = new RoleDTO("ROLE_personal", "personal", "Es un personal");
                break;
            case "3":
                System.out.println("Escogiste rol USUARIO");
                rol = new RoleDTO("ROLE_usuario", "usuario", "Es un usuario");
                break;
            default:
                System.out.println("Opción inválida, no se creará el usuario");
                return; // Salir del método si la opción no es válida
        }
        if (rol != null) {
            try {
                if (tools.esTextoValido(username) && tools.contrasenaValida(password)) {
                    List<RoleDTO> roles = new ArrayList<>();
                    // Lógica para verificar si el rol ya existe y añadirlo solo si no existe
                    roles.add(rol);
                    UserDTO userDTO = new UserDTO(username, password, nif, name, surname, email, roles);
                    System.out.println("No olvide recordar su nombre y contraseña, el usuario se creará");
                    apiUserService.create(userDTO);
                    System.out.println("<<<<<<<<<<<<<<<----------------------------->>>>>>>>>>>>>>>>");
                    System.out.println("Te has conectado con el usuario " + userDTO.getUsername());
                    System.out.println("<<<<<<<<<<<<<<<----------------------------->>>>>>>>>>>>>>>>");
                    Client.userLogged = userDTO;
                } else {
                    System.out.println("Los parámetros introducidos no son válidos");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: El servidor no está disponible");
            } catch (NotPermissException e) {
                System.out.println("Usuario sin permisos");
            } catch (NotLoggedCorretlyException e) {
                System.out.println("Usuario no logueado");
            } catch (NotFoundException e) {
                System.out.println("Usuario no encontrado");
            } catch (Exception e) {
                System.out.println("Error inesperado");
            }
        }
    }

    public void deleteUser(Scanner scanner) {
        UserDTO userDTO;
        try {
            userDTO = findUser(scanner);
            if (tools.confirmarAccion(scanner)) {
                apiUserService.delete(userDTO.getName());
                System.out.println("Usuario eliminado");
            } else {
                System.out.println("No se ha confirmado la eliminación");
            }

        } catch (NotPermissException e) {
            System.out.println("Usuario sin permisos");
        } catch (NotLoggedCorretlyException e) {
            System.out.println("Usuario no logueado");
        } catch (NotFoundException e) {
            System.out.println("Usuario no encontrado");
        } catch (RuntimeException e) {
            System.out.println("El server no está disponible");
        } catch (Exception e) {
            System.out.println("Error inesperado");
        }
    }

    public UserDTO findUser(Scanner scanner) {
        System.out.println("Consulta usuario por nombre de usuario");
        System.out.println("Introduce el nombre de usuario:");
        String username = scanner.nextLine().trim().toUpperCase();
        if (tools.esTextoValido(username)) {
            try {
                UserDTO userDTO = apiUserService.findUser(username);
                printUser(userDTO);
                return userDTO;
            } catch (NotPermissException e) {
                System.out.println("Usuario sin permisos");
            } catch (NotLoggedCorretlyException e) {
                System.out.println("Usuario no logueado");
            } catch (NotFoundException e) {
                System.out.println("Usuario no encontrado");
            } catch (RuntimeException e) {
                System.out.println("El server no está disponible");
            } catch (Exception e) {
                System.out.println("Error inesperado");
            }
        } else {
            System.out.println("El nombre no es valido");
        }
        return null;
    }

    public void updateUser(Scanner scanner) {
        System.out.println("Actualiza un usuario");

        UserDTO userDTO = findUser(scanner);
        userDTO.setPassword(Client.userLogged.getPassword());
        if (userDTO != null) {
            String name = userDTO.getName();


            String opcion = menuUpdate(scanner);

            if (optionsSwitchUpload(scanner, opcion, userDTO))
                return; // Salir del método sin realizar ninguna modificación
            try {
                if (tools.confirmarAccion(scanner)) {
                    // Llama al servicio para actualizar el pasajero en el vuelo
                    apiUserService.updateUser(userDTO, name);
                    System.out.println("Usuario actualizado");

                } else {
                    System.out.println("No se confirmó la actualización");
                }
            } catch (NotPermissException e) {
                System.out.println("Usuario sin permisos");
            } catch (NotLoggedCorretlyException e) {
                System.out.println("Usuario no logueado");
            } catch (NotFoundException e) {
                System.out.println("Usuario no encontrado");
            } catch (RuntimeException e) {
                System.out.println("El server no está disponible");
            } catch (Exception e) {
                System.out.println("Error inesperado");
            }
        } else {
            System.out.println("No se ha encontrado el usuario");
        }
    }

    public UserDTO login(Scanner scanner) {
        System.out.println("Introduce el username de usuario: ");
        String username = scanner.nextLine().toUpperCase().trim();
        System.out.println("Introduce el password: ");
        String pass = scanner.nextLine().toUpperCase().trim();
        if (tools.esTextoValido(username) && tools.esTextoValido(pass)) {
            try {
                UserDTO userDTO = apiUserService.login(username, pass);
                if (userDTO != null) {
                    Client.userLogged = userDTO;
                    userDTO.setPassword(pass);
                    System.out.println("Logeado con " + userDTO.getUsername());
                    System.out.println("username: " + userDTO.getUsername());
                    System.out.println("contrasena: " + userDTO.getPassword());
                } else {
                    System.out.println("Usuario no encontrado");
                }
                return userDTO;
            } catch (NotPermissException e) {
                System.out.println("Usuario sin permisos");
            } catch (NotLoggedCorretlyException e) {
                System.out.println("Usuario no logueado");
            } catch (NotFoundException e) {
                System.out.println("Usuario no encontrado");
            } catch (RuntimeException e) {
                System.out.println("El server no está disponible");
            } catch (Exception e) {
                System.out.println("Error inesperado");
            }
        }
        return null;
    }

    private void printUser(UserDTO userDTO) {
        System.out.println("----------------------------");
        System.out.println("Detalles del USUARIO:");
        System.out.println("Username: " + userDTO.getUsername());
        System.out.println("NIF: " + userDTO.getNif());
        System.out.println("Nombre: " + userDTO.getName());
        System.out.println("Apellido: " + userDTO.getSurname());
        System.out.println("email: " + userDTO.getEmail());
        System.out.println("----------------------------");
    }
}

