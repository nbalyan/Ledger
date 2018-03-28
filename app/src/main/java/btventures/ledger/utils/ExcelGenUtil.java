package btventures.ledger.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by tewat on 28-03-2018.
 */

public class ExcelGenUtil {

    public File getFile(){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/ledger");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "ledger-" + n + ".xls";
        return new File(myDir, fname);
    }

    public void writeExcel(Context context, List<Label> labels){

        WritableWorkbook myFirstWbook = null;
        File file=getFile();
        try {

            myFirstWbook = Workbook.createWorkbook(file);

            // create an Excel sheet
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

            // add something into the Excel sheet
            /*Label label = new Label(0, 0, "");
            excelSheet.addCell(label);*/
            for(Label label1:labels){
                excelSheet.addCell(label1);
            }
            /*Number number = new Number(0, 1, 1);
            excelSheet.addCell(number);

            label = new Label(1, 0, "Result");
            excelSheet.addCell(label);

            label = new Label(1, 1, "Passed");
            excelSheet.addCell(label);

            number = new Number(0, 2, 2);
            excelSheet.addCell(number);

            label = new Label(1, 2, "Passed 2");
            excelSheet.addCell(label);*/

            myFirstWbook.write();



        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }


        }
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        downloadManager.addCompletedDownload(file.getName(), file.getName(), true, "image/jpeg",file.getAbsolutePath(),file.length(),true);
    }
}
