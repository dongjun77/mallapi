package com.project.mallapi.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MongoTodoListDTO {

    private String id;

    private String title;

    private String content;

    private String memberEmail;

    private boolean complete;

    private LocalDate dueDate;

    private String imageFile;

}
