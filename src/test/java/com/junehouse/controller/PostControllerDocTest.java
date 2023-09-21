package com.junehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junehouse.config.CustomAnnotation;
import com.junehouse.domain.Post;
import com.junehouse.repository.MemberRepository;
import com.junehouse.repository.PostRepository;
import com.junehouse.request.PostCreate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.june.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void clean() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    /*@BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }*/

    @Test
    @DisplayName("글 조회")
    void test1() throws Exception {
        Post post = Post.builder()
                .title("글 저장")
                .content("내용내용")
                .build();
        postRepository.save(post);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", 1L)
                    .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(
                        document("post-inquire", pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("게시글 ID"),
                                        fieldWithPath("title").description("제목"),
                                        fieldWithPath("content").description("내용")
                                )
                        ));
    }

    @Test
    @DisplayName("글 등록")
    @CustomAnnotation
    void test2() throws Exception {
        PostCreate request = PostCreate.builder()
                .title("글 등록 테스트")
                .content("내용내용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(
                        document("post-create", requestFields(
                                fieldWithPath("title").description("제목").attributes(Attributes.key("constraint").value("필수입력입니다.")),
                                fieldWithPath("content").description("내용").optional()
                                )
                        ));
    }
}
