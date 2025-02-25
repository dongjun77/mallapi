package com.project.mallapi.repository.search;

import com.project.mallapi.domain.Product;
import com.project.mallapi.domain.QProduct;
import com.project.mallapi.domain.QProductImage;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import com.project.mallapi.dto.ProductDTO;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
@RequiredArgsConstructor // ✅ JPAQueryFactory를 생성자로 주입받음
public class ProductSearchImpl implements ProductSearch {

    private final JPAQueryFactory queryFactory; // ✅ JPAQueryFactory 주입

    @Override
    public PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO) {

        log.info("------------------------searchList--------------------");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        // ✅ Step 1: Product ID만 페이징 처리
        List<Long> productIds = queryFactory
                .select(product.pno)
                .from(product)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // ✅ Step 2: fetchJoin()을 사용하여 데이터 조회
        List<Tuple> productList = queryFactory
                .select(product, productImage)
                .from(product)
                .leftJoin(product.imageList, productImage).fetchJoin()
                .where(product.pno.in(productIds)) // ✅ Step 1에서 가져온 ID 목록으로 필터링
                .fetch();

        // ✅ 중복 제거한 Count 쿼리
        long count = Optional.ofNullable(queryFactory
                .select(product.pno.countDistinct())
                .from(product)
                .fetchOne()).orElse(0L);

        // ✅ PageResponseDTO 반환 로직 추가
        return PageResponseDTO.<ProductDTO>withAll()
                .dtoList(productList.stream()
                        .map(tuple -> ProductDTO.builder()
                                .pno(tuple.get(product).getPno())
                                .pname(tuple.get(product).getPname())
                                .pdesc(tuple.get(product).getPdesc())
                                .price(tuple.get(product).getPrice())
                                .uploadFileNames(List.of(tuple.get(productImage).getFileName()))
                                .build())
                        .toList())
                .pageRequestDTO(pageRequestDTO)
                .totalCount(count)
                .build();
    }
}
