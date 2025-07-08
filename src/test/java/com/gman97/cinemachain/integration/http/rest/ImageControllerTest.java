package com.gman97.cinemachain.integration.http.rest;

import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@RequiredArgsConstructor
class ImageControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ImageService imageService;

    @Value("${app.image.bucket}")
    private String BUCKET;
    private final String IMAGE_NAME = "testImage.jpg";

    @BeforeEach
    void setUp() throws IOException {
        var image = new MockMultipartFile("poster", IMAGE_NAME,
                "image/jpg", new byte[] {1, 2, 3});
        imageService.uploadImage(image.getOriginalFilename(), image.getInputStream());
    }

    @Test
    void getImageByFileName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/images/{fileName}", IMAGE_NAME))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().bytes(new byte[] {1, 2, 3}),
                        header().exists(HttpHeaders.CONTENT_TYPE)
                );
    }

    @Test
    void getImageByFileNameNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/images/{fileName}", "NotExists.jpj"))
                .andExpectAll(
                        status().is4xxClientError(),
                        content().bytes(new byte[0]),
                        header().doesNotExist(HttpHeaders.CONTENT_TYPE)
                );
    }

    @AfterEach
    void clean() {
        try {
            Files.deleteIfExists(Path.of(BUCKET, IMAGE_NAME));
            Files.deleteIfExists(Path.of(BUCKET));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}