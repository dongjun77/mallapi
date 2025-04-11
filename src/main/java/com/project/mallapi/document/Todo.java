package com.project.mallapi.document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tbl_todo")
public class Todo {

    @Id
    private String id;

    private String title;

    private String content;

    private boolean complete;

//    @Indexed
    private LocalDate dueDate;

    @Builder.Default
    private List<TodoImage> imageList = new ArrayList<>();

    private String memberEmail;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void addMember(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    // 이미지 추가
    public void addImage(TodoImage image) {
        image.setOrd(imageList.size());
        imageList.add(image);
    }

    public void addImageString(String fileName) {
        TodoImage todoImage = TodoImage.builder()
                .fileName(fileName)
                .build();

        addImage(todoImage);
    }

    // 이미지 전체 삭제
    public void clearImageList() {
        this.imageList.clear();
    }

}

