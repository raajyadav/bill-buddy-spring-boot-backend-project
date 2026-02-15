package com.rajyadav.split_with_room_mates.dao;

import com.rajyadav.split_with_room_mates.dto.Owe;

public interface OweDao {

	Owe findByUserIdDao(int userId);
}
