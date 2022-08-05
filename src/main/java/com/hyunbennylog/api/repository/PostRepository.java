package com.hyunbennylog.api.repository;

import com.hyunbennylog.api.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{
    /**
     *  현재 라이브러리 추가한 방식으로 하면
     *     implementation 'com.querydsl:querydsl-core'
     *     implementation 'com.querydsl:querydsl-jpa'
     *     annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jpa" // querydsl JPAAnnotationProcessor 사용 지정
     *     annotationProcessor 'jakarta.persistence:jakarta.persistence-api'
     *     annotationProcessor 'jakarta.annotation:jakarta.annotation-api'
     *     => gradle -> build -> classes 실행
     *          -> 프로젝트에서 build -> generated -> source -> annotationProcessor 폴더에 생성됨
     *   이전 방식으로 하면
     *     => gradle -> other -> compileJava 실행 ->
     */



}
