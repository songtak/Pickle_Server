package com.pickle.web.users;

import com.pickle.web.commons.Messenger;
import com.pickle.web.commons.Proxy;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    @Autowired Proxy proxy;
    @PostMapping("/login")
    @Transactional
    public User login(@RequestBody User user) {
        User u = userService.findUser(user);
        proxy.countAll(u.getSchoolCode());
        return u;
    }

    @GetMapping("/find/{userCode}")
    public User find(@PathVariable String userCode) {
        return userService.findByUserCode(Integer.parseInt(userCode));
    }

    @PostMapping("/update") @Transactional
    public void update(@RequestBody User user) {
        userRepository.update(user);
    }

    @GetMapping("/check/{id}/{password}") @Transactional
    public Messenger checkUserPw(@PathVariable("id") String id,
                                  @PathVariable("password") String password) {
        Messenger message = Messenger.FAIL;
        if(userService.findOneById(id).getUserPw().equals(password)) message = Messenger.SUCCESS;
        return message;
    }
    @GetMapping("/update/{id}/{newUserPw}") @Transactional
    public void updateUserPw(@PathVariable("id") String id,
                             @PathVariable("newUserPw") String newUserPw){
        User user = userService.findOneById(id);
        user.setUserPw(newUserPw);
        userRepository.save(user);
    }
}
