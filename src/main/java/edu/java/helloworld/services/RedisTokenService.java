package edu.java.helloworld.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import edu.java.helloworld.model.RedisToken;
import edu.java.helloworld.repository.RedisTokenRepository;

@Service
@RequiredArgsConstructor
public class RedisTokenService {
    private final RedisTokenRepository redisTokenRepository;

    public void save(RedisToken token) {
        redisTokenRepository.save(token);
    }

    public void remove(String id) {
        isExists(id);
        redisTokenRepository.deleteById(id);
    }

    public boolean isExists(String id) {
        if (!redisTokenRepository.existsById(id)) {

        }
        return true;
    }
}