package ru.itgroup.intouch.repository;

import java.util.List;

public interface UserRepository {

    List<Long> findUserIdByName(String name);
}
