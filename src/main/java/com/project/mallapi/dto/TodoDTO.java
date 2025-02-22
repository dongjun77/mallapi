package com.project.mallapi.dto;


import com.project.mallapi.domain.Member;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class TodoDTO {

    private Long tno;

    private String title;

    private String content;

    private String memberEmail;

    private boolean complete;

    private LocalDate dueDate;

    private String imageFile;

    @QueryProjection
    public TodoDTO(Long tno, String title, String content, String memberEmail, boolean complete,
                   LocalDate dueDate, String imageFile) {
        this.tno = tno;
        this.title = title;
        this.content = content;
        this.memberEmail = memberEmail;
        this.imageFile = imageFile;
        this.complete = complete;
        this.dueDate = dueDate;
    }
}
