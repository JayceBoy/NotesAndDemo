package com.jayce.demo.utils;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 大数据量Excel文件导出工具类
 *
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BigDataExcelExportUtil {
    // 分片大小
    @Setter
    private static int PARTITION_SIZE = 10000;

    //一个Sheet页的数据容量,最大可设置为100_0000
    private final static int SHEET_SIZE = 10_0000;

    // 列英文字段 按照此顺序写文件
    @Setter
    private List<String> feilds;
    //表头

    @Setter
    private String[] titles;

    // 字典转换
    @Setter
    private Map<String, Map<String, Object>> dictMap;

    private OutputStream os;

    private SXSSFWorkbook sxssfWorkbook;

    private Sheet hssfSheet;

    //行号
    private int rowNum = 0;
    //Sheet页编号
    private int sheetNumber = 0;

    public BigDataExcelExportUtil(OutputStream os, List<String> feilds,String[] titles) {
        this.os = os;
        this.feilds = feilds;
        this.titles = titles;
        //内存中保留 1000 条数据，以免内存溢出，其余写入 硬盘
        sxssfWorkbook = new SXSSFWorkbook( 1000);
        sxssfWorkbook.setCompressTempFiles(true);  //启用压缩
        drawTitle();
    }

    //绘制表头
    private BigDataExcelExportUtil drawTitle() {
        rowNum = 0;
        hssfSheet = sxssfWorkbook.createSheet("Sheet " + sheetNumber);
        Row rowTitle = hssfSheet.createRow(rowNum);
        for (int colNum = 0; colNum < titles.length; colNum++) {
            rowTitle.createCell(colNum).setCellValue(titles[colNum]);
        }
        rowNum++;
        sheetNumber++;
        return this;
    }

    /**
     * 绘制Excel内容区域
     * @param contents 内容列表
     * @throws IOException
     */
    public BigDataExcelExportUtil drawContent(List<Map<String, Object>> contents) {
        if(null == dictMap) {
            dictMap = new HashMap<>();
        }
        //写入内容   分片
        for (List<Map<String, Object>> contentTemp : Lists.partition(contents, PARTITION_SIZE)) {
            for (Map<String, Object> content : contentTemp) {
                if (rowNum % SHEET_SIZE == 0) {
                   drawTitle();
                }
                //创建新的一行
                Row row = hssfSheet.createRow(rowNum);
                //列编号
                int colNum = 0;
                for (String feild : feilds) {
                    Object c = content.get(feild);
                    if (dictMap.containsKey(feild)) {
                        c = dictMap.get(feild).get(c);
                    }
                    row.createCell(colNum).setCellValue(c == null ? "" : c.toString());
                    colNum++;
                }
                rowNum++;
            }
        }
        return this;
    }

    //将绘制好的表格内容写入输出流
    public BigDataExcelExportUtil write() throws IOException{
        sxssfWorkbook.write(os);
        os.flush();
        sxssfWorkbook.dispose();
        return this;
    }

    //关闭输出流
    public BigDataExcelExportUtil flush() throws IOException {
        if (null != os) {
            os.flush();
            os.close();
        }
        return this;
    }

    public OutputStream getOs() {
        return this.os;
    }


    /**
     * 设置Header
     *
     * @param fileName 文件名
     * @param response 响应
     * @throws UnsupportedEncodingException
     */
    public void responseSetProperties(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fn = fileName + sdf.format(new Date()) + ".xlsx";
        // 读取字符编码
        String utf = "UTF-8";
        // 设置响应
        response.setContentType("application/ms-txt.numberformat:@");
        response.setCharacterEncoding(utf);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, utf));
    }
}
