package jp.ac.hcs.GreenShower.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ac.hcs.GreenShower.report.ReportService;
@Controller
public class PdfController {
    
    @Autowired
    ReportService reportService;
    
    @RequestMapping("/pdf")
    public MyPdfView pdf(@RequestParam("id") String id, MyPdfView mav) {
//        ReportEntity reportEntity = reportService.getReport(id);
//        mav.addStaticAttribute("reportEntity", reportEntity);
        return mav;
    }
}