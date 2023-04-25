package com.nineplus.localhand.repository;

import com.nineplus.localhand.dto.jpa_interface.UserNearbyDto;
import com.nineplus.localhand.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUsersByUsernameOrEmailOrPhone(@Size(max = 150) String username, @Size(max = 150) String email,@Size(max = 150) String phone);

    Optional<User> findUsersByUsername(@Size(max = 150) String username);

    @Query(value = "select COUNT(*) FROM users u WHERE (u.USERNAME = :username OR u.EMAIL = :email OR u.PHONE = :phoneNumber);",
            nativeQuery = true)
    Integer checkUserExist(@Param("username")String username,
                                  @Param("email")String email,
                                  @Param("phoneNumber")String phoneNumber);

    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Transactional
    @Query(value = "update " +
            "USERS " +
            "set " +
            "LAST_GEO_LAT = :lat, " +
            "LAST_GEO_LNG = :lng, " +
            "LAST_GEO_AT = now()  " +
            "where " +
            "USER_ID = :userId;", nativeQuery = true)
    void updateUserLocation(@Param("lat")String lat,@Param("lng")String lng,@Param("userId")Long userId);

    @Procedure("P_GET_NEARBY_USER")
    @Transactional
    List<UserNearbyDto> getUsersNearBy(@Param("USERID") int userId, @Param("LOBLNG") Double jobLng,
                                       @Param("JOBLAT") Double jobLat, @Param("SEARCH_RADIUS") int radius,
                                       @Param("LIMITATION") int limitation, @Param("UNIT") String unit);
}
