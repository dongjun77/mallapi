package com.project.mallapi.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTodoImage is a Querydsl query type for TodoImage
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTodoImage extends BeanPath<TodoImage> {

    private static final long serialVersionUID = 1479985493L;

    public static final QTodoImage todoImage = new QTodoImage("todoImage");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Integer> ord = createNumber("ord", Integer.class);

    public QTodoImage(String variable) {
        super(TodoImage.class, forVariable(variable));
    }

    public QTodoImage(Path<? extends TodoImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTodoImage(PathMetadata metadata) {
        super(TodoImage.class, metadata);
    }

}

