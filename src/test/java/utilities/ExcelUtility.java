package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook wb;
    public XSSFSheet ws;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle style;
    private String path;

    public ExcelUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(sheetName);
        int rowcount = (ws != null) ? ws.getLastRowNum() : 0;
        wb.close();
        fi.close();
        return rowcount;
    }

    public int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(sheetName);
        row = (ws != null) ? ws.getRow(rownum) : null;
        int cellcount = (row != null) ? row.getLastCellNum() : 0;
        wb.close();
        fi.close();
        return cellcount;
    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(sheetName);
        row = (ws != null) ? ws.getRow(rownum) : null;
        cell = (row != null) ? row.getCell(colnum) : null;

        DataFormatter formatter = new DataFormatter();
        String data;
        try {
            data = formatter.formatCellValue(cell);
        } catch (Exception e) {
            data = "";
        }

        wb.close();
        fi.close();
        return data;
    }

    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        File xlfile = new File(path);
        if (!xlfile.exists()) {
            wb = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            wb.write(fo);
            fo.close();
            wb.close();
        }

        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        fi.close(); // Close read stream before modifying/writing

        if (wb.getSheetIndex(sheetName) == -1) {
            wb.createSheet(sheetName);
        }
        ws = wb.getSheet(sheetName);

        if (ws.getRow(rownum) == null) {
            ws.createRow(rownum);
        }
        row = ws.getRow(rownum);

        cell = row.createCell(colnum);
        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        wb.write(fo);
        wb.close();
        fo.close();
    }

    public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
        fillColor(sheetName, rownum, colnum, IndexedColors.GREEN.getIndex());
    }

    public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
        fillColor(sheetName, rownum, colnum, IndexedColors.RED.getIndex());
    }

    private void fillColor(String sheetName, int rownum, int colnum, short colorIndex) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        fi.close();

        ws = wb.getSheet(sheetName);
        row = (ws != null) ? ws.getRow(rownum) : null;
        cell = (row != null) ? row.getCell(colnum) : null;

        if (cell != null) {
            style = wb.createCellStyle();
            style.setFillForegroundColor(colorIndex);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);

            fo = new FileOutputStream(path);
            wb.write(fo);
            fo.close();
        }
        wb.close();
    }
}