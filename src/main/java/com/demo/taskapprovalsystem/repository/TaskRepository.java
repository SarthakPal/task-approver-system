package com.demo.taskapprovalsystem.repository;

import com.demo.taskapprovalsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {



}
