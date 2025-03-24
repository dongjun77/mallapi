package com.project.mallapi.controller;

import com.project.mallapi.document.Todo;
import com.project.mallapi.dto.MongoTodoListDTO;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import com.project.mallapi.dto.TodoListDTO;
import com.project.mallapi.service.MongoTodoService;
import com.project.mallapi.util.MongoTodoFileUtil;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/mongotodo")
public class MongoTodoController {

    private final MongoTodoFileUtil mongoTodoFileUtil;

    private final MongoTodoService mongoTodoService;

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName) {
        log.info("viewFileGet=================");
        log.info("fileName:" + fileName);
        return mongoTodoFileUtil.getFile(fileName);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/findall")
    public List<MongoTodoListDTO> findAllMongoTodoList(Principal principal) {

        String memberEmail = principal.getName();
        return mongoTodoService.findAllMongoTodoList(memberEmail);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/list")
    public PageResponseDTO<MongoTodoListDTO> list(PageRequestDTO pageRequestDTO, Principal principal) {

        String memberEmail = principal.getName();

        log.info(pageRequestDTO);

        return mongoTodoService.pageMongoTodoList(memberEmail, pageRequestDTO);
    }

}
