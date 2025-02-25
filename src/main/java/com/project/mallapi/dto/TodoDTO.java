package com.project.mallapi.dto;


import com.project.mallapi.domain.Member;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long tno;

    private String title;

    private String content;

    private String memberEmail;

    private boolean complete;

    private LocalDate dueDate;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>(); //

    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>(); // 파일의 이름들

}
