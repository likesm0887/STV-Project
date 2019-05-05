package adapter.parser;

import entity.TestData;
import entity.TestDatum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Integer> mergedRowNumbers = getMergedRowNumbers(sheet);
        Iterator<Row> it = sheet.iterator();
        it.next();
        it.forEachRemaining(row -> {
            if (!mergedRowNumbers.contains(row.getRowNum()) && !isRowEmpty(row)) {
                TestDatum datum = createTestDatum(row);
                sheetTestData.put(row.getCell(0).getStringCellValue(), datum);
            }
        });
        testData.addSheetTestData(sheet.getSheetName(), sheetTestData);
    }

    private TestDatum createTestDatum(Row row) {
        if (row.getCell(0) != null && row.getCell(1) == null)
            throw new RuntimeException("The xPath cells of test data should not be empty");

        return new TestDatum(
            row.getCell(0).getStringCellValue(),
            row.getCell(1).getStringCellValue(),
            row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null
        );
    }

    public List<Integer> getMergedRowNumbers(Sheet sheet) {
        List<CellRangeAddress> mergedRegnios = sheet.getMergedRegions();
        return mergedRegnios.stream()
                .filter(rangeAddress -> rangeAddress.getFirstColumn() <= 2)
                .map(CellRangeAddress::getFirstRow)
                .collect(Collectors.toList());
    }

    public static boolean isRowEmpty(Row row) {
        for (Cell each : row) {
            if (each != null && each.getCellType() != CellType.BLANK)
                return false;
        }
        return true;
    }

    public TestData getTestData() {
        return testData;
    }
}
