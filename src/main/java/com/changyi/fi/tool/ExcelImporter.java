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
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        InvoiceDao dao = (InvoiceDao) context.getBean("invoiceDao");
        File f = new File("/Users/finley/Documents/ceshi");
        if (f != null && f.isDirectory()) {
            File[] fileArray = f.listFiles();
            if (fileArray != null) {
                for (int i = 0; i < fileArray.length; i++) {
                    new Thread(new DBUpdater(fileArray[i], dao)).start();
                }
            }
        }
    }


    private static class DBUpdater implements Runnable {

        private InvoiceDao dao;

        private File file;

        public DBUpdater(File file, InvoiceDao dao) {
            this.file = file;
            this.dao = dao;
        }

        private void insertPO(EnterprisePO po, String name, String code) {
            System.out.println("Name: " + name + ", code: " + code);
            if (dao.getEnterpriseById(code) == null) {
                po.setName(name);
                po.setCreditCode(code);
                po.setCreateTime(new Date());
                po.setCreateBy(FIConstants.SYSTEM);
                po.setModifyTime(new Date());
                po.setModifyBy(FIConstants.SYSTEM);
                dao.insertEnterprise(po);
            }
        }

        public void run() {
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
}


}
