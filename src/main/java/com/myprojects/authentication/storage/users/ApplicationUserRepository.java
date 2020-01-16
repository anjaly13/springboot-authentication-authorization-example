package com.myprojects.authentication.storage.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Integer> {

    ApplicationUser findByUsername(String username);

    @Query("SELECT u FROM ApplicationUser u where u.username=:usr AND u.password=:pass ")
    ApplicationUser findByUsernameAndPassword(@Param("usr") String username,@Param("pass") String password);
}
