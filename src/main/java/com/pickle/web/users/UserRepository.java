package com.pickle.web.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, IUserRepository {
    User findByUserCode(int userCode);

}
