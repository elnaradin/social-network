package ru.itgroup.intouch.service.account;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HashGeneratorImpl implements HashGenerator {
    @Override
    public String generateHash() {
        return UUID.randomUUID().toString();
    }
}
