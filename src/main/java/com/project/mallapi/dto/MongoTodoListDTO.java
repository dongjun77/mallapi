package com.project.mallapi.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class MongoTodoListDTO {

    private String id;

    private String title;

    private String content;

    private String memberEmail;

    private boolean complete;

    private LocalDate dueDate;

    private String imageFile;

    public MongoTodoListDTO(String id, String title, String content, String memberEmail, boolean complete, LocalDate dueDate,
                       String imageFile) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberEmail = memberEmail;
        this.complete = complete;
        this.dueDate = dueDate;
        this.imageFile = imageFile;
    }
}
