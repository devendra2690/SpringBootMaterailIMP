package com.devendrabrains.EventMgr.repository;

import com.devendrabrains.EventMgr.entity.EventHttpLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventHttpLogRepository extends JpaRepository<EventHttpLog, Long> {

}
