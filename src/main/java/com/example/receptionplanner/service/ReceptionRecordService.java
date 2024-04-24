package com.example.receptionplanner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.receptionplanner.domain.ReceptionRecord;

import java.io.IOException;


public interface ReceptionRecordService extends IService<ReceptionRecord> {

    //输出records到excel表中
    void exportRecordsToExcel() throws IOException;
}
