package jp.ac.hcs.GreenShower.pdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import jp.ac.hcs.GreenShower.report.ReportEntity;
public class MyPdfView extends AbstractPdfView {
    @SuppressWarnings("deprecation")
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 出力したいデータを変数へ設定
        ReportEntity entity = (ReportEntity)model.get("reportEntity");
        
        // フォントの作成
        BaseFont baseFont = BaseFont.createFont(
            "HeiseiMin-W3",       // 平成明朝体
            "UniJIS-UCS2-HW-H",   // 横書き指定&英数を半角幅で印字
            BaseFont.NOT_EMBEDDED);
         
        Font font18 = new Font(baseFont, 10);
        Font font18b = new Font(baseFont, 8);
        // (4)文書にテキストを追加
        doc.add(new Paragraph("受験報告書", new Font(baseFont, 30)));
        
        Table table = new Table(2);
        table.setAutoFillEmptyCells(true);
        table.setBorderWidth(1);
        
        Cell cell = new Cell(new Phrase("求人番号", font18));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("例）A2020", font18b));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("企業名（正式名称）", font18));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("例）北海道情報専門学校", font18b));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("学校登録種別", font18));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("例）学校斡旋", font18b));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        /** 以降は出力したいデータを追加してください。 */
        
        doc.add(table);
    }
}


