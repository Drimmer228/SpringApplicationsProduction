package com.example.springmodels.repositories;

import com.example.springmodels.models.RoleModel;
import com.example.springmodels.models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel,Long> {
    UserModel findByUsername(String username);
    Iterable<UserModel> findAllByRoleRoleName(String role_roleName);

    UserModel findByRole(RoleModel role);
}
