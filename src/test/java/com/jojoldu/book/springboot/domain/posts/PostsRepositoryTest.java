package com.jojoldu.book.springboot.domain.posts;

import jdk.vm.ci.meta.Local;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
    // 보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용합니다.
    // 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어
    // 다음 테스트 실행 시 테스트가 실패할 수 있습니다.
    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글저장_불러오기")
    public void 게시글저장_불러오기() {

        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // 테이블 posts에 insert/update 쿼리를 실행합니다.
        // id 값이 있다면 update , 없다면 insert 쿼리가 실행됩니다.
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jojoldu@gmail.com")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll(); // 테이블 posts에 있는 모든 데이터를 조회해오는 메소드입니다.

        // then
        Posts posts = postsList.get(0);
        assertEquals(posts.getTitle() , title);
        assertEquals(posts.getContent() , content);
    }

    @Test
    public void BaseTimeEntity_등록() {

        // given
        LocalDateTime now = LocalDateTime.of(2019,12,7,7,24);
        postsRepository.save(Posts.builder()
                .title("타이틀")
                .content("마이러브")
                .author("정경화")
                .build());

        // when
        List<Posts> all = postsRepository.findAll();

        // then
        Posts posts = all.get(0);

        System.out.println(">>>>>>>>>>>>>>>> createDate="+posts.getCreateDate()
        +", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}