package com.pickle.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class HomeControllerTest {
    @Autowired HomeController homeController;
    @Test
    void home() {
        assertThat(homeController.home()).isEqualTo("Hello Pickle!");
    }
}