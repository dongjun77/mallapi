package com.project.mallapi.mongoRepository;

import com.project.mallapi.document.Todo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MongoTodoRepository extends MongoRepository<Todo, String> {

    // 특정 Todo 조회 (ID 기반)
//    Optional<Todo> findById(String id);

    // 특정 사용자의 미완료 Todo 리스트 조회 (페이지네이션 지원)
    @Query("{ 'memberEmail' : ?0, 'complete' : false }")
    Page<Todo> findByMemberEmailAndCompleteIsFalse(String email, Pageable pageable);

    // 특정 사용자의 미완료 Todo 리스트 조회 (모든 데이터)
    @Query(value = "{ 'memberEmail' : ?0, 'complete' : false, 'imageList': { '$elemMatch': { 'ord': 0 } } }")
    List<Todo> findAllByMemberEmailAndCompleteIsFalse(String email);

    // 특정 Todo의 작성자 이메일 가져오기 (필드 projection)
    @Query(value = "{ '_id' : ?0 }", fields = "{ 'memberEmail' : 1 }")
    Optional<String> findMemberEmailById(String id);

    @Query(value = "{ 'complete' : true }")
    List<Todo> findAllByComplete(boolean b);

    @Query("{ 'member.$id' :  ?0 }")
    List<Todo> findAllByMember(String email);

    Page<Todo> findAll(Pageable pageable);

}
