import Entity.Data;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

public class DatabaseUploader {
    public static void main(String[] args) {
        try {
            File file=new File("/Users/lvzhiwei/Desktop/1.xls");
            Workbook workbook=Workbook.getWorkbook(file);
            Sheet s=workbook.getSheet(0);
            DatabaseHelper databaseHelper=new DatabaseHelper();
            databaseHelper.init();
            databaseHelper.ChooseDatabase("foods");
            for(int i=2;i<s.getRows();i++){
                int j=0;
                Data data=new Data(s.getCell(1,i).getContents().replace('\"','\0'),
                        s.getCell(2,i).getContents().replace('\"','\0')
                        ,s.getCell(0,i).getContents().replace('\"','\0'),
                        s.getCell(27,i).getContents().replace('\"','\0'),
                        s.getCell(3,i).getContents().replace('\"','\0')
                        ,s.getCell(4,i).getContents().replace('\"','\0'),
                        s.getCell(6,i).getContents().replace('\"','\0'),
                        s.getCell(5,i).getContents().replace('\"','\0'),
                        s.getCell(8,i).getContents().replace('\"','\0'),
                        s.getCell(9,i).getContents().replace('\"','\0'),
                        s.getCell(10,i).getContents().replace('\"','\0'),
                        s.getCell(11,i).getContents().replace('\"','\0'),
                        s.getCell(12,i).getContents().replace('\"','\0'),
                        s.getCell(13,i).getContents().replace('\"','\0'),
                        s.getCell(14,i).getContents().replace('\"','\0'),
                        s.getCell(15,i).getContents().replace('\"','\0'),
                        s.getCell(17,i).getContents().replace('\"','\0'),
                        s.getCell(18,i).getContents().replace('\"','\0'),
                        s.getCell(19,i).getContents().replace('\"','\0'));
                String SQL=data.returnInsertSQL();
                System.out.println(i+"/"+s.getRows());
                System.out.println(SQL);
                databaseHelper.ExecuteSQL(SQL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

    }

}

