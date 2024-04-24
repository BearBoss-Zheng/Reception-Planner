package com.example.receptionplanner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.receptionplanner.domain.ReceptionRecord;
import com.example.receptionplanner.mapper.ReceptionRecordMapper;
import com.example.receptionplanner.service.ReceptionRecordService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceptionRecordServiceImpl extends ServiceImpl<ReceptionRecordMapper, ReceptionRecord> implements ReceptionRecordService {
    private final ReceptionRecordMapper recordMapper;

    @Value("${file.save.path}")
    private String savePath;

    public boolean saveReceptionRecord(ReceptionRecord record) {
        /*
            接待机会是否提交（是/否）
            是，则有提交时间
            接待总结是否提交（是/否）
            是，则有提交时间
         */
        if ((record.getOpportunitySubmitted() && record.getOpportunitySubmitTime() == null)
                ||
            (record.getSummarySubmitted() && record.getSummarySubmitTime() == null)){
            return false;
        }

        return recordMapper.insert(record) > 0;
    }

    public void exportRecordsToExcel() throws IOException {
        List<ReceptionRecord> records = recordMapper.selectList(null);

        LocalDate currentDate = LocalDate.now();
        String currentYearMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy年M月"));
        String fileName = "reception_records_" + currentYearMonth + ".xlsx";
        String filePath = Paths.get(savePath, fileName).toString();

        try (Workbook workbook = new XSSFWorkbook();
             // 设置为 false，覆盖原文件
             FileOutputStream fileOut = new FileOutputStream(filePath, false)) {
            Sheet sheet = workbook.createSheet("接待记录");

            // 创建标题行
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(currentYearMonth + "来访接待台账（营销部）");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12)); // 合并单元格
            titleCell.setCellStyle(getTitleCellStyle(workbook)); // 设置单元格样式

            // 创建字段
            Row headerRow = sheet.createRow(1);
            String[] headers = {"ID", "开始时间", "结束时间", "客户名称", "归属片区", "接待人",
                    "协同领导", "主接待单位", "接待机会是否提交", "接待机会提交时间",
                    "接待总结是否提交", "接待总结提交时间", "备注"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(getHeaderCellStyle(workbook)); // 设置单元格样式
            }

            // 创建数据行
            int rowNum = 2; // 从第 2 行开始
            for (ReceptionRecord record : records) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(record.getId());
                row.createCell(1).setCellValue(record.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                row.createCell(2).setCellValue(record.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                row.createCell(3).setCellValue(record.getCustomerName());
                row.createCell(4).setCellValue(record.getArea());
                row.createCell(5).setCellValue(record.getReceptionist());
                row.createCell(6).setCellValue(record.getCoLeader());
                row.createCell(7).setCellValue(record.getMainUnit());
                row.createCell(8).setCellValue(record.getOpportunitySubmitted() ? "是" : "否");
                if (record.getOpportunitySubmitted()) {
                    row.createCell(9).setCellValue(record.getOpportunitySubmitTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                }
                row.createCell(10).setCellValue(record.getSummarySubmitted() ? "是" : "否");
                if (record.getSummarySubmitted()) {
                    row.createCell(11).setCellValue(record.getSummarySubmitTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                }
                Cell remarksCell = row.createCell(12);
                remarksCell.setCellValue(record.getRemarks());
                remarksCell.setCellStyle(getRemarksCellStyle(workbook)); // 设置单元格样式
            }

            // 自动调整列的大小，备注列除外
            for (int i = 0; i < headers.length - 1; i++) {
                sheet.autoSizeColumn(i);
            }

            // 将工作簿内容写入文件
            workbook.write(fileOut);
        }
    }

    private CellStyle getTitleCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle getRemarksCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }

}
