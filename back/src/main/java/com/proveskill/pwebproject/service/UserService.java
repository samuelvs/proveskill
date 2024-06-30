package com.proveskill.pwebproject.service;

import com.proveskill.pwebproject.model.User;
import com.proveskill.pwebproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    
    public List<User> findAll() {
			return this.repository.findAll();
    }

    public Optional<User> findByEmail(String email) {
			return this.repository.findByEmail(email);
    }

    public Optional<User> findById(Integer id) {
			return this.repository.findById(id);
    }
     
    public User update(User userDto) throws Exception {
			try {
				if(userDto.getId() == null) {
					throw  new Exception("UserService - update: User id not found");
				}

				User userFound = this.repository.findById(userDto.getId()).orElseThrow(
							() -> new Exception("UserServiceImpl - create/update: User not found")
				);

				userFound.setName(userDto.getName());
				userFound.setEmail(userDto.getEmail());
				userFound.setSchool(userDto.getSchool());
				userFound.setFirstAccess(userDto.getFirstAcess() || true);
				userFound.setRole(userDto.getRole());
				User userSaved = this.repository.saveAndFlush(userFound);
				userSaved.setPassword(null);
				return userSaved;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("UserService - update: Error when update user: {}", e);
			}
    }

    public void delete(Integer id) {
			try {
				Optional<User> user = this.repository.findById(id);
				if(user.isPresent()) {
					this.repository.delete(user.get());
				} else {
					throw new Exception("UserService - delete: User not found");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("UserService - delete: Error when delete User: {}", e);
			}
    }
}
