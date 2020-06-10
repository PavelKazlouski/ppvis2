package plagamedicum.ppvis.lab2s4.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {
    private List<Patient> patientList;

    public Model(int patientNumber){
        if(patientNumber > 0){
            patientList = generatePatients(patientNumber);
        } else {
            patientList = new ArrayList<>();
        }
    }

    private List<Patient> generatePatients(int entitiesNumber){
        List<Patient> patients = new ArrayList<>();

        for(int i = 0; i < entitiesNumber; i++){
            patients.add(
                    new Patient(new SNP(RandomizationData.reqSurname(), RandomizationData.reqName(), RandomizationData.reqPatronym()), RandomizationData.reqBirthDate(), RandomizationData.reqAddress(), RandomizationData.reqRecDate(), RandomizationData.reqConclusion(), new SNP(RandomizationData.reqSurname(), RandomizationData.reqName(), RandomizationData.reqPatronym()))
            );
        }
        return patients;
    }

    public List<Patient> getPatientList(){
        return patientList;
    }

    public void setPatientList(List<Patient> patientList){
        this.patientList = patientList;
    }

    public void addPatient(Patient patient){
        patientList.add(patient);
    }

}
