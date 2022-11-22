package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelenideFileTest {
//static {
//    Configuration.fileDownload = FileDownloadMode.PROXY;
//}
    @Test
    void selenideFileDownloadTest() throws Exception {
        open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloadedFile = $("#raw-url").download();
       // String contents = FileUtils.readFileToString (downloadedFile, StandardCharsets.UTF_8); идентичен try

       try (InputStream is = new FileInputStream(downloadedFile)) {
           byte [] fileSource = is.readAllBytes();
           String fileContent = new String(fileSource, StandardCharsets.UTF_8);
           assertThat(fileContent).contains("This repository is the home of the next generation of JUnit");
       }
    }
    @Test
    void uploadFile() throws Exception {
        open("https://fineuploader.com/demos.html");
        $ ("input[type='file']").uploadFromClasspath("folder/kot.jpg");
        $ ("div.qq-file-info").shouldHave(text("kot.jpg"));


    }
}
