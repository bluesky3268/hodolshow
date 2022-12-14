package com.hyunbennylog.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.repository.PostRepository;
import com.hyunbennylog.api.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.hyunbenny.com", uriPort = 443)
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

//    @BeforeEach
//    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation))
//                .build();
//    }

    @Test
    @DisplayName("????????? ?????? ?????? restdoc")
    void getPost() throws Exception {
        //given
        Post request = Post.builder()
                .title("???????????????1234567890")
                .content("????????? ??????")
                .build();

        postRepository.save(request);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", request.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("postInquiry",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("postId").description("????????? ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("????????? ID"),
                                PayloadDocumentation.fieldWithPath("title").description("????????? ??????"),
                                PayloadDocumentation.fieldWithPath("content").description("????????? ??????")
                        )
                        ))
        ;
    }

    @Test
    @DisplayName("????????? ?????? restdoc")
    void registPost() throws Exception {
        //given
       PostCreate postCreate = PostCreate.builder()
               .title("???????????????")
               .content("???????????????")
               .build();

        String postCreateToJson = objectMapper.writeValueAsString(postCreate);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postCreateToJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("postRegist",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("????????? ??????").attributes(key("constraint").value("Hello World?????? ??????")),
                                PayloadDocumentation.fieldWithPath("content").description("????????? ??????").optional()
                        )
                ))
        ;
    }
}
