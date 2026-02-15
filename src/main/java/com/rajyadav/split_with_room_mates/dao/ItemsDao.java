package com.rajyadav.split_with_room_mates.dao;

import java.util.List;

import com.rajyadav.split_with_room_mates.dto.Items;

public interface ItemsDao {

	Items saveItemsDao(Items items, String roomName);

	List<Items> findByRoomsIdDao(int roomId);
}
