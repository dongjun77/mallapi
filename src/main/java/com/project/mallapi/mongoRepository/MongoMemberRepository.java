package com.project.mallapi.mongoRepository;

import com.project.mallapi.document.Member;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MongoMemberRepository extends MongoRepository<Member, String> {

    @Query("{ 'email': ?0 }")
    Member findByEmailWithRoles(String email); // memberRoleList도 자동 포함

}
