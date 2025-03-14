package com.demo.taskapprovalsystem.repository;

import com.demo.taskapprovalsystem.entity.Task;
import com.demo.taskapprovalsystem.entity.TaskApproverMapping;
import com.demo.taskapprovalsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskApproverMappingRepository extends JpaRepository<TaskApproverMapping, Long> {

    @Query(value = "SELECT * FROM task_approver_mapping tam WHERE tam.task_id = :taskId AND tam.approved_by = :approverId", nativeQuery = true)
    Optional<TaskApproverMapping> findByTaskAndApprover(Task task, User approver);

    List<TaskApproverMapping> findByTask(Task task);


}
