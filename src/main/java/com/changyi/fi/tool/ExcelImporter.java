package com.changyi.fi.tool;

import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.util.FIConstants;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;


/**
 * Created by finley on 7/10/17.
 */
public class ExcelImporter {

    public static void main(String[] args) {
        System.out.println("This is importer");
        File f = new File("/Users/finley/Documents/ceshi");
        if (f != null && f.isDirectory()) {
            File[] fileArray = f.listFiles();
            if (fileArray != null) {
                for (int i = 0; i < fileArray.length; i++) {
                    parseExcel(fileArray[i]);
                }
            }
        }
    }

    private static void parseExcel(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            Workbook wb = new XSSFWorkbook(in);
            Sheet st = wb.getSheetAt(0);
            for (int j = 1; j < st.getPhysicalNumberOfRows(); j++) {
                Row row = st.getRow(j);
                EnterprisePO po = new EnterprisePO();
                String name = row.getCell(1).getStringCellValue();
                String code = row.getCell(2).getStringCellValue();
                insertPO(po, name, code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertPO(EnterprisePO po, String name, String code) {
        System.out.println("Name: " + name + ", code: " + code);
        po.setName(name);
        po.setCreditCode(code);
        po.setCreateTime(new Date());
        po.setCreateBy(FIConstants.SYSTEM);
        po.setModifyTime(new Date());
        po.setModifyBy(FIConstants.SYSTEM);
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        InvoiceDao dao = (InvoiceDao) context.getBean("invoiceDao");
        dao.insertEnterprise(po);
    }


}
