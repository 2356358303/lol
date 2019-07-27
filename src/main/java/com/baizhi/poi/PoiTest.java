package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class PoiTest {
    public static void main(String[] args) throws IOException {
        //导出一个Excel
        HSSFWorkbook workbook=new HSSFWorkbook();
        //获取合并单元格对象
        CellRangeAddress cellAddresses=new CellRangeAddress(0,0,0,4);
        //获取日期格式对象
        HSSFDataFormat dataFormat =workbook.createDataFormat();
        //给日期格式对象绑定展示格式
        short format=dataFormat.getFormat("yyy-mm-dd HH:mm:ss");
        //获取字体对象
        HSSFFont font=workbook.createFont();
        //设置字体加粗
        font.setBold(true);
        //设置字体变红
        font.setColor(Font.COLOR_RED);
        //拿到当前excel表
        HSSFSheet sheet =workbook.createSheet("测试");
        //合并
        sheet.addMergedRegion(cellAddresses);
        //获取样式对象
        HSSFCellStyle cellStyle=workbook.createCellStyle();
        //将字体对象绑定到样式对象中
        cellStyle.setFont(font);
        //设置居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //绑定日期格式
        cellStyle.setDataFormat(format);
        //设置列宽，第一个参数：列下标    第二个参数：宽度（进制：256）
        sheet.setColumnWidth(0,40*256);
        //获取第一行数据
       HSSFRow row= sheet.createRow(0);
        //获取第一行第一个单元格数据
        HSSFCell cell=row.createCell(0);
        //给当前单元格绑定样式
        cell.setCellStyle(cellStyle);
        //        给第一行第一个单元格写入内容
//        cell.setCellValue("编号编号编号编号编号编号");
        cell.setCellValue(new Date());
//        将excel文件写入到本地磁盘
        workbook.write(new File("E:/框架阶段/后期项目/day7/test.xls"));

    }
}
