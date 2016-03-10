package com.grexoft.resume.pdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;

import com.grexoft.resume.helpers.Common_Utilty;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class ImpressiveResumePdf extends ResumePdf {

	public static final String TITLE = "title";

	public ImpressiveResumePdf(Context context, String templateName) {
		super(context, templateName);
		isTopAddress = true;
	}

	@Override
	protected void setTemplateSpecifications(String templateName) {
		this.templateName = templateName;
		fontFileName = "calibri.ttf";

	}

	
	protected void onBlockCreated(Paragraph block, String blockName) {

		block.setSpacingBefore(10);
	}

	protected Paragraph getHeaderOfDocument() {  Paragraph titlePara = new Paragraph();

	  String name = Common_Utilty.isNotEmptyString(resumeData.name)?resumeData.name:"";

	  Chunk nameChunk = new Chunk(name.toUpperCase(Locale.US), titleFont);

	  titlePara.add(nameChunk);

	 

	  String presentAddressStr = "";

	  if (Common_Utilty.isNotEmptyString(resumeData.city)) {
	   if (Common_Utilty.isNotEmptyString(resumeData.street)) {
	    presentAddressStr = presentAddressStr + resumeData.street
	      + ",\n";

	   }
	   presentAddressStr = presentAddressStr + resumeData.city;
	   if (Common_Utilty.isNotEmptyString(resumeData.pincode)) {
	    presentAddressStr = presentAddressStr + "-"
	      + resumeData.pincode;
	   }
	  }

	  if (Common_Utilty.isNotEmptyString(resumeData.city)
	    && Common_Utilty.isNotEmptyString(resumeData.country)) {
	   presentAddressStr = presentAddressStr + ",\n";
	  }

	  if (Common_Utilty.isNotEmptyString(resumeData.country)) {

	   if (Common_Utilty.isNotEmptyString(resumeData.state)) {
	    presentAddressStr = presentAddressStr + resumeData.state + ",";

	   }
	   presentAddressStr = presentAddressStr + resumeData.country;
	  }

	  String presentAdd=Common_Utilty.isNotEmptyString(presentAddressStr)?"\n"+presentAddressStr:"";
	  
	  Chunk presentAddChunk = new Chunk(presentAdd, contentFont);
	  titlePara.add(presentAddChunk);

	  String contactstr =Common_Utilty.isNotEmptyString(resumeData.primarycontact) ? resumeData.primarycontact
	    : "";

	  Chunk contactHeading = new Chunk("Contact : ", headingFont);
	  Chunk contactBodyChunk = new Chunk(contactstr, contentFont);

	  Phrase contactPhrase = new Phrase();

	  if(Common_Utilty.isNotEmptyString(contactstr)){
	   contactPhrase.add(new Chunk("\n",contentFont));
	   contactPhrase.add(contactHeading);
	   contactPhrase.add(contactBodyChunk);
	  }
	  

	  
	  titlePara.add(contactPhrase);

	  String emailstr = (Common_Utilty.isNotEmptyString(resumeData.email)) ? resumeData.email
	    : "";

	  Chunk emailHeading = new Chunk("E-mail : ", headingFont);
	  Chunk emailBodyChunk = new Chunk(emailstr, contentFont);

	  Phrase emailPhrase = new Phrase();

	 
	  if (Common_Utilty.isNotEmptyString(emailstr)) {
	   
	   emailPhrase.add(new Chunk("\n",contentFont));
	   emailPhrase.add(emailHeading);
	   emailPhrase.add(emailBodyChunk);

	  }

	  titlePara.add(emailPhrase);
	  titlePara.setAlignment(Paragraph.ALIGN_RIGHT);
	  titlePara.setSpacingAfter(5);
	  titlePara.setSpacingBefore(5);
	  return titlePara;}

	@Override
	protected void setTitleOfDocument() throws DocumentException {
		try {

			if (!document.isOpen())
				return;

			Paragraph headParagraph = new Paragraph();
			String titleOfResume=(Common_Utilty.isNotEmptyString(resumeData.title)&& resumeData.showTitle)?resumeData.title:"resume";
			
			Chunk headChunk = new Chunk(titleOfResume.toUpperCase(Locale.US), titleFont);
			headParagraph.add(headChunk);
			headParagraph.setAlignment(Paragraph.ALIGN_CENTER);
			headParagraph.add(new Chunk("\n"));
			
			headParagraph.add(getLine(0.8f, BaseColor.LIGHT_GRAY));
			headParagraph.setSpacingAfter(25);
			document.add(headParagraph);

		} catch (DocumentException e) {

			e.printStackTrace();

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	protected Paragraph getPersonalInfo() {
		
		
		
		Paragraph personalInfoPara = new Paragraph();
		
		Paragraph personalInfoHeading=new Paragraph();
		
		String personalInfo = "";
		
		personalInfoHeading.add(new Chunk(PERSONAL_HEADING.toUpperCase(Locale.US) +"\n", headingFont));
		
		personalInfoHeading.setSpacingAfter(5);
		if (Common_Utilty.isNotEmptyString(resumeData.name)) {
			personalInfo = personalInfo +"Name : " + resumeData.name;
		}
		if (Common_Utilty.isNotEmptyString(resumeData.gender.getString()) && !resumeData.gender.getString().equals("Not specified")) {
			personalInfo = personalInfo + "\n" + "Gender : "
					+ resumeData.gender.getString();
		}
		if (Common_Utilty.isNotEmptyString(resumeData.dob)) {
			personalInfo = personalInfo + "\n" + "Date of birth : "
					+ resumeData.dob;
		}
		if (Common_Utilty.isNotEmptyString(resumeData.nationality)) {
			personalInfo = personalInfo + "\n" + "Nationality : "
					+ resumeData.nationality;
		}
		if (Common_Utilty.isNotEmptyString(resumeData.language)) {
			personalInfo = personalInfo + "\n" + "Languages known : "
					+ resumeData.language;
		}
		if (Common_Utilty.isNotEmptyString(resumeData.fatherName)) {
			personalInfo = personalInfo + "\n" + "Father's name : "
					+ resumeData.fatherName;

		}
		if (Common_Utilty.isNotEmptyString(resumeData.motherName)) {
			personalInfo = personalInfo + "\n" + "Mother's name : "
					+ resumeData.motherName;

		}

		if (resumeData.customFields != null
				&& !resumeData.customFields.isEmpty()) {
			for (Map.Entry entry : resumeData.customFields.entrySet()) {

				personalInfo = personalInfo + "\n"
						+ entry.getKey().toString() + " : "
						+ entry.getValue().toString();

			}
		}

		String permanentAddressStr = "";

		if (Common_Utilty.isNotEmptyString(resumeData.city2)) {
			if (Common_Utilty.isNotEmptyString(resumeData.street2)) {
				permanentAddressStr = permanentAddressStr
						+ resumeData.street2 + ",";

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
				permanentAddressStr = permanentAddressStr
						+ resumeData.state2 + ",";

			}
			permanentAddressStr = permanentAddressStr + resumeData.country2;
		}

		if(resumeData.addressesAreSame){
			if (Common_Utilty.isNotEmptyString(permanentAddressStr)) {
				personalInfo = personalInfo + "\n" + "Address : "
						+ permanentAddressStr;
			}
		}
		else{
			if (Common_Utilty.isNotEmptyString(permanentAddressStr)) {
				personalInfo = personalInfo + "\n" + "Parmanent address : "
						+ permanentAddressStr;
			}
		}
		
		Chunk personalInfoChunk = new Chunk(personalInfo, contentFont);

		personalInfoPara.add(personalInfoHeading);
		personalInfoPara.add(personalInfoChunk);


		return personalInfoPara;
		
		
	}

	protected PdfPTable getAggrigateBlock(ArrayList<String> blocks)
			throws DocumentException {
		
		System.out.println("blocks are : " + blocks);

		Paragraph aggrigateParagarph = new Paragraph();

		PdfPTable aggrigateTable = new PdfPTable(2);

		PdfPCell aggrigateCell1 = new PdfPCell();

		aggrigateCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);

		aggrigateCell1.setBorder(Rectangle.NO_BORDER);

		PdfPCell aggrigateCell2 = new PdfPCell();

		aggrigateCell2.setPadding(15);

		aggrigateCell2.setBorderColor(BaseColor.LIGHT_GRAY);

		if (blocks != null && !blocks.isEmpty()) {
			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i).equals(TITLE)) {
					aggrigateParagarph.add(getHeaderOfDocument());
				}
				else if(blocks.get(i).equals(PERSONAL_HEADING)){
					aggrigateParagarph.add(getPersonalInfo());
				}
				
				else {
					aggrigateParagarph.add(getBlockParagraph(blocks.get(i)));
				}

			}
			aggrigateCell2.addElement(aggrigateParagarph);
			aggrigateTable.addCell(aggrigateCell1);

			aggrigateTable.addCell(aggrigateCell2);
		}

		
		
		aggrigateTable.setWidthPercentage(100);
		aggrigateTable.setWidths(new float[] { (float) 1.0, (float) 19.0 });
		aggrigateTable.setSpacingAfter(10);
		// customParagarph.add(customTable);

		return aggrigateTable;

	}

	@Override
	protected Paragraph getBlockHeadingParagraph(String blockName) {
		System.out.println("inside getBlockheadingparagraph(g) . blockname : "
				+ blockName);
		Paragraph heading = new Paragraph();

		Chunk headChunk = new Chunk(blockName.toUpperCase(Locale.US) + "\n",
				headingFont);		

		heading.add(headChunk);
		
		heading.setSpacingAfter(10);

		System.out.println(heading);
		return heading;

	}

	protected Paragraph getDeclarationOfDocument() throws DocumentException {

		Paragraph declarationParagraph = new Paragraph();
		Chunk headChunk = new Chunk(DECLARATION_HEADING.toUpperCase(Locale.US), titleFont);

		
		declarationParagraph.add(getLine(0.8f, BaseColor.LIGHT_GRAY));
		

		declarationParagraph.add(new Chunk("\n\n"));

		declarationParagraph.add(headChunk);
		
		declarationParagraph.add(new Chunk("\n"));
		declarationParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		
		Paragraph bodyPara = new Paragraph(new Chunk(resumeData.declaration, contentFont));
		bodyPara.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		bodyPara.setSpacingAfter(30);
		
		declarationParagraph.add(bodyPara);
		

		Chunk date=new Chunk("Date : ",contentFont);
		Chunk place = new Chunk("Place : ", contentFont);
		Chunk name = new Chunk(resumeData.name, contentFont);
		float gap = documentWidth  - place.getWidthPoint() - 20;
		String cfiller = ResumePdf.getFiller(gap, name.getWidthPoint());
//		blockBody.add(line);
		
		
		declarationParagraph.add(date);
		declarationParagraph.add("\n");
		declarationParagraph.add(place);
		declarationParagraph.add(new Chunk(cfiller));
		declarationParagraph.add(name);
		
		

		return declarationParagraph;

	}

	@Override
	public String createPdf() {

		try {
			createDocument();

			document.open();

			System.out.println("document is : " + document.toString());

			setTitleOfDocument();

			ArrayList<String> blocks = new ArrayList<>();

			
				
				blocks.add(TITLE);
			
			

			document.add(getAggrigateBlock(blocks));

			blocks = new ArrayList<String>();

			if (Common_Utilty.isNotEmptyString(resumeData.objectives)) {

				blocks.add(OBJECTIVE_HEADING);
			}

			if (resumeData.educationDetail != null
					&& !resumeData.educationDetail.isEmpty()) {
				blocks.add(EDUCATION_HEADING);
			}

			if (resumeData.skills != null && !resumeData.skills.isEmpty()) {
				blocks.add(SKILLS_HEADING);
			}

			document.add(getAggrigateBlock(blocks));

			blocks = new ArrayList<String>();
			if (resumeData.workExperience != null
					&& !resumeData.workExperience.isEmpty()) {
				blocks.add(EXPERIENCE_HEADING);
			}
			if (resumeData.project != null && !resumeData.project.isEmpty()) {
				blocks.add(PROJECTS_HEADING);
			}
			document.add(getAggrigateBlock(blocks));

			blocks = new ArrayList<String>();
			if (resumeData.research != null && !resumeData.research.isEmpty()) {
				blocks.add(RESEARCH_HEADING);
			}
			if (resumeData.strength != null && !resumeData.strength.isEmpty()) {
				blocks.add(STRENGTH_HEADING);
			}
			document.add(getAggrigateBlock(blocks));

			blocks = new ArrayList<String>();

			if (resumeData.achive != null && !resumeData.achive.isEmpty()) {
				blocks.add(ACHEIVEMENT_HEADING);
			}
			if (resumeData.exCarr != null && !resumeData.exCarr.isEmpty()) {
				blocks.add(EXTRA_CURRICULAR_HEADING);
			}
			if (resumeData.hobbies != null && !resumeData.hobbies.isEmpty()) {
				blocks.add(HOBBIES_HEADING);
			}
			document.add(getAggrigateBlock(blocks));

			blocks = new ArrayList<String>();

			blocks.add(PERSONAL_HEADING);


			if (resumeData.reference != null && !resumeData.reference.isEmpty()) {
				blocks.add(REFERENCE_HEADING);
			}
			document.add(getAggrigateBlock(blocks));
			
			if( Common_Utilty.isNotEmptyString(resumeData.declaration)){
				document.add(getDeclarationOfDocument());
			}
			
		}

		catch (DocumentException e) {
			e.printStackTrace();
		} finally {

			document.close();
		}
		return templateName;
		
	}

	@Override
	protected Paragraph getBlockBodyParagraph(String blockName)
			throws DocumentException {
		System.out.println("in side blockbodyparagraph");
		Paragraph blockBody = new Paragraph();

	//	blockBody.setIndentationLeft(5);

		blockBody.setIndentationRight(5);

		blockBody.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		
		blockBody.setSpacingBefore(10);

		if (blockName.equals(OBJECTIVE_HEADING)) {

			String objectiveString = resumeData.objectives;

			Chunk objectiveChunk = new Chunk(objectiveString, contentFont);

			blockBody.add(objectiveChunk);

			System.out.println("inside getBlockBodyparagraph() . blockname : "
					+ OBJECTIVE_HEADING);

		}

		else if (blockName.equals(EDUCATION_HEADING)) {

			Paragraph eduPara;

			for (int i = 0; i < resumeData.educationDetail.size(); i++) {
				eduPara = new Paragraph();
				Chunk degree = new Chunk(
						resumeData.educationDetail.get(i).degree, headingFont);
				String eduBody = (Common_Utilty
						.isNotEmptyString(resumeData.educationDetail.get(i).university) ? " from "
						+ resumeData.educationDetail.get(i).university
						: "")
						+ (Common_Utilty
								.isNotEmptyString(resumeData.educationDetail
										.get(i).passout) ? " in the year "
								+ resumeData.educationDetail.get(i).passout
								: "")
						+ (Common_Utilty
								.isNotEmptyString(resumeData.educationDetail
										.get(i).result) ? ". Percentage/CGPA : "
								+ resumeData.educationDetail.get(i).result
								: "");
				Chunk body = new Chunk(eduBody, contentFont);
				Phrase educationPharase = new Phrase();
				educationPharase.add(degree);
				educationPharase.add(body);
				eduPara.add(educationPharase);
				if(i<resumeData.educationDetail.size()-1){
					eduPara.add(new Chunk("\n"));
					eduPara.setSpacingAfter(5);
				}
				
				blockBody.add(eduPara);
			}
		
		}

		else if (blockName.equals(SKILLS_HEADING)) {

			StringBuilder skillStr = new StringBuilder();
			int i = 0;

			for (String skill : resumeData.skills) {

				i++;

				if (i < resumeData.skills.size())
					skillStr.append(skill + '\n');

				else if (i == resumeData.skills.size())
					skillStr.append(skill);

			}

			blockBody.add(new Chunk(skillStr.toString(), contentFont));

			// blockBody.setFont(contentFont);
			System.out.println(blockBody);

		}

		else if (blockName.equals(EXPERIENCE_HEADING)) {
			Paragraph work ;
			for (int i = 0; i < resumeData.workExperience.size(); i++) {
				work = new Paragraph();
				Chunk compName = new Chunk(
						resumeData.workExperience.get(i).compName, headingFont);
				work.add(compName);
				if (Common_Utilty.isNotEmptyString(resumeData.workExperience
						.get(i).timeStart)
						&& Common_Utilty
								.isNotEmptyString(resumeData.workExperience
										.get(i).timeEnd)) {
					String timePeriod = resumeData.workExperience.get(i).timeStart
							+ "-" + resumeData.workExperience.get(i).timeEnd;
					Chunk time = new Chunk(timePeriod, contentFontItalic);

					float compNameWidth = (compName.getWidthPoint());
					float timeWidth = time.getWidthPoint();
					String fillerExp = ResumePdf.getFiller(
							(documentWidth *0.86f- compNameWidth), timeWidth);
					Chunk fillerExperience = new Chunk(fillerExp);

					work.add(fillerExperience);
					work.add(time);
				}

				String workExp = (Common_Utilty
						.isNotEmptyString(resumeData.workExperience.get(i).jobDesig) ? "\n"
						+ "Designation: "
						+ resumeData.workExperience.get(i).jobDesig
						: "")
						+ (Common_Utilty
								.isNotEmptyString(resumeData.workExperience
										.get(i).description) ? "\n"
								+ "Description: "
								+ resumeData.workExperience.get(i).description
								+ "\n"
								: "");

				Chunk expDetail = new Chunk(workExp, contentFont);
				work.add(expDetail);
				if(i<resumeData.workExperience.size()-1){
					work.setSpacingAfter(8);
				}
				blockBody.add(work);
			}
			
		}

		else if (blockName.equals(PROJECTS_HEADING)) {
			Paragraph projectPara;
			for (int i = 0; i < resumeData.project.size(); i++) {
				projectPara = new Paragraph();
				Chunk projNameChunk = new Chunk(
						resumeData.project.get(i).title, headingFont);
				projectPara.add(projNameChunk);
				if (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).timebeg)
						&& Common_Utilty.isNotEmptyString(resumeData.project
								.get(i).timeend)) {

					String projectTime = resumeData.project.get(i).timebeg
							+ "-" + resumeData.project.get(i).timeend;
					Chunk projecttimeChunk = new Chunk(projectTime,
							contentFontItalic);
					float projectNameWidth = projNameChunk.getWidthPoint();
					float projectTimeWidth = projecttimeChunk.getWidthPoint() ;
					String projectFiller = ResumePdf.getFiller(
							(documentWidth*0.86f - projectNameWidth),
							projectTimeWidth);
					Chunk projectFillerChunk = new Chunk(projectFiller);
					projectPara.add(projectFillerChunk);
					projectPara.add(projecttimeChunk);

				}
				

				String projectDetail = (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).size) ? "\n"
						+ "Team size: " + resumeData.project.get(i).size
						: "")
						+ (Common_Utilty.isNotEmptyString(resumeData.project
								.get(i).role) ? "\n" + "Role: "
								+ resumeData.project.get(i).role : "")
						+ (Common_Utilty.isNotEmptyString(resumeData.project
								.get(i).description) ? "\n" + "Description: "
								+ resumeData.project.get(i).description + "\n": "");

				Chunk projectDetailChunk = new Chunk(projectDetail, contentFont);
				projectPara.add(projectDetailChunk);
				if(i<resumeData.project.size()-1){
					projectPara.setSpacingAfter(8);
				}
				blockBody.add(projectPara);
			}
			
		}

		else if (blockName.equals(RESEARCH_HEADING)) {
			Paragraph researchParagraph ;
			for (int i = 0; i < resumeData.research.size(); i++) {
				researchParagraph = new Paragraph();
				Chunk researchTitle = new Chunk(
						resumeData.research.get(i).papertitle, headingFont);
				researchParagraph.add(researchTitle);
				String reserchDetail = (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).journal) ? "\n"
						+ "Journal name: " + resumeData.research.get(i).journal
						: "")
						+ (Common_Utilty.isNotEmptyString(resumeData.research
								.get(i).volume) ? "\n" + "Volume: "
								+ resumeData.research.get(i).volume : "")
						+ (Common_Utilty.isNotEmptyString(resumeData.research
								.get(i).paperIssue) ? "\n" + "Issue: "
								+ resumeData.research.get(i).paperIssue : "")
						+ (Common_Utilty.isNotEmptyString(resumeData.research
								.get(i).paperDescription) ? "\n"
								+ "Description: "
								+ resumeData.research.get(i).paperDescription
								 + "\n"
								: "");

				Chunk researchDetailChunk = new Chunk(reserchDetail,
						contentFont);
				researchParagraph.add(researchDetailChunk);
				System.out.println(researchParagraph.toString());
				if(i<resumeData.research.size()-1){
					researchParagraph.setSpacingAfter(5);
				}
				
				blockBody.add(researchParagraph);
			}
			
		} else if (blockName.equals(STRENGTH_HEADING)) {

			StringBuilder strengthStr = new StringBuilder();
			int i = 0;

			for (String strength : resumeData.strength) {

				i++;

				if (i < resumeData.strength.size())
					strengthStr.append(strength + '\n');

				else if (i == resumeData.strength.size())
					strengthStr.append(strength);

			}

			blockBody.add(new Chunk(strengthStr.toString(), contentFont));

			// blockBody.setFont(contentFont);

		} else if (blockName.equals(ACHEIVEMENT_HEADING)) {

			StringBuilder acheivementStr = new StringBuilder();
			int i = 0;

			for (String acheivement : resumeData.achive) {

				i++;

				if (i < resumeData.achive.size())
					acheivementStr.append(acheivement + '\n');

				else if (i == resumeData.achive.size())
					acheivementStr.append(acheivement);

			}

			blockBody.add(new Chunk(acheivementStr.toString(), contentFont));

			// blockBody.setFont(contentFont);

		} else if (blockName.equals(EXTRA_CURRICULAR_HEADING)) {

			StringBuilder extraCurricularStr = new StringBuilder();
			int i = 0;

			for (String extraCarricular : resumeData.exCarr) {

				i++;

				if (i < resumeData.exCarr.size())
					extraCurricularStr.append(extraCarricular + '\n');

				else if (i == resumeData.exCarr.size())
					extraCurricularStr.append(extraCarricular);

			}

			blockBody
					.add(new Chunk(extraCurricularStr.toString(), contentFont));

			// blockBody.setFont(contentFont);

		} else if (blockName.equals(HOBBIES_HEADING)) {

			StringBuilder hobbyStr = new StringBuilder();
			int i = 0;

			for (String hobby : resumeData.hobbies) {

				i++;

				if (i < resumeData.hobbies.size())
					hobbyStr.append(hobby + '\n');

				else if (i == resumeData.hobbies.size())
					hobbyStr.append(hobby);

			}

			blockBody.add(new Chunk(hobbyStr.toString(), contentFont));

			// blockBody.setFont(contentFont);

		}
//		else if (blockName.equals(PERSONAL_HEADING)) {
//			blockBody = getPersonalDetailBodyParagraph();
//			
//			System.out.println("personal info" + blockBody);
//
//		}
		else if (blockName.equals(REFERENCE_HEADING)) {
			Paragraph referenceParagraph;
			for (int i = 0; i < resumeData.reference.size(); i++) {
				referenceParagraph = new Paragraph();
				Chunk refName = new Chunk(resumeData.reference.get(i).name,
						headingFont);
				referenceParagraph.add(refName);
				String refDetail = (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).title) ? "\n"
						+ "Designation: " + resumeData.reference.get(i).title
						: "")
						+ (Common_Utilty.isNotEmptyString(resumeData.reference
								.get(i).workAddress) ? "\n" + "Work address: "
								+ resumeData.reference.get(i).workAddress : "")
						+ (Common_Utilty.isNotEmptyString(resumeData.reference
								.get(i).email) ? "\n" + "E-mail: "
								+ resumeData.reference.get(i).email : "");

				Chunk refDetailChunk = new Chunk(refDetail, contentFont);
				referenceParagraph.add(refDetailChunk);

				HashMap<String, String> phones = new HashMap<String, String>();

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).workPhone)) {
					phones.put("Work phone",
							resumeData.reference.get(i).workPhone);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).personalPhone)) {
					phones.put("Personal phone",
							resumeData.reference.get(i).personalPhone);
				}

				if (phones.size() == 1) {
					String phone = "\nContact : "
							+ phones.entrySet().iterator().next().getValue();

					Chunk phoneChunk = new Chunk(phone, contentFont);

					referenceParagraph.add(phoneChunk);
				}

				else if (phones.size() == 2) {

					Iterator<Entry<String, String>> iterator = phones
							.entrySet().iterator();

					HashMap.Entry entry = iterator.next();

					Chunk phoneChunk1 = new Chunk("\n" + entry.getKey() + " : "
							+ entry.getValue(), contentFont);

					entry = iterator.next();

					Chunk phoneChunk2 = new Chunk(entry.getKey() + " : "
							+ entry.getValue(), contentFont);

					referenceParagraph.add(phoneChunk1);
					referenceParagraph.add("\n");
					referenceParagraph.add(phoneChunk2);
					if(i<resumeData.reference.size()-1){
						referenceParagraph.setSpacingAfter(8);
					}
					blockBody.add(referenceParagraph);
				}

			}
		}

		return blockBody;

	}

	@Override
	protected Font getHeadingFont() {
		// TODO Auto-generated method stub
		return headingFont;
	}

	@Override
	protected Font getContentFont() {
		// TODO Auto-generated method stub
		return contentFont;
	}

	@Override
	protected Font getContentFontItalic() {
		// TODO Auto-generated method stub
		return contentFontItalic;
	}

	@Override
	protected Font getTitleFont() {
		// TODO Auto-generated method stub
		return titleFont;
	}

}
