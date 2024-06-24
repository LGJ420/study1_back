package org.zerock.mallapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.dto.TodoDTO;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class TodoServiceImpl implements TodoService{

    @Override
    public Long register(TodoDTO todoDTO) {
        
       log.info(".........."); 

       return null;
    }
    
}
