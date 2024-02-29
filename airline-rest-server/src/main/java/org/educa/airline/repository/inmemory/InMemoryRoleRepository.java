package org.educa.airline.repository.inmemory;

import org.educa.airline.entity.Role;

public class InMemoryRoleRepository {
    public InMemoryRoleRepository() {
        Role roleAdmin = new Role("ROLE_admin", "admin", "Es un administrador");
        Role rolePersonal = new Role("ROLE_personal", "personal", "Es un personal");
        Role roleUsuario = new Role("ROLE_usuario", "usuario", "Es un usuario");
    }
}
