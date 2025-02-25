package com.project.mallapi.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class TodoListDTO {

    private Long tno;

    private String title;

    private String content;

    private String memberEmail;

    private boolean complete;

    private LocalDate dueDate;

    private String imageFile;

    public TodoListDTO(Long tno, String title, String content, String memberEmail, boolean complete, LocalDate dueDate,
                       String imageFile) {
        this.tno = tno;
        this.title = title;
        this.content = content;
        this.memberEmail = memberEmail;
        this.complete = complete;
        this.dueDate = dueDate;
        this.imageFile = imageFile;
    }
}
