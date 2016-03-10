package com.grexoft.resume.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;
import com.grexoft.resume.helpers.SdCardManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public abstract class ResumePdf {
	
//	public static final String IMAGE_HEADING = "Upload Image";
//
//	public static final String CONTACT_HEADING = "Contact Information";
		
	public static final String ACTIVITIES_HEADING = "Activities";

	public static final String OBJECTIVE_HEADING = "Objective";

	public static final String PERSONAL_HEADING = "Personal Information";

	public static final String EDUCATION_HEADING = "Educational Qualification";

	public static final String PROJECTS_HEADING = "Projects";

	public static final String EXPERIENCE_HEADING = "Work Experience";

	public static final String SKILLS_HEADING = "Skills";

	public static final String STRENGTH_HEADING = "Strengths";

	public static final String EXTRA_CURRICULAR_HEADING = "Extra Curricular Activities";

	public static final String HOBBIES_HEADING = "Hobbies";

	public static final String ACHEIVEMENT_HEADING = "Achievements";

	public static final String RESEARCH_HEADING = "Research Papers";

	public static final String REFERENCE_HEADING = "References";

	public static final String DECLARATION_HEADING = "Declaration";

	protected Document document;

	protected Font titleFont, headingFont, contentFont, contentFontItalic;

	protected String templateName;
	protected String fontFileName;
	protected float documentWidth;
	protected boolean isTopAddress;
	protected boolean isLatter;

	Resume resumeData;
	
	Context context;

	protected File resumeFile;

	public ResumePdf(Context context, String templateName) {
		
		this.context = context;

		isTopAddress=false;
		
		resumeData = Resume.getInstance();

		String basePath = Environment.getExternalStorageDirectory().toString()
				+ "/" + Common_Utilty.APPLICATION_DIRECTORY;

		File baseDirectory = new File(basePath);

		baseDirectory.mkdirs();

		File resumeDirectory = new File(baseDirectory.getPath() + "/"
				+ Common_Utilty.RESUME_DIRECTORY);

		resumeDirectory.mkdirs();

		setTemplateSpecifications(templateName);
		
		int index = resumeData.date_of_creation.indexOf(" ");
		
		resumeData.date_of_creation = replacement(resumeData.date_of_creation, ":", "_");
		
		resumeData.date_of_creation = replacement(resumeData.date_of_creation, " ", "_");
		
		resumeData.date_of_creation = replacement(resumeData.date_of_creation, "-", "_");
		
		this.templateName = replacement(templateName, " ", "_") + "_" + resumeData.date_of_creation + ".pdf";
		
		
		
		System.out.println("date create : " +templateName);

		resumeFile = new File(resumeDirectory, this.templateName);
		

		if (resumeFile.exists())
			resumeFile.delete();


	}
	
	
	private String replacement(String str, String find, String replace) {
		while (str.contains(find)) {

			int index = str.indexOf(find);

			str = str.substring(0, index ) + replace + str.substring(index + 1);

		}
		return str;
	}


	protected void createDocument(){
		
		document = new Document(PageSize.A4, 50, 50, 50, 50);
		
		documentWidth= document.getPageSize().getWidth()
				- document.leftMargin() - document.rightMargin();
		setFonts();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(resumeFile));
		} catch (FileNotFoundException | DocumentException e) {

			e.printStackTrace();
		}
	}
	protected static LineSeparator getLine(float width,BaseColor color) {
		LineSeparator line=new LineSeparator();
		line.setLineColor(color);
		line.setLineWidth(width);
		return line;
		
	}
	protected static String getFiller(Float f1, Float f2){
		Float diff = Math.abs(f1 - f2);
		String filler = "";
		for(;;){
			filler = filler + " ";
			if((new Chunk(filler)).getWidthPoint() >= diff) break;
		}
		return filler;
	}

	protected void setFonts() {

		BaseFont baseFont = null;

		String fontDirectoryName = null;
		SdCardManager.CopyAssetsFontsToSdCard(context);
		try {
			fontDirectoryName = Environment.getExternalStorageDirectory()
					.toString()
					+ "/"
					+ Common_Utilty.APPLICATION_DIRECTORY
					+ "/" + Common_Utilty.FONT_DIRECTORY;

		
				baseFont = BaseFont.createFont(fontDirectoryName + "/"+fontFileName,
						"UTF-8", BaseFont.EMBEDDED);
			
		
		
		

		} catch (DocumentException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

//			SdCardManager.CopyAssetsFontsToSdCard(context);
//
//			try {
//				fontDirectoryName = Environment.getExternalStorageDirectory()
//						.toString()
//						+ "/"
//						+ Common_Utilty.APPLICATION_DIRECTORY
//						+ "/" + Common_Utilty.FONT_DIRECTORY;
//
//				baseFont = BaseFont.createFont(fontDirectoryName + "/"+fontFileName,
//						"UTF-8", BaseFont.EMBEDDED);
//			
//			
//
//			} catch (DocumentException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}

		}

		titleFont = new Font(baseFont, 16 ,Font.BOLD);

		headingFont = new Font(baseFont, 12, Font.BOLD);

		contentFont = new Font(baseFont, 12);

		contentFontItalic = new Font(baseFont, 12 ,Font.ITALIC);


	}

	protected PdfPTable getBlockBodyTable(String block) throws DocumentException {
		System.out.println("In get block table" +block);
		PdfPTable eduTable=new PdfPTable(2);
		PdfPCell cell1=new PdfPCell();
		cell1.setBorder(Rectangle.LEFT);
		cell1.setBorderWidth(0.2f);
	
		

		cell1.setBorderColor(BaseColor.BLACK);
		
		PdfPCell cell2=new PdfPCell();
		cell2.disableBorderSide(Rectangle.BOX);
		if(block.equals(PERSONAL_HEADING)){
			cell2.addElement(getPersonalDetailBodyParagraph());
			System.out.println("In if"+cell2);
		}
		else
		{
			cell2.addElement(getBlockBodyParagraph(block));
			System.out.println("In else cell2" +block +"and"+getBlockBodyParagraph(block) +"and cell2"+cell2);
		}
	
		eduTable.addCell(cell1);
		eduTable.addCell(cell2);
		eduTable.setWidthPercentage(97.8f);
		eduTable.setWidths(new float[]{0.5f,9.5f});
		return eduTable;
		
	}
	
	protected PdfPTable getExtraBorderTable() {
		
		PdfPTable bordrExtra=new PdfPTable(1);
		PdfPCell extraBordercell=new PdfPCell();
		extraBordercell.setBorder(Rectangle.LEFT);
		extraBordercell.setBorderColor(BaseColor.BLACK);
		extraBordercell.setBorderWidth(0.2f);
		bordrExtra.addCell(extraBordercell);
		bordrExtra.setWidthPercentage(97.8f);
		bordrExtra.setSpacingBefore(-10);
		return bordrExtra;
		
	}
	protected Paragraph getBlockParagraph(String blockName) throws DocumentException{

		System.out.println("getBlockParagraph() for block : " + blockName);

		Paragraph block = new Paragraph();

		block.add(getBlockHeadingParagraph(blockName));
		
		Paragraph blockBody=new Paragraph();
		
		if (blockName == PERSONAL_HEADING){
			
			System.out.println("getPersonalDetailBodyParagraph");
			
			blockBody = getPersonalDetailBodyParagraph();
			System.out.println("blockbody " + blockBody);
			
		}
		
		else {
			
			
			
			blockBody = getBlockBodyParagraph(blockName);
			
		}		

		block.add(blockBody);
		System.out.println(block);

		onBlockCreated(block, blockName);

		return block;

	}
	
	
	protected Paragraph getPersonalDetailBodyParagraph(){
		
		
		System.out.println("inside getPersonaldetailBody paragarph");
	
		Paragraph personalBodyPara = new Paragraph();
		
		PdfPTable personalBody = new PdfPTable(3);
		if(Common_Utilty.isNotEmptyString(resumeData.name)){
			addPersonalDetailCell(personalBody, "Name", resumeData.name);
		}
		if(Common_Utilty.isNotEmptyString(resumeData.gender.getString())  && !resumeData.gender.getString().equals("Not specified")){
			addPersonalDetailCell(personalBody, "Gender", resumeData.gender.getString());
		}
		if(Common_Utilty.isNotEmptyString(resumeData.dob)){
			addPersonalDetailCell(personalBody, "Date of birth", resumeData.dob);
		}
		if (Common_Utilty.isNotEmptyString(resumeData.nationality)){
			addPersonalDetailCell(personalBody, "Nationality", resumeData.nationality);
		}
		if(Common_Utilty.isNotEmptyString(resumeData.language)){
			addPersonalDetailCell(personalBody, "Languages known", resumeData.language);
		}
		if(Common_Utilty.isNotEmptyString(resumeData.fatherName)){
			addPersonalDetailCell(personalBody, "Father's name", resumeData.fatherName);
			
		}
		if(Common_Utilty.isNotEmptyString(resumeData.motherName)){
			addPersonalDetailCell(personalBody, "Mother's name", resumeData.motherName);
			
		}
		
		
		if(resumeData.customFields !=null && !resumeData.customFields.isEmpty()){
			for(Map.Entry entry : resumeData.customFields.entrySet()){
				
				addPersonalDetailCell(personalBody, entry.getKey().toString(), entry.getValue().toString());
		}
		
		}
		
		String presentAddressStr = "";

		String permanentAddressStr = "";

		if (Common_Utilty.isNotEmptyString(resumeData.city)) {
			if (Common_Utilty.isNotEmptyString(resumeData.street)) {
				presentAddressStr = presentAddressStr + resumeData.street + ",";

			}
			presentAddressStr = presentAddressStr + resumeData.city;
			if (Common_Utilty.isNotEmptyString(resumeData.pincode)) {
				presentAddressStr = presentAddressStr + "-" + resumeData.pincode;
			}
		}

		if (Common_Utilty.isNotEmptyString(resumeData.city) && Common_Utilty.isNotEmptyString(resumeData.country)) {
			presentAddressStr = presentAddressStr + ", ";
		}

		if (Common_Utilty.isNotEmptyString(resumeData.country)) {

			if (Common_Utilty.isNotEmptyString(resumeData.state)) {
				presentAddressStr = presentAddressStr + resumeData.state + ",";

			}
			presentAddressStr = presentAddressStr + resumeData.country;
		}
		
		
		
		if (Common_Utilty.isNotEmptyString(resumeData.city2)) {
			if (Common_Utilty.isNotEmptyString(resumeData.street2)) {
				permanentAddressStr = permanentAddressStr + resumeData.street2+ ",";

			}
			permanentAddressStr = permanentAddressStr + resumeData.city2;
			if (Common_Utilty.isNotEmptyString(resumeData.pincode2)) {
				permanentAddressStr = permanentAddressStr + "-"
						+ resumeData.pincode2;
			}
		}

		if (Common_Utilty.isNotEmptyString(resumeData.city2)
				&& Common_Utilty.isNotEmptyString(resumeData.country2)) {
			permanentAddressStr = permanentAddressStr + ", ";
		}

		if (Common_Utilty.isNotEmptyString(resumeData.country2)) {

			if (Common_Utilty.isNotEmptyString(resumeData.state2)) {
				permanentAddressStr = permanentAddressStr + resumeData.state2
						+ ",";

			}
			permanentAddressStr = permanentAddressStr + resumeData.country2;
		}
		
		
		if(resumeData.addressesAreSame){
			
			if(!presentAddressStr.equals("")){
				
			addPersonalDetailCell(personalBody, "Address", presentAddressStr);
			}
		}
		
		else if(isTopAddress){
			

//			if (Common_Utilty.isNotEmptyString(resumeData.city2)) {
//				if (Common_Utilty.isNotEmptyString(resumeData.street2)) {
//					permanentAddressStr = permanentAddressStr + resumeData.street2+ ",";
//
//				}
//				permanentAddressStr = permanentAddressStr + resumeData.city2;
//				if (Common_Utilty.isNotEmptyString(resumeData.pincode2)) {
//					permanentAddressStr = permanentAddressStr + "-"
//							+ resumeData.pincode2;
//				}
//			}
//
//			if (Common_Utilty.isNotEmptyString(resumeData.city2)
//					&& Common_Utilty.isNotEmptyString(resumeData.country2)) {
//				permanentAddressStr = permanentAddressStr + ", ";
//			}
//
//			if (Common_Utilty.isNotEmptyString(resumeData.country2)) {
//
//				if (Common_Utilty.isNotEmptyString(resumeData.state2)) {
//					permanentAddressStr = permanentAddressStr + resumeData.state2
//							+ ",";
//
//				}
//				permanentAddressStr = permanentAddressStr + resumeData.country2;
//			}

			if (!permanentAddressStr.equals("")) {

				addPersonalDetailCell(personalBody, "Parmanent address", permanentAddressStr);
			}
		}
		
		else {
			if(!presentAddressStr.equals("") ){
			addPersonalDetailCell(personalBody, "Present address",presentAddressStr);
			}
		



			if (!permanentAddressStr.equals("")) {

				addPersonalDetailCell(personalBody, "Parmanent address", permanentAddressStr);
			}
		}
		
			
		

		personalBody.setWidthPercentage(100f);
		try {
			personalBody.setWidths(new float[]{3.0f, 1.0f, 6.0f});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			//Toast.makeText(context, "document exception while setting widths", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		
		System.out.println("personal body" +personalBody);
		
		System.out.println("height of personal body : " + personalBody.getTotalHeight());
		
		personalBodyPara.add(personalBody);	
		
		System.out.println("personalBodyPara" + personalBodyPara);
		
		return personalBodyPara;
	}
	
	
	private void addPersonalDetailCell(PdfPTable table, String key, String value){
		
		Phrase c1 = new Phrase(key, getContentFont());
		PdfPCell name = new PdfPCell(c1);
		name.setBorder(Rectangle.NO_BORDER);
		name.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		PdfPCell colon = new PdfPCell(new Phrase(" :", getContentFont()));
		colon.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		colon.setBorder(Rectangle.NO_BORDER);
		Phrase c2 = new Phrase(value, getContentFont());
		PdfPCell valueCell = new PdfPCell(c2);
		valueCell.setBorder(Rectangle.NO_BORDER);
		valueCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		
		table.addCell(name);
		table.addCell(colon);
		table.addCell(valueCell);
		
		System.out.println("cell added to tableBody");
		
		
		
	}
	

	protected void onBlockCreated(Paragraph block, String blockName) {

	}

	public String createPdf() {

		try {
			
			createDocument();

			document.open();

			System.out.println("document is : " + document.toString());

			setTitleOfDocument();
			if(Common_Utilty.isNotEmptyString(resumeData.objectives)){

				Paragraph objective = getBlockParagraph(OBJECTIVE_HEADING);

				document.add(objective);
			}

			
			if (resumeData.educationDetail !=null && !resumeData.educationDetail.isEmpty()){
				document.add(getBlockParagraph(EDUCATION_HEADING));
			}
			if(resumeData.workExperience !=null && !resumeData.workExperience.isEmpty()){
				document.add(getBlockParagraph(EXPERIENCE_HEADING));
			}
			
			if(resumeData.project !=null && !resumeData.project.isEmpty()){
				document.add(getBlockParagraph(PROJECTS_HEADING));
				
			}
			if(resumeData.research !=null && !resumeData.research.isEmpty()){
				document.add(getBlockParagraph(RESEARCH_HEADING));
			}
			

			if (resumeData.skills != null && !resumeData.skills.isEmpty()) {

				document.add(getBlockParagraph(SKILLS_HEADING));

			}

			if (resumeData.achive != null && !resumeData.achive.isEmpty()) {

				document.add(getBlockParagraph(ACHEIVEMENT_HEADING));

			}
			

			if (resumeData.exCarr != null && !resumeData.exCarr.isEmpty()) {

				document.add(getBlockParagraph(EXTRA_CURRICULAR_HEADING));

			}

			if (resumeData.hobbies != null && !resumeData.hobbies.isEmpty()) {

				document.add(getBlockParagraph(HOBBIES_HEADING));

			}
			if (resumeData.strength != null && !resumeData.strength.isEmpty()) {

				document.add(getBlockParagraph(STRENGTH_HEADING));

			}
			
			
			
			System.out.println("get personldetail body"+getPersonalDetailBodyParagraph()+".");
			
			if(!getPersonalDetailBodyParagraph().equals("")){
				document.add(getBlockParagraph(PERSONAL_HEADING));
			}
			
			
			if(resumeData.reference !=null && !resumeData.reference.isEmpty()){
				document.add(getBlockParagraph(REFERENCE_HEADING));
		}
			if(Common_Utilty.isNotEmptyString(resumeData.declaration) && Common_Utilty.isNotEmptyString(resumeData.name)){
				
				document.add(getBlockParagraph(DECLARATION_HEADING));
				
			}
			
			



		
		}
		catch (DocumentException e) {
			e.printStackTrace();
		} finally {

			document.close();
			
			File dir=new File( Environment.getExternalStorageDirectory()
					.toString()
					+ "/"
					+ Common_Utilty.APPLICATION_DIRECTORY
					+ "/" + Common_Utilty.FONT_DIRECTORY);
			
			if(dir.exists()){
				SdCardManager.deleteDirectory(dir);
			}
				
			
			
		}
		return templateName;
		

	}

	protected abstract void setTitleOfDocument() throws DocumentException;

	protected abstract void setTemplateSpecifications(String templateName);

	protected abstract Paragraph getBlockHeadingParagraph(String blockName);

	protected abstract Paragraph getBlockBodyParagraph(String blockName) throws DocumentException;

	protected abstract Font getHeadingFont();

	

	protected abstract Font getContentFont();

	

	protected abstract Font getContentFontItalic();

	

	protected abstract Font getTitleFont();

	
	
	

	
	
	

}
