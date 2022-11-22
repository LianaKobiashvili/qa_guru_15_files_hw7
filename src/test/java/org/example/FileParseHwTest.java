package org.example;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.example.model.Student;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FileParseHwTest {

    ClassLoader cl = FileParseHwTest.class.getClassLoader();
    @Test
    void jsonMyTest () throws Exception {

        InputStream is = cl.getResourceAsStream("student.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.readValue(is, Student.class);
        assertThat(student.firstname).isEqualTo("Liana");
        assertThat(student.lastname).isEqualTo("Kobiashvili");
        assertThat(student.age).isEqualTo(30);
        assertThat(student.pets).contains("cat","dog");
        assertThat(student.friends.number).isEqualTo(2);
        assertThat(student.friends.girlsname).isEqualTo("Gala");
        assertThat(student.friends.boysname).isEqualTo("Danil");
    }
    @Test
    void zipReadTest() throws Exception {
        ZipFile zipFile = new ZipFile("src/test/resources/testzip.zip");
        try (
                InputStream is = cl.getResourceAsStream("testzip.zip");
                ZipInputStream zis = new ZipInputStream(is)
        )
        {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                switch (entryName) {
                    case "addresses.csv":
                        try (InputStream inputStream = zipFile.getInputStream(entry)) {
                            CSVReader csv = new CSVReader(new InputStreamReader(inputStream));
                            List<String[]> content = csv.readAll();
                            String[] row = content.get(1);
                            assertThat(row[0]).isEqualTo("Jack");
                        }
                        break;
                    case "sample3.xlsx":
                        try (InputStream inputStream = zipFile.getInputStream(entry)) {
                            XLS xls = new XLS(inputStream);
                            assertThat(
                                    xls.excel.getSheetAt(0)
                                            .getRow(0)
                                            .getCell(1)
                                            .getStringCellValue()
                            ).isEqualTo("Months");
                        }
                        break;
                    case "obrazec.pdf":
                        try (InputStream inputStream = zipFile.getInputStream(entry)) {
                            PDF pdf = new PDF(inputStream);
                            assertThat(pdf.title).contains("ЗАЯВЛЕНИЕ О ВЫДАЧЕ ПАСПОРТА");
                        }
                        break;
                        }
                }
            }
        }
    }
