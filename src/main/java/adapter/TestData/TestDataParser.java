package adapter.TestData;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestDataParser {
    private String xlsxFilePath;
    private TestData testData;

    public TestDataParser(String xlsxFilePath) {
        this.xlsxFilePath = xlsxFilePath;
        testData = new TestData();
    }

    public void parse() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(xlsxFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        workbook.iterator().forEachRemaining(this::parseSheet);
    }

    private void parseSheet(Sheet sheet) {
        Map<String, TestDatum> sheetTestData = new HashMap<>();
        Iterator<Row> it = sheet.iterator();
        it.next();
        it.forEachRemaining(row -> {
            TestDatum datum = createTestDatum(row);
            sheetTestData.put(row.getCell(0).getStringCellValue(), datum);
        });
        testData.addSheetTestData(sheet.getSheetName() ,sheetTestData);
    }

    private TestDatum createTestDatum(Row row) {
        if (row.getCell(0) == null || row.getCell(1) == null)
            throw new RuntimeException("The component and xPath cells of test data should not be empty");

        return new TestDatum(
            row.getCell(0).getStringCellValue(),
            row.getCell(1).getStringCellValue(),
            row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null
        );
    }

    public TestData getTestData() {
        return testData;
    }
}
