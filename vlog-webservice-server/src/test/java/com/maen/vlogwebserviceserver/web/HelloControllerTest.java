package com.maen.vlogwebserviceserver.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maen.vlogwebserviceserver.domain.Hello;
import com.maen.vlogwebserviceserver.domain.HelloRepository;
import com.maen.vlogwebserviceserver.web.dto.HelloRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private HelloRepository helloRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello_등록한다() throws Exception {
        String name = "이름";
        HelloRequestDto helloRequestDto = new HelloRequestDto(name);
        String url = "http://localhost:8080/api/hello";

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        //본문(body) 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열을 JSON으로 변환
                        .content(new ObjectMapper().writeValueAsString(helloRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void hello_불러온다() throws Exception {
        String name = "이름";
        helloRepository.save(new Hello(name));

        String url = "http://localhost:8080/api/hello/all";

        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());


    }


}
