package com.jonservices.meetingscheduler.repository;

import com.jonservices.meetingscheduler.data.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query
    Page<Room> findByNameContainingIgnoreCase(String roomName, Pageable page);
}
