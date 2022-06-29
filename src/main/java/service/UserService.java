package service;

import dto.RegisterDto;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User register(RegisterDto registerDto){
        User user = new User();
        user.setName(registerDto.getName());
        user.setPassword(registerDto.getPassword());
        user.setUsername(registerDto.getUsername());

        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findUser(int id){
        return userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("Not Found User");
        });
    }
}
