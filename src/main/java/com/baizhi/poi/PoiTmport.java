package com.baizhi.poi;

import com.baizhi.entity.User;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiTmport {
    public static void main(String[] args)throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E:/框架阶段/后期项目/day7/用户信息.xls")));
        HSSFSheet sheet = workbook.getSheet("用户信息表");

        List<User> list = new ArrayList<>();

        for (int i = 2; i < sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            User user = new User();

            HSSFCell cell = row.getCell(0);
            String s = cell.getStringCellValue();
            user.setId(s);

            HSSFCell cell1 = row.getCell(1);
            String s1 = cell1.getStringCellValue();
            user.setUsername(s1);

            HSSFCell cell2 = row.getCell(2);
            user.setSex(cell2.getStringCellValue());


            list.add(user);
        }

        for (User user : list) {
            System.out.println(user);
        }

    }
}
