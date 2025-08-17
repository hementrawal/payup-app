package com.payup.payupapp.repository;

import com.payup.payupapp.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember,Long> {
}
