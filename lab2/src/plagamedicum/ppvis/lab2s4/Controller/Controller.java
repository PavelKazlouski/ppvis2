package plagamedicum.ppvis.lab2s4.Controller;
import java.time.LocalDate;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import plagamedicum.ppvis.lab2s4.model.Exam;
import plagamedicum.ppvis.lab2s4.model.Model;
import plagamedicum.ppvis.lab2s4.model.SNP;
import plagamedicum.ppvis.lab2s4.model.Patient;

public class Controller {
    private Model model;

    public Controller(Model model){
        this.model = model;
    }

    public List<Patient> getPatientList(){
        return model.getPatientList();
    }

    public void newDoc(int patientNumber){
        this.model = new Model(patientNumber);
    }

    public void addPatient(String surname, String name, String patronym,  LocalDate BD, String Address, LocalDate RD, String Conc, String vrName, String vrSurname, String vrPatronym){
        model.addPatient(
                new Patient(new SNP(surname, name, patronym), BD, Address, RD, Conc, new SNP(vrSurname, vrName, vrPatronym))
        );
    }

    public void openDoc(File file) {
        try {
            model.setPatientList(DocOpener.openDoc(file));
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public void saveDoc(File file) {
        List<Patient> patientList = model.getPatientList();
        Element patients;
        Element patient;
        Element snp;
        Element doctor;
        Element birth_date;
        Element date_of_receipt;
        Element adr;
        Element conc;
        Attr conclusion;
        Attr address;
        Attr surname;
        Attr name;
        Attr patronym;
        Attr year;
        Attr month;
        Attr day;
        Document doc;
        DocumentBuilderFactory docBuilderFactory;
        DocumentBuilder docBuilder;
        TransformerFactory transformerFactory;
        Transformer transformer;
        DOMSource source;
        StreamResult streamResult;

        try{
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            patients = doc.createElement("patients");
            doc.appendChild(patients);

            for (Patient patienti : patientList){
                surname = doc.createAttribute("surname");
                surname.setValue(patienti.getSnp().getSurname());
                name = doc.createAttribute("name");
                name.setValue(patienti.getSnp().getName());
                patronym = doc.createAttribute("patronym");
                patronym.setValue(patienti.getSnp().getPatronym());
                snp = doc.createElement("snp");
                snp.setAttributeNode(surname);
                snp.setAttributeNode(name);
                snp.setAttributeNode(patronym);

                surname = doc.createAttribute("surname");
                surname.setValue(patienti.getVrach().getSurname());
                name = doc.createAttribute("name");
                name.setValue(patienti.getVrach().getName());
                patronym = doc.createAttribute("patronym");
                patronym.setValue(patienti.getVrach().getPatronym());
                doctor = doc.createElement("doctor");
                doctor.setAttributeNode(surname);
                doctor.setAttributeNode(name);
                doctor.setAttributeNode(patronym);

                address = doc.createAttribute("address");
                address.setValue(patienti.getAddress());
                adr = doc.createElement("adr");
                adr.setAttributeNode(address);

                conclusion = doc.createAttribute("conclusion");
                conclusion.setValue(patienti.getConclusion());
                conc = doc.createElement("conc");
                conc.setAttributeNode(conclusion);

                year = doc.createAttribute("year");
                year.setValue(Integer.toString(patienti.getBD().getYear()));
                month = doc.createAttribute("month");
                month.setValue(Integer.toString(patienti.getBD().getMonthValue()));
                day = doc.createAttribute("day");
                day.setValue(Integer.toString(patienti.getBD().getDayOfMonth()));
                birth_date = doc.createElement("birth_date");
                birth_date.setAttributeNode(year);
                birth_date.setAttributeNode(month);
                birth_date.setAttributeNode(day);

                year = doc.createAttribute("year");
                year.setValue(Integer.toString(patienti.getRD().getYear()));
                month = doc.createAttribute("month");
                month.setValue(Integer.toString(patienti.getRD().getMonthValue()));
                day = doc.createAttribute("day");
                day.setValue(Integer.toString(patienti.getRD().getDayOfMonth()));
                date_of_receipt = doc.createElement("receipt_date");
                date_of_receipt.setAttributeNode(year);
                date_of_receipt.setAttributeNode(month);
                date_of_receipt.setAttributeNode(day);

                patient = doc.createElement("patient");
                patient.appendChild(snp);
                patient.appendChild(doctor);
                patient.appendChild(adr);
                patient.appendChild(conc);
                patient.appendChild(birth_date);
                patient.appendChild(date_of_receipt);
                patients.appendChild(patient);
            }

            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(doc);
            streamResult = new StreamResult(file);
            transformer.transform(source, streamResult);
        } catch (Exception  exception){
            exception.printStackTrace();
            return;
        }
    }

    public List search(String selectedItem, List<String> criteriaList){
        final String CRITERIA_1  = "Фамилия пациента или адрес прописки";
        final String CRITERIA_2  = "Дата рождения";
        final String CRITERIA_3  = "ФИО врача или дата последнего приема";
        List<Patient> patientList = getPatientList();
        List resultList;

        resultList = new ArrayList<Patient>();

        switch (selectedItem){
            case CRITERIA_1:
                final String ADDRESS = criteriaList.get(0);
                final String SURNAME = criteriaList.get(1);
                for(Patient patient:patientList){
                    if(patient.getSnp().getSurname().equals(SURNAME) || patient.getAddress().equals(ADDRESS)){
                        resultList.add(patient);
                    }
                }
                break;
            case CRITERIA_2:
                final String BirthDate = criteriaList.get(2);
                for(Patient patient:patientList) {
                    if (patient.getBD().toString().equals(BirthDate)) {
                        resultList.add(patient);
                    }
                }
                break;
            case CRITERIA_3:
                final String  Doctor_Surname = criteriaList.get(3);
                final String  Doctor_Name = criteriaList.get(4);
                final String  Doctor_Patronym = criteriaList.get(5);
                final String Date_of_recent = criteriaList.get(6);

                for(Patient patient:patientList) {
                    if (patient.getVrach().getSurname().equals(Doctor_Surname) || patient.getVrach().getName().equals(Doctor_Name) || patient.getVrach().getPatronym().equals(Doctor_Patronym) || patient.getRD().toString().equals(Date_of_recent)) {
                        resultList.add(patient);
                    }
                }
                break;
        }

        return resultList;
    }

    public void delete(List<Patient> indexList){
        for(Patient patient:indexList){
            getPatientList().remove(patient);
        }
    }
}
