package com.project.mallapi.service;

import com.project.mallapi.domain.Member;
import com.project.mallapi.domain.Todo;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import com.project.mallapi.dto.TodoDTO;
import com.project.mallapi.repository.MemberRepository;
import com.project.mallapi.repository.TodoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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
    public void modify(TodoDTO dto) {

        Optional<Todo> result = todoRepository.findById(dto.getTno());

        Todo todo = result.orElseThrow();

        todo.changeTitle(dto.getTitle());
        todo.changeContent(dto.getContent());
        todo.changeComplete(dto.isComplete());
        todo.changeDueDate(dto.getDueDate());

        todoRepository.save(todo);

    }

    @Override
    public void remove(Long tno) {

        todoRepository.deleteById(tno);

    }

//    @Override
//    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {
//
//        String email = "user1@aaa.com";
//
//        // JPA
//        Page<Todo> result = todoRepository.search(pageRequestDTO);
//
//        //Todo List => TodoDto List
//        List<TodoDTO> dtoList = result
//                .get()
//                .map(todo -> entityToDTO(todo)).collect(Collectors.toList());
//
//        PageResponseDTO<TodoDTO> responseDTO =
//                PageResponseDTO.<TodoDTO>withAll()
//                        .dtoList(dtoList)
//                        .pageRequestDTO(pageRequestDTO)
//                        .totalCount(result.getTotalElements())
//                        .build();
//
//        return responseDTO;
//    }
}
