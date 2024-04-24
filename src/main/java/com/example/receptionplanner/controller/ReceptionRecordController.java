package com.example.receptionplanner.controller;

import com.example.receptionplanner.domain.ReceptionRecord;
import com.example.receptionplanner.service.ReceptionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/records")
public class ReceptionRecordController {

    private final ReceptionRecordService recordService;

    @Autowired
    public ReceptionRecordController(ReceptionRecordService recordService) {
        this.recordService = recordService;
    }

    // 存储接待记录
    @PostMapping("/save")
    public ResponseEntity<String> saveRecord(@RequestBody ReceptionRecord record) {
        boolean saved = recordService.save(record);
        if (saved) {
            return ResponseEntity.ok("Record saved successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save record!");
        }
    }


    // 导出接待记录到 Excel
    @GetMapping("/export")
    public ResponseEntity<String> exportRecordsToExcel() {
        try {
            recordService.exportRecordsToExcel();
            return ResponseEntity.ok("Excel 导出成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Excel 导出失败！");
        }
    }


}
