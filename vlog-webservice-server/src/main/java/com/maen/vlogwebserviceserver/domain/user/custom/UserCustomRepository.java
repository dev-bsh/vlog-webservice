package com.maen.vlogwebserviceserver.domain.user.custom;

import com.maen.vlogwebserviceserver.domain.user.User;

import java.util.List;

public interface UserCustomRepository {
    List<User> findUserByKeyword(String keyword, Integer pageNumber);
}
