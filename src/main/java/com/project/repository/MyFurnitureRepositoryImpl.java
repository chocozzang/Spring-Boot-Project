package com.project.repository;

import com.project.dto.FurnitureSearchDTO;
import com.project.dto.MainFurnitureDTO;
import com.project.dto.QMainFurnitureDTO;
import com.project.entity.FurnitureEntity;
import com.project.entity.QFurnitureEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

@Log4j2
public class MyFurnitureRepositoryImpl implements MyFurnitureRepository{

    private JPAQueryFactory jpaQueryFactory;

    public MyFurnitureRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("furnitureName", searchBy)) {
            log.info("searchbylike");
            return QFurnitureEntity.furnitureEntity.furnitureName.like("%" + searchQuery + "%");
        }
        return null;
    }
    public BooleanExpression searchByLike(String searchQuery) {
        log.info("==============" + searchQuery + "==================");
        searchQuery = searchQuery.toUpperCase();
        searchQuery = searchQuery.strip();
        log.info(StringUtils.isEmpty(searchQuery));
        return StringUtils.isEmpty(searchQuery) ? null : QFurnitureEntity.furnitureEntity.furnitureType.like("%" + searchQuery + "%");
    }

    @Override
    public Page<FurnitureEntity> getAdminFurniturePage(FurnitureSearchDTO furnitureSearchDTO, Pageable pageable) {
        QueryResults<FurnitureEntity> result = jpaQueryFactory.selectFrom(QFurnitureEntity.furnitureEntity)
                .where(searchByLike(furnitureSearchDTO.getSearchBy(), furnitureSearchDTO.getSearchQuery()))
                .orderBy(QFurnitureEntity.furnitureEntity.fid.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<FurnitureEntity> content = result.getResults();
        Long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
    @Override
    public Page<MainFurnitureDTO> getMainFurniturePage(String findwhat, Pageable pageable) {
        QFurnitureEntity qFurnitureEntity = QFurnitureEntity.furnitureEntity;

        QueryResults<MainFurnitureDTO> results = jpaQueryFactory
                .select(
                        new QMainFurnitureDTO(
                                qFurnitureEntity.fid,
                                qFurnitureEntity.furnitureName,
                                qFurnitureEntity.furnitureType,
                                qFurnitureEntity.furnitureStock,
                                qFurnitureEntity.furniturePrice,
                                qFurnitureEntity.furnitureHeight,
                                qFurnitureEntity.furnitureLength,
                                qFurnitureEntity.furnitureWidth,
                                qFurnitureEntity.fileUrl,
                                qFurnitureEntity.hits)
                )
                .from(qFurnitureEntity)
                .where(searchByLike(findwhat))
                .orderBy(qFurnitureEntity.fid.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        log.info(results.isEmpty());
        log.info(results.getResults());
        List<MainFurnitureDTO> content = results.getResults();
        long total= results.getTotal();
        return new PageImpl<>(content,pageable,total);

    }


}
