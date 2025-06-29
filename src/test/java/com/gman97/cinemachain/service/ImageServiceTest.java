package com.gman97.cinemachain.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ImageServiceTest {

    @Spy
    private ImageService imageService;
    private final String BUCKET = "C:/Users/Georgy/IdeaProjects/CinemaChain/testDir";
    private final String IMAGE_NAME = "testImage.jpg";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(imageService, "bucket", BUCKET);
    }

    @Test
    @Order(1)
    void uploadImage() {

        imageService.uploadImage(IMAGE_NAME, InputStream.nullInputStream());

        assertTrue(new File(BUCKET, IMAGE_NAME).exists());
    }

    @Test
    @Order(2)
    void givenNameOfExistsImage_whenGet_thenReturnOptionalOfByteArray() {

        var actual = imageService.get(IMAGE_NAME);

        assertTrue(actual.isPresent());
    }

    @Test
    @Order(3)
    void givenNameOfNoExistsImage_whenGet_thenReturnEmptyOptional() {

        var actual = imageService.get("noExistsImage.jpg");

        assertTrue(actual.isEmpty());
    }

    @AfterAll
    void clean() {
        try {
            Files.deleteIfExists(Path.of(BUCKET, IMAGE_NAME));
            Files.deleteIfExists(Path.of(BUCKET));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}