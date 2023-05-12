package com.bpeproject.miniso.Controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpeproject.miniso.Entity.DailySale;
import com.bpeproject.miniso.Service.DailySaleService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    DailySaleService dailySaleService;

    @GetMapping("/daily-entry")
    public String dailyEntryGet(Model model) {
        model.addAttribute("dailySale", new DailySale());
        return "dailySalesEntry";
    }

    @PostMapping("/daily-entry")
    public String dailyEntryPost(@ModelAttribute("dailySale") DailySale dailySale) {
        double saleswogst = (int) dailySale.getTotalSales() / 1.17;
        dailySale.setTotalSalesWoGST(saleswogst);

        double avgTval = (int) dailySale.getTotalSales() / dailySale.getNumOfCustomers();
        dailySale.setAvgTransactionValue(avgTval);

        dailySale.setSalesDay(dailySale.getDate().getDayOfMonth());
        dailySale.setSalesMonth(dailySale.getDate().getMonthValue());
        dailySale.setSalesYear(dailySale.getDate().getYear());
        dailySaleService.addDailySale(dailySale);
        return "dailySalesEntry";
    }

    @GetMapping("/view-sales")
    public String viewSalesGet() {
        return "viewSales";
    }

    @PostMapping("/view-sales")
    public String processDates(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("interval") String interval,
            Model model, HttpSession session) {
        interval = interval.toLowerCase();
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);

        if (startDate.isAfter(endDate)) {
            model.addAttribute("errorMessage", "Start date must be before end date.");
            return "viewSales";
        }

        List<Object[]> intervalStats = dailySaleService.getSalesBetweenDates(interval, startDate, endDate);

        // Store the revenue data in the session
        session.setAttribute("intervalStats", intervalStats);
        // session.setAttribute("mostPopularData", mostPopularList);
        // session.setAttribute("leastPopularData", leastPopularList);

        model.addAttribute("datesSelected", true);
        model.addAttribute("intervalStats", intervalStats);
        model.addAttribute("interval", interval);
        session.setAttribute("interval", interval);
        // model.addAttribute("mostPopularList", mostPopularList);
        // model.addAttribute("leastPopularList", leastPopularList);

        return "viewSales"; // Return the statistics page
    }

    @GetMapping("/view-sales/intervalRevenue")
    public ResponseEntity<byte[]> getChart(HttpSession session) {
        // Retrieve the revenue data from the session
        @SuppressWarnings("unchecked")
        List<Object[]> intervalStats = (List<Object[]>) session.getAttribute("intervalStats");

        // Create a dataset using the revenue data
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String intervalSelected = (String) session.getAttribute("interval");
        System.out.println(intervalSelected + "      !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        if (intervalSelected.equals("daily")) {

            for (Object[] data : intervalStats) {
                String day = data[0].toString();
                Number revenue = (Number) data[1];
                dataset.addValue(revenue.doubleValue(), "Revenue", day);
            }
        } else if (intervalSelected.equals("monthly")) {
            for (Object[] data : intervalStats) {
                String month = data[0].toString() + "/" + data[1].toString();
                Number revenue = (Number) data[2];
                dataset.addValue(revenue.doubleValue(), "Revenue", month);
            }
        } else if (intervalSelected.equals("yearly")) {
            for (Object[] data : intervalStats) {
                String year = data[0].toString();
                Number revenue = (Number) data[1];
                dataset.addValue(revenue.doubleValue(), "Revenue", year);
            }
        }
        // switch (interval.toLowerCase()) {
        // case "daily":
        // for (Object[] data : intervalStats) {
        // String day = data[0].toString();
        // Number revenue = (Number) data[1];
        // dataset.addValue(revenue.doubleValue(), "Revenue", day);
        // }
        // break;
        // case "monthly":
        // for (Object[] data : intervalStats) {
        // String month = data[0].toString() + "/" + data[1].toString();
        // Number revenue = (Number) data[2];
        // dataset.addValue(revenue.doubleValue(), "Revenue", month);
        // }
        // break;
        // case "yearly":
        // for (Object[] data : intervalStats) {
        // String year = data[0].toString();
        // Number revenue = (Number) data[1];
        // dataset.addValue(revenue.doubleValue(), "Revenue", year);
        // }
        // break;
        // default:
        // interval = "daily";
        // for (Object[] data : intervalStats) {
        // String day = data[0].toString();
        // Number revenue = (Number) data[1];
        // dataset.addValue(revenue.doubleValue(), "Revenue", day);
        // }
        // throw new IllegalArgumentException("Invalid interval: " + interval);
        // }

        // Create a bar chart using the dataset
        JFreeChart chart = ChartFactory.createBarChart("Revenue Graph", "Time", "Revenue(PKR)", dataset);

        // Customize chart appearance
        chart.setBackgroundPaint(Color.white);

        // Customize plot appearance
        CategoryPlot plot = chart.getCategoryPlot();
        Color lightGray = new Color(230, 230, 230); // Lighter gray color for background
        plot.setBackgroundPaint(lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // Customize renderer appearance
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        Color lightBlue = new Color(135, 206, 250); // Light blue color for bars
        renderer.setSeriesPaint(0, lightBlue);
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.2);

        // Remove gradient paint (white shine)
        renderer.setBarPainter(new StandardBarPainter());

        // Render chart to a BufferedImage
        BufferedImage chartImage = chart.createBufferedImage(900, 600);

        // Convert BufferedImage to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ChartUtils.writeBufferedImageAsPNG(byteArrayOutputStream, chartImage);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}
