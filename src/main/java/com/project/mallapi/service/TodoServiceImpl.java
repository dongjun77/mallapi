package com.project.mallapi.service;

import com.project.mallapi.domain.Member;
import com.project.mallapi.domain.Todo;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import com.project.mallapi.dto.TodoDTO;
import com.project.mallapi.dto.TodoListDTO;
import com.project.mallapi.repository.MemberRepository;
import com.project.mallapi.repository.TodoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Override
    public TodoDTO get(Long tno) {

        Optional<Todo> result = todoRepository.selectOneWithImageList(tno);

        Todo todo = result.orElseThrow();

        return entityToDTO(todo);
    }

    @Override
    public Long register(TodoDTO todoDTO) {

        Member member = memberRepository.findById(todoDTO.getMemberEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Todo todo = dtoToEntity(todoDTO, member);

        Todo result = todoRepository.save(todo);

        return result.getTno();
    }

    @Override
    public void modify(TodoDTO todoDTO) {

        Member member = memberRepository.findById(todoDTO.getMemberEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Optional<Todo> result = todoRepository.findById(todoDTO.getTno());

        Todo todo = result.orElseThrow();

        todo.changeTitle(todoDTO.getTitle());
        todo.changeContent(todoDTO.getContent());
        todo.changeComplete(todoDTO.isComplete());
        todo.changeDueDate(todoDTO.getDueDate());
        todo.addMember(member);

        // 이미지 처리
        List<String> uploadFileNames = todoDTO.getUploadFileNames();

        todo.clearImageList();
        if (uploadFileNames != null && uploadFileNames.size() > 0) {
            uploadFileNames.stream().forEach(fileName -> {
                todo.addImageString(fileName);
            });
        }

        todoRepository.save(todo);
    }

    @Override
    public void remove(Long tno) {

        todoRepository.deleteById(tno);

    }

    @Override
    public PageResponseDTO<TodoListDTO> getList(PageRequestDTO pageRequestDTO, String email) {

        Page<TodoListDTO> result = todoRepository.search(email, pageRequestDTO);

        List<TodoListDTO> dtoList = result.getContent();

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<TodoListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
