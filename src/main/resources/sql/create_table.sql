-- 创建数据库
CREATE DATABASE IF NOT EXISTS zjx_reception;

-- 使用数据库
USE zjx_reception;

-- 创建表
CREATE TABLE IF NOT EXISTS reception_records (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '接待记录ID',
                                   start_time DATETIME NOT NULL COMMENT '接待开始时间',
                                   end_time DATETIME NOT NULL COMMENT '接待结束时间',
                                   customer_name VARCHAR(255) DEFAULT '' COMMENT '客户名称',
                                   area VARCHAR(255) DEFAULT '' COMMENT '归属片区',
                                   receptionist VARCHAR(255) DEFAULT '' COMMENT '接待人',
                                   co_leader VARCHAR(255) DEFAULT '' COMMENT '协同领导',
                                   main_unit VARCHAR(255) DEFAULT '' COMMENT '主接待单位',
                                   opportunity_submitted BOOLEAN DEFAULT FALSE COMMENT '接待机会是否提交（是/否）',
                                   opportunity_submit_time DATETIME DEFAULT NULL COMMENT '接待机会提交时间',
                                   summary_submitted BOOLEAN DEFAULT FALSE COMMENT '接待总结是否提交（是/否）',
                                   summary_submit_time DATETIME DEFAULT NULL COMMENT '接待总结提交时间',
                                   remarks VARCHAR(255) DEFAULT '' COMMENT '备注'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;