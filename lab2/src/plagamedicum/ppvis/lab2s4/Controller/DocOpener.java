package plagamedicum.ppvis.lab2s4.Controller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import plagamedicum.ppvis.lab2s4.model.Exam;
import plagamedicum.ppvis.lab2s4.model.SNP;
import plagamedicum.ppvis.lab2s4.model.Patient;
import java.util.Date;
import java.time.LocalDate;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocOpener {
    private static SNP patient;
    private static SNP vrach;
    private static LocalDate Birth_date;
    private static String Address;
    private static String Conclusion;
    private static LocalDate Rec_date;
    private static List<Patient> patientList;

    public static List<Patient> openDoc (File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory parserFactory;
        SAXParser parser;
        XMLHandler handler;

        patientList = new ArrayList<>();

        handler = new XMLHandler();
        parserFactory = SAXParserFactory.newInstance();
        parser = parserFactory.newSAXParser();
        parser.parse(file, handler);
        return patientList;
    }

    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
            if(qName.equals("snp")){
                patient = new SNP(
                    attributes.getValue("surname"),
                    attributes.getValue("name"),
                    attributes.getValue("patronym")
                );
            }
            if(qName.equals("doctor")){
                vrach = new SNP(
                        attributes.getValue("surname"),
                        attributes.getValue("name"),
                        attributes.getValue("patronym")
                );
            }
            if(qName.equals("birth_date")){
                Birth_date = LocalDate.of(
                        Integer.parseInt(attributes.getValue("year")),
                        Integer.parseInt(attributes.getValue("month")),
                        Integer.parseInt(attributes.getValue("day"))
                );
            }
            if(qName.equals("receipt_date")){
                Rec_date = LocalDate.of(
                        Integer.parseInt(attributes.getValue("year")),
                        Integer.parseInt(attributes.getValue("month")),
                        Integer.parseInt(attributes.getValue("day"))
                );
            }
            if(qName.equals("adr")){
                Address = new String(attributes.getValue("address"));
            }
            if(qName.equals("conc")){
                Conclusion = new String(attributes.getValue("conclusion"));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException{
            if(qName.equals("patient")){
                patientList.add(new Patient(patient, Birth_date, Address, Rec_date, Conclusion, vrach));
            }
        }
    }
}
