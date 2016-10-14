package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import utilisateurs.Utilisateur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by user on 15/01/16.
 */
public class PDF {

    private static Document document;
    private static FileOutputStream file;
    private static String filePath;

    private PDF() {}

    /**
     * create a pdf with user informations
     * @param utilisateur
     */
    public static void createPDF(Utilisateur utilisateur) {
        Rectangle pagesize = new Rectangle(400f, 720f);
        document = new Document(pagesize, 36f, 72f, 108f, 180f);
        try {
            filePath = utilisateur.getNom() + utilisateur.getNumTransactions() + ".pdf";
            file = new FileOutputStream(filePath);
            PdfWriter.getInstance(document, file);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
    }

    /**
     * add new information in the document
     * @param str
     */
    public static void addToDoc(String str) {
        try {
            document.add(new Paragraph(str));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    /**
     * remove the document
     */
    public static void delete() {
        File f = new File(filePath);
        f.delete();
    }
}
