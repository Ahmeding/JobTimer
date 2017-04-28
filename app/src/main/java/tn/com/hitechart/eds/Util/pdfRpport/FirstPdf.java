package tn.com.hitechart.eds.Util.pdfRpport;

import android.content.Context;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Achat;
import tn.com.hitechart.eds.Entity.Composant;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.Entity.Message;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.data.UserManager;

/**
 * Created by BARA' on 18/01/2017.
 */


public class FirstPdf {

    private static String PATHFILE = "/data/user/0/tn.com.hitechart.eds/files/";
    private static Font catFont = new Font(Font.FontFamily.UNDEFINED, 22, Font.BOLD, BaseColor.BLUE);
    private static Font catFontblanc = new Font(Font.FontFamily.UNDEFINED, 5, Font.BOLD, BaseColor.WHITE);
    private static Font catFontempty = new Font(Font.FontFamily.UNDEFINED, 15, Font.NORMAL, BaseColor.PINK);


    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font dataFont = new Font(Font.FontFamily.UNDEFINED, 16, Font.NORMAL, BaseColor.BLUE);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    String[] tabDatapointage = new String[]{};
    User user = UserManager.load();
    List<Task> tasks = new ArrayList<>();
    List<Task> autreActs = new ArrayList<>();

    List<Achat> achats = new ArrayList<>();
    List<Composant> comps = new ArrayList<>();
    List<Message> msgs = new ArrayList<>();
    List<Dossier> dossiers = new ArrayList<>();
    TasksDAO td;


    public void createPdf(Context context, String NamePDF, String[] tabData, List<Task> taskList, List<Achat> achatList, List<Composant> compList, List<Message> msgList, List<Task> autreActList, List<Dossier> dossList) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(PATHFILE + NamePDF));
            document.open();
            //addMetaData(document);
            // addTitlePage(document);

            tabDatapointage = tabData;
            tasks = taskList;
            achats = achatList;
            comps = compList;
            msgs = msgList;
            dossiers = dossList;
            td = new TasksDAO(context);
            autreActList = autreActList;

            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addContent(Document document) throws DocumentException {

        // Second parameter is the number of the chapter

        Phrase phrase = new Phrase("", catFontblanc);
        phrase.add("");
        Chapter catPart = new Chapter(new Paragraph(phrase), 0);
        Chapter catParttask = new Chapter(new Paragraph(phrase), 0);
        Paragraph p = new Paragraph("", catFontblanc);

        Section subCatPart = catPart.addSection(p);


        // subCatPart.add(new Paragraph("Hello1111111111111111111111 "+user.getLogin()));

        //subPara = new Paragraph("Subcategory 22222222222222 "+pointage.getDate_timeInM(), subFont);
        //subCatPart = catPart.addSection(subPara);
        //subCatPart.add(new Paragraph("Paragraph 1"));
        //subCatPart.add(new Paragraph("Paragraph 2"));
        //subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        //createList(subCatPart);
        //Paragraph paragraph = new Paragraph();
        //addEmptyLine(paragraph, 5);
        //subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);
        //document.add(catParttask);
        // Next section
        // anchor = new Anchor("Second Chapter", catFont);
        //anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        //catPart = new Chapter(new Paragraph(anchor), 1);

        //subPara = new Paragraph("Subcategory", subFont);
        //subCatPart = catPart.addSection(subPara);
        //subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        //document.add(catPart);

    }


    private void createTable(Section subCatPart) throws BadElementException {


        //-------------------------- TAB POINTAGE -------------------//
        PdfPTable table = new PdfPTable(8);
        table.setTotalWidth(PageSize.A4.rotate().getWidth() - 10);
        table.setLockedWidth(true);
        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
        //----------- C1 --------------//
        PdfPCell c1 = new PdfPCell(new Phrase("Date Rapport"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
//----------- C2 --------------//
        c1 = new PdfPCell(new Phrase("Technicien"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
//----------- C3 --------------//
        c1 = new PdfPCell(new Phrase("Poinatge"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

//----------- C4 --------------//
        c1 = new PdfPCell(new Phrase("Matin"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
//----------- C5 --------------//
        c1 = new PdfPCell(new Phrase("Après-Midi"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

//----------- C6 --------------//
        c1 = new PdfPCell(new Phrase("Rapport Journalier", catFont));
        c1.setBorder(Rectangle.NO_BORDER);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(3);
        table.addCell(c1);
//----------- C7 --------------//

//----------- C8 --------------//

        table.setHeaderRows(1);

//----------- R2 --------------//
//----------- C1 --------------//
        c1 = new PdfPCell(new Phrase(tabDatapointage[0], dataFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRowspan(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(user.getLogin(), dataFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRowspan(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Arrivée"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(tabDatapointage[1], dataFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(tabDatapointage[2], dataFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);


        c1 = new PdfPCell(new Phrase(""));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
//----------- C7 --------------//

        c1 = new PdfPCell(new Phrase(""));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
//----------- C8 --------------//

        c1 = new PdfPCell(new Phrase(""));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);


//----------- R3 --------------//

        c1 = new PdfPCell(new Phrase("Sortie"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(tabDatapointage[3], dataFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(tabDatapointage[4], dataFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(""));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
//----------- C7 --------------//
        c1 = new PdfPCell(new Phrase(""));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
//----------- C8 --------------//
        c1 = new PdfPCell(new Phrase(""));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        table.setSpacingAfter(10);
        subCatPart.add(table);


//-------------------------- END TAB POINTAGE -------------------//
// TODO: 20/01/2017
//--------------------------------- TASK ---------------------//
        PdfPTable tableTask = new PdfPTable(5);

        tableTask.setTotalWidth(PageSize.A4.rotate().getWidth() - 10);
        tableTask.setLockedWidth(true);

        PdfPCell c2 = new PdfPCell(new Phrase("Dossiers Traités", catFont));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setColspan(5);
        c2.setBorder(Rectangle.NO_BORDER);
        tableTask.addCell(c2);

        tableTask.setHeaderRows(1);

        c2 = new PdfPCell(new Phrase("N°Doss.", dataFont));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableTask.addCell(c2);

        c2 = new PdfPCell(new Phrase("Client", dataFont));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableTask.addCell(c2);

        c2 = new PdfPCell(new Phrase("Durée", dataFont));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableTask.addCell(c2);

        c2 = new PdfPCell(new Phrase("Mission", dataFont));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableTask.addCell(c2);

        c2 = new PdfPCell(new Phrase("Resultat", dataFont));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableTask.addCell(c2);
        if (tasks.isEmpty()) {
            c2 = new PdfPCell(new Phrase("Aucune  Tache a été traité", catFontempty));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setColspan(5);
            tableTask.addCell(c2);
        } else {


            if (!dossiers.isEmpty()) {
                for (Dossier d : dossiers) {

                    tableTask.addCell(d.getNumDoss());
                    tableTask.addCell(d.getClient());
                    tableTask.addCell(convertLongtoDurationfr(d.getTimeDuration()));
                    tableTask.addCell(td.getTaskById(d.get_idTask()).getType());
                    tableTask.addCell(td.getTaskById(d.get_idTask()).getRes());
                }
            }

        }

        tableTask.setSpacingAfter(10);

        subCatPart.add(tableTask);

//-------------------------- END TAB TASK -------------------//
        // TODO: 20/01/2017  tab achat
//-------------------------- TAB ACHAT -------------------//


        PdfPTable tableAchat = new PdfPTable(3);

        tableAchat.setTotalWidth(PageSize.A4.rotate().getWidth() - 10);
        tableAchat.setLockedWidth(true);

        PdfPCell c3 = new PdfPCell(new Phrase("Frais Engagés", catFont));
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setColspan(3);
        c3.setBorder(Rectangle.NO_BORDER);
        tableAchat.addCell(c3);

        tableAchat.setHeaderRows(1);
        c3 = new PdfPCell(new Phrase("N°.Doss.", dataFont));
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableAchat.addCell(c3);


        c3 = new PdfPCell(new Phrase("Désignation", dataFont));
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableAchat.addCell(c3);


        c3 = new PdfPCell(new Phrase("Montant TCC", dataFont));
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableAchat.addCell(c3);
        if (achats.isEmpty()) {
            c3 = new PdfPCell(new Phrase("la liste des achats est vide", catFontempty));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setColspan(3);
            tableAchat.addCell(c3);
        } else {

            for (Achat achat : achats) {

                tableAchat.addCell(String.valueOf(achat.getNumDoss()));
                tableAchat.addCell(achat.getDesignation());
                tableAchat.addCell(String.valueOf(achat.getPrix()));
            }
        }
        tableAchat.setSpacingAfter(10);
        subCatPart.add(tableAchat);
//-------------------------- END TAB ACHAT -------------------//

        // TODO: 20/01/2017  tab composant
//-------------------------- TAB COMPOSANT -------------------//


        PdfPTable tableComp = new PdfPTable(3);

        tableComp.setTotalWidth(PageSize.A4.rotate().getWidth() - 10);
        tableComp.setLockedWidth(true);

        PdfPCell c4 = new PdfPCell(new Phrase("Composants", catFont));
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        c4.setColspan(3);
        c4.setBorder(Rectangle.NO_BORDER);
        tableComp.addCell(c4);
        tableComp.setHeaderRows(1);

        c4 = new PdfPCell(new Phrase("N°.Doss.", dataFont));
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableComp.addCell(c4);


        c4 = new PdfPCell(new Phrase("Désignation", dataFont));
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableComp.addCell(c4);


        c4 = new PdfPCell(new Phrase("Quantité", dataFont));
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableComp.addCell(c4);

        if (comps.isEmpty()) {
            c4 = new PdfPCell(new Phrase("la liste des composants est vide", catFontempty));
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            c4.setColspan(3);
            tableComp.addCell(c4);
        } else {
            for (Composant comp : comps) {

                tableComp.addCell(String.valueOf(comp.getNumDoss()));
                tableComp.addCell(comp.getName());
                tableComp.addCell(String.valueOf(comp.getQte()));
            }
        }
        tableComp.setSpacingAfter(10);
        subCatPart.add(tableComp);
//-------------------------- END TAB COMPOSANT -------------------//

        // TODO: 20/01/2017  tab message
//-------------------------- TAB MESSAGE -------------------//


        PdfPTable tableMsg = new PdfPTable(2);

        tableMsg.setTotalWidth(PageSize.A4.rotate().getWidth() - 10);
        tableMsg.setLockedWidth(true);

        PdfPCell c5 = new PdfPCell(new Phrase("Message à Transmettre", catFont));
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        c5.setColspan(3);
        c5.setBorder(Rectangle.NO_BORDER);
        tableMsg.addCell(c5);


        c5 = new PdfPCell(new Phrase("N°.Doss.", dataFont));
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableMsg.addCell(c5);


        tableMsg.setHeaderRows(1);
        c5 = new PdfPCell(new Phrase("Message", dataFont));
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableMsg.addCell(c5);


        tableMsg.setHeaderRows(1);
        if (msgs.isEmpty()) {
            c5 = new PdfPCell(new Phrase("Aucun message", catFontempty));
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            c5.setColspan(3);
            tableMsg.addCell(c5);
        } else {
            for (Message msg : msgs) {

                tableMsg.addCell(String.valueOf(msg.getNumDoss()));
                tableMsg.addCell(msg.getMsg());
            }
        }
        tableMsg.setSpacingAfter(10);
        subCatPart.add(tableMsg);
//-------------------------- END TAB MESSAGE -------------------//

        // TODO: 20/01/2017  tab Activitee
//-------------------------- TAB ACTIVITEE -------------------//


        PdfPTable tableAct = new PdfPTable(2);

        tableAct.setTotalWidth(PageSize.A4.rotate().getWidth() - 10);
        tableAct.setLockedWidth(true);

        PdfPCell c6 = new PdfPCell(new Phrase("Autre Activitées", catFont));
        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
        c6.setColspan(2);
        c6.setBorder(Rectangle.NO_BORDER);
        tableAct.addCell(c6);

        tableAct.setHeaderRows(1);

        c6 = new PdfPCell(new Phrase("N°.Doss.", dataFont));
        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableAct.addCell(c6);


        c6 = new PdfPCell(new Phrase("Durée", dataFont));
        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableAct.addCell(c6);
        if (autreActs.isEmpty()) {
            c6 = new PdfPCell(new Phrase("Aucune autre Activitée a été effectué", catFontempty));
            c6.setHorizontalAlignment(Element.ALIGN_CENTER);
            c6.setColspan(3);
            tableAct.addCell(c6);
        } else {

            for (Task autreA : autreActs) {

                tableAct.addCell(String.valueOf(autreA.getType()));
                tableAct.addCell(autreA.getNumDoss());
            }

        }
        tableAct.setSpacingAfter(10);
        subCatPart.add(tableAct);
//-------------------------- END TAB MESSAGE -------------------//
    }


    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private void addMetaData(Document document) {

        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("AHMED BEJ");
        document.addCreator("AHMED BEJ");
    }

    private void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Rapport Journalier", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Technicien: " + System.getProperty("" + user.getLogin())
                        + ", " + //pointage.getDate_timeInM(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        smallBold));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
                "This document describes something which is very important ",
                smallBold));

        addEmptyLine(preface, 8);

        preface.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    // private void createList(Section subCatPart) {
    //     List list = new List(true, false, 10);
    //     list.add(new ListItem("First point"));
    //     list.add(new ListItem("Second point"));
    //     list.add(new ListItem("Third point"));
    //     subCatPart.add(list);
    // }
    public String convertLongtoDurationfr(Long t) {
        String duration;


        int seconds = (int) (t / 1000);
        int minutes = seconds / 60;
        int hours = seconds / 3600;
        seconds = seconds % 60;
        duration =
                String.format("%02d", hours) + "H"
                        + String.format("%02d", minutes);

        return duration;
    }

    private void getTaskfromDoss() {
        for (Dossier d : dossiers) {
            for (Task t : tasks) {
                //if(t.get_id()==d.get_idTask();
            }
        }
    }
}

