package com.yhh.practice.springboot.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class ExcelController {


    @RequestMapping(value = "/downExcel")
    public void downExcel() throws Exception {
        File fil = new File( "E:\\excel");
        if(!fil.exists()){
            fil.getParentFile().mkdir();
        }
        try {
            fil.createNewFile();
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fil));
        // 获取第一个表单
        Sheet first = workbook.getSheetAt(0);
        for (int i = 0; i < 100000; i++) {
            Row row = first.createRow(i);
            for (int j = 0; j < 11; j++) {
                if(i == 0) {
                    // 首行
                    row.createCell(j).setCellValue("column" + j);
                } else {
                    // 数据
                    if (j == 0) {
                        CellUtil.createCell(row, j, String.valueOf(i));
                    } else
                        CellUtil.createCell(row, j, String.valueOf(Math.random()));
                }
            }
        }
        // 写入文件
        FileOutputStream out = new FileOutputStream("workbook.xlsx");
        workbook.write(out);
        out.close();


    }

}
