package com.wsq.excel;

import com.wsq.excel.context.AnalysisContext;
import com.wsq.excel.event.AnalysisEventListener;
import com.wsq.excel.event.WriteHandler;
import com.wsq.excel.metadata.Sheet;
import com.wsq.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Reader and writer factory class
 *
 * @author jipengfei
 */
public class EasyExcelFactory {

    /**
     * Quickly read small files，no more than 10,000 lines.
     *
     * @param in    the POI filesystem that contains the Workbook stream.
     * @param sheet read sheet.
     * @return analysis result.
     */
    public static List<Object> read(InputStream in, Sheet sheet) {
        final List<Object> rows = new ArrayList<Object>();
        new ExcelReader(in, null, new AnalysisEventListener() {
            @Override
            public void invoke(Object object, AnalysisContext context) {
                rows.add(object);
            }

            @Override
            public boolean doAfterAllAnalysed(AnalysisContext context) {
                return true;
            }

/*            @Override
            public Map<String, Object> getParams() {
                return null;
            }

            @Override
            public void setParams(Map map) {

            }*/

        }, false).read(sheet);
        return rows;
    }

    /**
     * Parsing large file
     *
     * @param in       the POI filesystem that contains the Workbook stream.
     * @param sheet    read sheet.
     * @param listener Callback method after each row is parsed.
     */
    public static boolean readBySax(InputStream in, Sheet sheet, AnalysisEventListener listener) {
        return new ExcelReader(in, null, listener).read(sheet);
    }

    /**
     * Get ExcelReader.
     *
     * @param in       the POI filesystem that contains the Workbook stream.
     * @param listener Callback method after each row is parsed.
     * @return ExcelReader.
     */
    public static ExcelReader getReader(InputStream in, AnalysisEventListener listener) {
        return new ExcelReader(in, null, listener);
    }

    /**
     * Get ExcelWriter
     *
     * @param outputStream the java OutputStream you wish to write the data to.
     * @return new ExcelWriter.
     */
    public static ExcelWriter getWriter(OutputStream outputStream) {
        return new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
    }

    /**
     * Get ExcelWriter
     *
     * @param outputStream the java OutputStream you wish to write the data to.
     * @param typeEnum     03 or 07
     * @param needHead     Do you need to write the header to the file?
     * @return new  ExcelWriter
     */
    public static ExcelWriter getWriter(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
        return new ExcelWriter(outputStream, typeEnum, needHead);
    }

    /**
     * Get ExcelWriter with a template file
     *
     * @param temp         Append data after a POI file , Can be null（the template POI filesystem that contains the
     *                     Workbook stream)
     * @param outputStream the java OutputStream you wish to write the data to
     * @param typeEnum     03 or 07
     * @return new  ExcelWriter
     */
    public static ExcelWriter getWriterWithTemp(InputStream temp, OutputStream outputStream, ExcelTypeEnum typeEnum,
                                                boolean needHead) {
        return new ExcelWriter(temp, outputStream, typeEnum, needHead);
    }

    /**
     * Get ExcelWriter with a template file
     *
     * @param temp         Append data after a POI file , Can be null（the template POI filesystem that contains the
     *                     Workbook stream)
     * @param outputStream the java OutputStream you wish to write the data to
     * @param typeEnum     03 or 07
     * @param needHead
     * @param handler      User-defined callback
     * @return new  ExcelWriter
     */
    public static ExcelWriter getWriterWithTempAndHandler(InputStream temp,
                                                          OutputStream outputStream,
                                                          ExcelTypeEnum typeEnum,
                                                          boolean needHead,
                                                          WriteHandler handler) {
        return new ExcelWriter(temp, outputStream, typeEnum, needHead, handler);
    }

}
