package com.project.mallapi.controller;

import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import com.project.mallapi.dto.TodoDTO;
import com.project.mallapi.dto.TodoListDTO;
import com.project.mallapi.service.TodoService;
import com.project.mallapi.util.TodoFileUtil;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoFileUtil todoFileUtil;

    private final TodoService todoService;

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName) {
        log.info("viewFileGet=================");
        log.info("fileName:" + fileName);
        return todoFileUtil.getFile(fileName);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable("tno") Long tno) {
        return todoService.get(tno);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/list")
    public PageResponseDTO<TodoListDTO> list(PageRequestDTO pageRequestDTO, Principal principal) {

        String memberEmail = principal.getName();

        return todoService.getList(pageRequestDTO, memberEmail);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/")
    public Map<String, Long> register(TodoDTO todoDTO, Principal principal) {

        String email = principal.getName();

        todoDTO.setMemberEmail(email);

        log.info("todoDTO: " + todoDTO);

        List<MultipartFile> files = todoDTO.getFiles();

        List<String> uploadFileNames = todoFileUtil.saveFiles(files);

        todoDTO.setUploadFileNames(uploadFileNames);

        Long tno = todoService.register(todoDTO);

        return Map.of("result", tno);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable(name = "tno") Long tno,
                                      TodoDTO todoDTO,
                                      Principal principal){
        String email = principal.getName();

        String todoWriter = todoService.getTodoWriter(tno);

        if(!todoWriter.equals(email)){
            throw new AccessDeniedException("권한이 없습니다.");
        }

        log.info("todoDTO: " + todoDTO);

        TodoDTO oldTodoDTO = todoService.get(tno);
        List<String> oldFileNames = oldTodoDTO.getUploadFileNames();

        // 입력받은 파일들 저장하기
        List<MultipartFile> files = todoDTO.getFiles();
        List<String> currentUploadFileNames = todoFileUtil.saveFiles(files);

        List<String> uploadedFileNames = todoDTO.getUploadFileNames();

        if (!currentUploadFileNames.isEmpty()) {
            uploadedFileNames.addAll(currentUploadFileNames);
        }

        todoService.modify(todoDTO);

        // 디렉토리에 파일 삭제하기
        if(oldFileNames != null && oldFileNames.size()>0) {
            List<String> removeFiles = oldFileNames.stream()
                    .filter(fileName -> uploadedFileNames.indexOf(fileName) == -1 )
                    .collect(Collectors.toList());

            todoFileUtil.deleteFiles(removeFiles);
        }

        return Map.of("RESULT","SUCCESS");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable("tno") Long tno,
                                      Principal principal) {

        String email = principal.getName();

        String todoWriter = todoService.getTodoWriter(tno);

        if(!todoWriter.equals(email)){
            throw new AccessDeniedException("권한이 없습니다.");
        }

        List<String> oldFielNames = todoService.get(tno).getUploadFileNames();

        todoService.remove(tno, email);

        todoFileUtil.deleteFiles(oldFielNames);

        return Map.of("RESULT","SUCCESS");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/findall")
    public List<TodoListDTO> findAllTodoList(Principal principal) {

        String memberEmail = principal.getName();
        return todoService.findAllTodoList(memberEmail);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/searchall")
    public List<TodoListDTO> searchAllTodoList(Principal principal) {

        String memberEmail = principal.getName();
        return todoService.searchAllTodoList(memberEmail);
    }
}
