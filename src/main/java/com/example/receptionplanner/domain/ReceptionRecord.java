package com.example.receptionplanner.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Data
@RequiredArgsConstructor
@TableName("reception_records")
public class ReceptionRecord {
    @TableId()
    private Long id;
    @TableField(value = "start_time")
    private LocalDateTime startTime;
    @TableField(value = "end_time")
    private LocalDateTime endTime;
    @TableField(value = "customer_name")
    private String customerName;
    @TableField(value = "area")
    private String area;
    @TableField(value = "receptionist")
    private String receptionist;
    @TableField(value = "co_leader")
    private String coLeader;
    @TableField(value = "main_unit")
    private String mainUnit;
    @TableField(value = "opportunity_submitted")
    private Boolean opportunitySubmitted;
    @TableField(value = "opportunity_submit_time")
    private LocalDateTime opportunitySubmitTime;
    @TableField(value = "summary_submitted")
    private Boolean summarySubmitted;
    @TableField(value = "summary_submit_time")
    private LocalDateTime summarySubmitTime;
    @TableField(value = "remarks")
    private String remarks;
}
