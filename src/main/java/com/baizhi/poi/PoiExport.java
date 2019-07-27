package com.baizhi.poi;

import com.baizhi.entity.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiExport {
    public static void main(String[] args) throws IOException {
        //从数据库中查出要导出的数据
        List<User>list=new ArrayList<User>();
        for(int i=0;i<10;i++){
            User user=new User();
            user.setId(i+"");
            user.setUsername("张三"+i+"号");
            user.setSex("男");
            list.add(user);
        }
//        使用poi进行导出
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户信息表");
//        第一行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("用户详细信息");
//        合并
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 3);
        sheet.addMergedRegion(cellAddresses);
//        居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
//        第二行
        HSSFRow rowHead = sheet.createRow(1);
        String[] arr = {"编号","姓名","性别","生日"};
        for (int i = 0; i < arr.length; i++) {
            HSSFCell cell1 = rowHead.createCell(i);
            cell1.setCellValue(arr[i]);
        }

//        设置日期格式
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        cellStyle.setDataFormat(format);


//        导出用户数据
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            HSSFRow userRow = sheet.createRow(2+i);


            HSSFCell cell0 = userRow.createCell(0);
            cell0.setCellValue(user.getId());

            HSSFCell cell1 = userRow.createCell(1);
            cell1.setCellValue(user.getUsername());

            HSSFCell cell2 = userRow.createCell(2);
            cell2.setCellValue(user.getSex());
        }

        workbook.write(new File("E:/框架阶段/后期项目/day7/用户信息.xls"));
    }
}
