package org.zerock.mallapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.ProductDTO;
import org.zerock.mallapi.service.ProductService;
import org.zerock.mallapi.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/products")
public class ProductController {
    
    // 신기하게 네이밍이 service가 아니라 util이다
    private final CustomFileUtil fileUtil;

    // 알고보니 서비스가 있었음, util은 파일 저장 기능이었던것
    private final ProductService productService;

    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO){

        log.info("list............" + pageRequestDTO);

        return productService.getList(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, String> register(ProductDTO productDTO){

        log.info("register: " + productDTO);

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = fileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames);

        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        
        return fileUtil.getFile(fileName);
    }
}
