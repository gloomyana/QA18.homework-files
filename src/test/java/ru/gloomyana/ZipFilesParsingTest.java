package ru.gloomyana;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class ZipFilesParsingTest {
    static private final ClassLoader cl = ZipFilesParsingTest.class.getClassLoader();

    static String zipName = "files.zip";

    @BeforeAll
    static void extractFilesFromZipSetUp() throws ZipException, URISyntaxException {
        extractFilesFromZip(zipName);
    }

    static void extractFilesFromZip(String zipName) throws ZipException, URISyntaxException {

        URL resource = cl.getResource(zipName);
        assert resource != null;
        File zipFIle = Paths.get(resource.toURI()).toFile();
        String extractionPath = zipFIle.getParent();
        ZipFile filesZip = new ZipFile(zipFIle);

        filesZip.extractAll(extractionPath);
    }

    @Test
    @DisplayName("Reading extracted PDF file")
    void parseExtractedPdfTest() throws Exception {
        URL pdfURL = cl.getResource("PostgreSQL-Cheat-Sheet.pdf");
        assert pdfURL != null;
        PDF pdf = new PDF(pdfURL);

        Assertions.assertEquals("PowerPoint Presentation", pdf.title);
        Assertions.assertEquals(3, pdf.numberOfPages);
        Assertions.assertEquals("Microsoft® PowerPoint® 2016", pdf.creator);
        Assertions.assertEquals("Duy Pham", pdf.author);
        Assertions.assertFalse(pdf.encrypted);
    }

    @Test
    @DisplayName("Reading extracted XLS file")
    void parseExtractedXlsTest() throws Exception {
        URL xlsURL = cl.getResource("menu.xlsx");
        assert xlsURL != null;
        XLS xls = new XLS(xlsURL);

        Assertions.assertEquals(xls.excel.getSheetAt(0).getRow(1).getCell(0)
                .getStringCellValue(), "ХОЛОДНЫЕ ЗАКУСКИ");
    }

    @Test
    @DisplayName("Reading extracted CSV file")
    void parseExtractedCsvTest() throws IOException, CsvException {
        List<String[]> content;
        try (InputStream is = cl.getResourceAsStream("schedule.csv")) {
            assert is != null;
            try (InputStreamReader isr = new InputStreamReader(is)) {
                CSVReader csvReader = new CSVReader(isr);
                content = csvReader.readAll();
                Assertions.assertArrayEquals(new String[]{"4", "Selenide #1. Алексей Виноградов", "2023-02-21"},
                        content.get(3));
            }
        }
    }
}
