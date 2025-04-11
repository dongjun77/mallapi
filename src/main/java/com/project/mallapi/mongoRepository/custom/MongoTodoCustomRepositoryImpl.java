package com.project.mallapi.mongoRepository.custom;

import com.project.mallapi.dto.MongoTodoListDTO;
import com.project.mallapi.dto.TodoListDTO;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MongoTodoCustomRepositoryImpl implements MongoTodoCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<MongoTodoListDTO> findTodoListDTOByMemberEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberEmail").is(email).and("complete").is(false));
        query.fields()
                .include("_id")
                .include("title")
                .include("content")
                .include("memberEmail")
                .include("complete")
                .include("dueDate")
                .slice("imageList", 1); // 대표 이미지 1개만 가져오기

        List<Document> documents = mongoTemplate.find(query, Document.class, "tbl_todo");

        List<MongoTodoListDTO> dtoList = new ArrayList<>();
        for (Document doc : documents) {

            ObjectId objectId = doc.getObjectId("_id");
            String id = objectId != null ? objectId.toHexString() : null;

            String fileName = null;
            List<Document> imageList = (List<Document>) doc.get("imageList");
            if (imageList != null && !imageList.isEmpty()) {
                fileName = imageList.get(0).getString("fileName");
            }
            Date dueDate = doc.getDate("dueDate");
            LocalDate localDueDate = dueDate != null
                    ? dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    : null;

            MongoTodoListDTO dto = new MongoTodoListDTO(
                    id,
                    doc.getString("title"),
                    doc.getString("content"),
                    doc.getString("memberEmail"),
                    doc.getBoolean("complete"),
                    localDueDate,
                    fileName
            );
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public Page<MongoTodoListDTO> findTodoListDTOByMemberEmail(String email, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberEmail").is(email).and("complete").is(false));
        query.fields()
                .include("_id")
                .include("title")
                .include("content")
                .include("memberEmail")
                .include("complete")
                .include("dueDate")
                .slice("imageList", 1); // 대표 이미지 1개만 가져오기

        query.skip(pageable.getOffset());
        query.limit(pageable.getPageSize());

        if (pageable.getSort().isSorted()) {
            query.with(pageable.getSort());
        }

        List<Document> documents = mongoTemplate.find(query, Document.class, "tbl_todo");

        List<MongoTodoListDTO> dtoList = new ArrayList<>();
        for (Document doc : documents) {

            ObjectId objectId = doc.getObjectId("_id");
            String id = objectId != null ? objectId.toHexString() : null;

            String fileName = null;
            List<Document> imageList = (List<Document>) doc.get("imageList");
            if (imageList != null && !imageList.isEmpty()) {
                fileName = imageList.get(0).getString("fileName");
            }
            Date dueDate = doc.getDate("dueDate");
            LocalDate localDueDate = dueDate != null
                    ? dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    : null;

            MongoTodoListDTO dto = new MongoTodoListDTO(
                    id,
                    doc.getString("title"),
                    doc.getString("content"),
                    doc.getString("memberEmail"),
                    doc.getBoolean("complete"),
                    localDueDate,
                    fileName
            );
            dtoList.add(dto);
        }

        Query countQuery = new Query();
        countQuery.addCriteria(Criteria.where("memberEmail").is(email).and("complete").is(false));
        long total = mongoTemplate.count(countQuery, "tbl_todo");

        return new PageImpl<>(dtoList, pageable, total);
    }
}
