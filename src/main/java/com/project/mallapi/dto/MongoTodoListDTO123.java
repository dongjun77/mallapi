package com.project.mallapi.dto;

import com.project.mallapi.document.TodoImage;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

public interface MongoTodoListDTO123 {

    String getId();   // MongoDB에서는 ID가 String 타입

    String getTitle();

    String getContent();

    String getMemberEmail();

    boolean isComplete();

    LocalDate getDueDate();

    @Value("#{target.imageList.fileName}")
    List<TodoImage> getImageList();

    String imageFile();

}
