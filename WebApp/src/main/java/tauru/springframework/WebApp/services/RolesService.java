package tauru.springframework.WebApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tauru.springframework.WebApp.entities.Roles;
import tauru.springframework.WebApp.repositories.RolesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {


    @Autowired
    private RolesRepository rolesRepository;


    public Roles findRoleById(Long roleId)
    {
        return rolesRepository.findRoleById(roleId);
    }

    public List<Roles> findAllRoles()
    {
        return rolesRepository.findAll();
    }
}
