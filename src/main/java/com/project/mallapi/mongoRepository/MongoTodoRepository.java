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

    // 특정 사용자의 미완료 Todo 리스트 조회 (페이지네이션)
    @Query(value = "{ 'memberEmail' : ?0, 'complete' : false, 'imageList': { '$elemMatch': { 'ord': 0 } } }")
    Page<Todo> findAllByMemberEmailAndCompleteIsFalse(String memberEmail, Pageable pageable);

    // 특정 사용자의 미완료 Todo 리스트 조회 (모든 데이터)
    @Query(value = "{ 'memberEmail' : ?0, 'complete' : false, 'imageList': { '$elemMatch': { 'ord': 0 } } }")
    List<Todo> findAllByMemberEmailAndCompleteIsFalse(String memberEmail);

    // 특정 Todo의 작성자 이메일 가져오기 (필드 projection)
    @Query(value = "{ '_id' : ?0 }", fields = "{ 'memberEmail' : 1 }")
    Optional<String> findMemberEmailById(String id);

    @Query(value = "{ 'complete' : true }")
    List<Todo> findAllByComplete(boolean b);

//    @Query("{ 'member.$id' :  ?0 }")
    @Query("{ 'memberEmail' :  ?0 }")
    List<Todo> findAllByMemberEmail(String memberEmail);

    Page<Todo> findAll(Pageable pageable);

}
