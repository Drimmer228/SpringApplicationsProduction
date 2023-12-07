package com.example.springmodels.repositories;

import com.example.springmodels.models.RoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleModel,Long> {
    RoleModel findByRoleName(String user);

    RoleModel findRoleModelById(Long id);
}
