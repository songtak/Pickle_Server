package com.pickle.web.users;

import com.pickle.web.commons.JpaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

interface UserService extends JpaService<User> {
    User findUser(User user);
    User findByUserCode(int userCode);
    User findOneById(String id);

}
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(Long.valueOf(id));
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public int count() {
        return (int) userRepository.count();
    }

    @Override
    public void delete(String id) {
        userRepository.delete(findById(id).orElse(new User()));
    }

    @Override
    public boolean exists(String id) {
        return userRepository.existsById(Long.valueOf(id));
    }

    @Override
    public User findUser(User user) {
        return userRepository.findUser(user);
    }

    @Override
    public User findByUserCode(int userCode) {
        return userRepository.findByUserCode(userCode);
    }

    @Override
    public User findOneById(String id) {
        return userRepository.getOne(Long.parseLong(id));
    }


}