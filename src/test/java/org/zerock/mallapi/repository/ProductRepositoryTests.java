package org.zerock.mallapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.domain.Product;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {
    
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testInsert(){

        for(int i = 0; i < 10; i++){

            Product product = Product.builder()
                .pname("상품" + i)
                .price(100*i)
                .pdesc("상품설명" + i)
                .build();

            product.addImageString(UUID.randomUUID().toString()+"_"+"IMAGE1.jpg");
            product.addImageString(UUID.randomUUID().toString()+"_"+"IMAGE2.jpg");

            productRepository.save(product);

            log.info("--------------------");
        }
    }

    @Transactional
    @Test
    public void testRead(){

        Long pno = 1L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product);
        log.info(product.getImageList());
    }

    /*
     * testRead()의 2버전
     * selectOne함수를 만들어놓고 @EntityGraph붙이면
     * 조인된 쿼리가 생성되기 때문에 트랜잭션이 필요없어짐
     */
    @Test
    public void testRead2(){

        Long pno = 1L;

        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();

        log.info(product);
        log.info(product.getImageList());
    }

    @Commit
    @Transactional
    @Test
    public void testDelete(){

        Long pno = 2L;

        productRepository.updateToDelete(pno, true);
    }
}
