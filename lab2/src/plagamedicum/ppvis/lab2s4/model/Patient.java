package plagamedicum.ppvis.lab2s4.model;

import javafx.scene.control.DatePicker;
import net.sf.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;

public class Patient {
	private SNP snp;
	private SNP vrach;
	private LocalDate Birth_date;
	private String Address;
	private String Conclusion;
	private LocalDate Rec_date;

	public Patient(SNP snp, LocalDate BD, String Address, LocalDate RD, String Conc, SNP vr){
		this.snp      = snp;
		this.Birth_date    = BD;
		this.Rec_date    = RD;
		this.Address = Address;
		this.Conclusion = Conc;
		this.vrach = vr;
	}

	public SNP getSnp(){
		return snp;
	}

	public String getSurname(){
		return snp.getSurname();
	}

	public void setSnp(SNP snp){
		this.snp = snp;
	}

	public String getAlignSnp(){
		return snp.getSurname() + " " + snp.getName() + " " + snp.getPatronym();
	}

	public String getAlignVrach(){
		return vrach.getSurname() + " " + vrach.getName() + " " + vrach.getPatronym();
	}

	public void setAlignSnp(String alignSnp){
		this.snp = new SNP(alignSnp);
	}

	public void setAlignVrach(String alignSnp){
		this.vrach = new SNP(alignSnp);
	}

	public LocalDate getBD(){
		return Birth_date;
	}

	public String getAlignBd(){
		return Birth_date.toString();
	}

	public String getAlignRd(){
		return Rec_date.toString();
	}

	public void setDB(LocalDate group){
		this.Birth_date = group;
	}

	public String getAddress(){
		return Address;
	}

	public void setAddress(String Adr){
		this.Address = Adr;
	}

	public String getConclusion(){
		return Conclusion;
	}

	public void setConclusion(String Conc){
		this.Conclusion = Conc;
	}
	public SNP getVrach(){
		return vrach;
	}

	public String getSurnameVarch(){
		return vrach.getSurname();
	}

	public void setVrach(SNP snp){
		this.vrach = snp;
	}

	public LocalDate getRD(){
		return Rec_date;
	}

	public void setrRB(LocalDate group){
		this.Rec_date = group;
	}
}
