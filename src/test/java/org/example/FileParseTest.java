package org.example;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.example.model.Teacher;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileParseTest {
    ClassLoader cl = FileParseTest.class.getClassLoader();

    @Test

    void pdfTest() throws Exception {
        open ("https://junit.org/junit5/docs/current/user-guide/");
        File downloadedFile = $("a[href*='junit-user-guide-5.9.1.pdf']").download();
        PDF pdf = new PDF(downloadedFile);
        assertThat(pdf.author).contains("Sam Brannen");
        System.out.println("");
    }
    @Test
    void xlsTest() throws Exception {
        InputStream is = cl.getResourceAsStream("pairwise33.xlsx");
        XLS xls = new XLS(is);
        assertThat(xls.excel.getSheetAt(0)
                .getRow(1)
                .getCell(1)
                .getStringCellValue()
        ).isEqualTo("нет");
    }
    @Test
    void csvTest() throws Exception {
        InputStream is = cl.getResourceAsStream("qa_guru.csv");
        CSVReader reader = new CSVReader(new InputStreamReader(is));
        List<String[]> content = reader.readAll();
        String[] row = content.get(1);
        assertThat(row[0]).isEqualTo("Tuchs");
        assertThat(row[1]).isEqualTo("JUnit 5");
    }
    @Test
    void zipTest() throws Exception {
        InputStream is = cl.getResourceAsStream("testzip.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            // String entryName =Entry.getName (); для дз + еще код

            System.out.println(entry.getName());

        }
    }
    @Test
    void jsonTest () {
    InputStream is = cl.getResourceAsStream("teacher.json");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
        assertThat(jsonObject.get("name").getAsString()).isEqualTo("Dmitrii");
        assertThat(jsonObject.get("isGoodTeacher").getAsBoolean()).isTrue();
        assertThat(jsonObject.get("passport").getAsJsonObject().get("number").getAsInt()).isEqualTo(123456);

    }

 @Test
    void jsonTestWithModel () {
    InputStream is = cl.getResourceAsStream("teacher.json");
        Gson gson = new Gson();
        Teacher teacher = gson.fromJson(new InputStreamReader(is), Teacher.class);
        assertThat(teacher.name).isEqualTo("Dmitrii");
        assertThat(teacher.isGoodTeacher).isTrue();
        assertThat(teacher.passport.number).isEqualTo(123456);
    }
}

