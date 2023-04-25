package com.nineplus.localhand.repository;

import com.nineplus.localhand.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findAll();

    @Query(value = "select r.* from roles r join user_role ur WHERE ur.USER_ID = :userId", nativeQuery = true)
    List<Role> getRolesUser(@Param("userId") Long userId);

    Role findByRoleName(String name);
}
