package com.test1.repos;

import com.test1.domain.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepo extends CrudRepository<Room, Integer>  {

    List<Room> findByText(String text);


}
