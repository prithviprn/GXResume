package com.grexoft.resume.pdf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

import org.apache.commons.lang3.text.WordUtils;

import android.content.Context;

import com.grexoft.resume.helpers.Common_Utilty;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class ClassicalResumePdf extends ResumePdf{

	public ClassicalResumePdf(Context context, String templateName) {
		super(context, templateName);
		isTopAddress=true;
	}

	@Override
	protected void setTemplateSpecifications(String templateName) {
		this.templateName = templateName;
		fontFileName = "CALIBRIL.TTF";
		
	}
	protected void onBlockCreated(Paragraph block, String blockName) {

		block.setSpacingBefore(15);
	}
	
	
	
	
	@Override
	protected void setTitleOfDocument() throws DocumentException {
		
		try {

			if (!document.isOpen())
				return;

			
			
			String name=Common_Utilty.isNotEmptyString(resumeData.name)?resumeData.name:"";
			
				Chunk nameChunk = new Chunk(
						resumeData.name.toUpperCase(Locale.US), titleFont);

				

				String titleOfResume=(Common_Utilty.isNotEmptyString(resumeData.title)&& resumeData.showTitle)?"\n"+resumeData.title:"";
				Chunk titleOfResumeChunk=new Chunk(WordUtils.capitalize(titleOfResume),headingFont);
				
			
			Paragraph titlePara = new Paragraph();

			titlePara.add(nameChunk);
			titlePara.add(titleOfResumeChunk);
			
			titlePara.setAlignment(Paragraph.ALIGN_CENTER);

			document.add(titlePara);

			String presentAddressStr = "";

			if (Common_Utilty.isNotEmptyString(resumeData.city)) {
				if (Common_Utilty.isNotEmptyString(resumeData.street)) {
					presentAddressStr = presentAddressStr + resumeData.street
							+ ",";

				}
				presentAddressStr = presentAddressStr + resumeData.city;
				if (Common_Utilty.isNotEmptyString(resumeData.pincode)) {
					presentAddressStr = presentAddressStr + "-"
							+ resumeData.pincode;
				}
			}

			if (Common_Utilty.isNotEmptyString(resumeData.city)
					&& Common_Utilty.isNotEmptyString(resumeData.country)) {
				presentAddressStr = presentAddressStr + ",";
			}

			if (Common_Utilty.isNotEmptyString(resumeData.country)) {

				if (Common_Utilty.isNotEmptyString(resumeData.state)) {
					presentAddressStr = presentAddressStr + resumeData.state
							+ ",";

				}
				presentAddressStr = presentAddressStr + resumeData.country;
			}

			Paragraph headPara = new Paragraph();
			headPara.setIndentationLeft(5);
			headPara.setIndentationRight(5);
			Font ZAPFDINGBATS=new Font(FontFamily.ZAPFDINGBATS,8);
			Chunk bullet=new Chunk(String.valueOf((char) 108 ),ZAPFDINGBATS);

			

				Chunk presentAddChunk = new Chunk(presentAddressStr,
						contentFont);
				Phrase addPhrase = new Phrase();

				addPhrase.add(presentAddChunk);
				
				headPara.add(addPhrase);

		

			String contactstr = (Common_Utilty.isNotEmptyString(resumeData.primarycontact)) ?" " + resumeData.primarycontact
					: "";

			Chunk contactBodyChunk = new Chunk(contactstr, contentFont);

			Phrase contactPhrase = new Phrase();

			if(!contactstr.equals("")){
				contactPhrase.add(new Chunk("  "));
				contactPhrase.add(bullet);
			}
			
			
			contactPhrase.add(contactBodyChunk);

		
			headPara.add(contactPhrase);

			String emailstr = (Common_Utilty.isNotEmptyString(resumeData.email )) ? " " + resumeData.email
					: "";

			Chunk emailBodyChunk = new Chunk(emailstr, contentFont);

			Phrase emailPhrase = new Phrase();

			if(!emailstr.equals("")){
				emailPhrase.add(new Chunk("  "));
				emailPhrase.add(bullet);
			}
			
			emailPhrase.add(emailBodyChunk);
//			headPara.add(new Chunk("\n"));
			
			
			headPara.add(emailPhrase);
			headPara.setAlignment(Paragraph.ALIGN_CENTER);
			
			
			
			headPara.setSpacingAfter(10);
			document.add(headPara);
			

			if(!headPara.equals("")){
				document.add(getLine(0.8f, BaseColor.DARK_GRAY));
				

			}
			

		} catch (DocumentException e) {

			e.printStackTrace();

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	

	

	@Override
	protected Paragraph getBlockHeadingParagraph(String blockName) {
		Paragraph heading = new Paragraph();

		Chunk headChunk = new Chunk(blockName.toUpperCase(Locale.US),
				headingFont);

		heading.setSpacingAfter(10);

		heading.add(headChunk);

		return heading;
	}

	@Override
	protected Paragraph getBlockBodyParagraph(String blockName)
			throws DocumentException {
		Paragraph blockBody = new Paragraph();

//		blockBody.setIndentationLeft(5);

		blockBody.setIndentationRight(5);

		blockBody.setAlignment(Paragraph.ALIGN_JUSTIFIED);

		if (blockName.equals(OBJECTIVE_HEADING)) {

			String objectiveString = resumeData.objectives;

			Chunk objectiveChunk = new Chunk(objectiveString, contentFont);

			blockBody.add(objectiveChunk);

			System.out.println("inside getBlockBodyparagraph() . blockname : "
					+ OBJECTIVE_HEADING);

		}

		else if (blockName.equals(EDUCATION_HEADING)) {
			
			
			if(resumeData.educationDetail!=null && !resumeData.educationDetail.isEmpty()){
				
				PdfPTable educationTable=new PdfPTable(4);
				PdfPCell degreeCell,universityCell, passYearCell, percentageCell;
				Paragraph degreeParagraph,universityParagraph,yearParagraph,percentageParagraph;
				
				degreeCell=new PdfPCell();
				degreeCell.setBorder(Rectangle.BOX);
				degreeCell.setPaddingBottom(10);
				
				universityCell=new PdfPCell();
				universityCell.setPaddingBottom(10);
				universityCell.setBorder(Rectangle.BOX);
				
				passYearCell=new PdfPCell();
				passYearCell.setBorder(Rectangle.BOX);
				passYearCell.setPaddingBottom(10);
				
				percentageCell=new PdfPCell();
				percentageCell.setBorder(Rectangle.BOX);
				percentageCell.setPaddingBottom(10);
				
				Chunk titleDegree=new Chunk("Degree" ,headingFont);
				degreeParagraph=new Paragraph(titleDegree);
				degreeParagraph.setAlignment(Paragraph.ALIGN_CENTER);
				degreeCell.addElement(degreeParagraph);
			
//				degreeCell.addElement(titleDegree);
//				degreeCell.setBorder(Rectangle.BOX);
//				degreeCell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
//				degreeCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
//				degreeCell.setPaddingLeft(15);
//				degreeCell.setPaddingBottom(10);
				
				Chunk titleUniversity=new Chunk("University", headingFont);
				
				universityParagraph=new Paragraph(titleUniversity);
				universityParagraph.setAlignment(Paragraph.ALIGN_CENTER);
				universityCell.addElement(universityParagraph);
//				universityCell.addElement(titleUniversity);
//				universityCell.setBorder(Rectangle.BOX);
//				universityCell.setHorizontalAlignment(PdfPCell.ALIGN_MIDDLE);
//				universityCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
//				universityCell.setPaddingLeft(15);
				
				Chunk titleYear=new Chunk("Passout", headingFont);
				yearParagraph=new Paragraph(titleYear);
				yearParagraph.setAlignment(Paragraph.ALIGN_CENTER);
				passYearCell.addElement(yearParagraph);
				
//				passYearCell.setHorizontalAlignment(PdfPCell.ALIGN_MIDDLE);
//				passYearCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
//				passYearCell.setPaddingLeft(15);
//				
				Chunk titlePercentage=new Chunk("Percentage/CGPA",headingFont);
				percentageParagraph=new Paragraph(titlePercentage);
				percentageParagraph.setAlignment(Paragraph.ALIGN_CENTER);
				
				percentageCell.addElement(percentageParagraph);
				
//				percentageCell.setHorizontalAlignment(PdfPCell.ALIGN_MIDDLE);
//				percentageCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
//				percentageCell.setPaddingLeft(15);
//				
				educationTable.addCell(degreeCell);
				educationTable.addCell(universityCell);
				educationTable.addCell(passYearCell);
				educationTable.addCell(percentageCell);
				
				for (int i = 0; i < resumeData.educationDetail.size(); i++) {

					
					degreeCell=new PdfPCell();
					degreeCell.setBorder(Rectangle.BOX);
					degreeCell.setPaddingBottom(10);
					
					universityCell=new PdfPCell();
					universityCell.setBorder(Rectangle.BOX);
					universityCell.setPaddingBottom(10);
					
					
					passYearCell=new PdfPCell();
					passYearCell.setBorder(Rectangle.BOX);
					passYearCell.setPaddingBottom(10);
					
					
					percentageCell=new PdfPCell();
					percentageCell.setBorder(Rectangle.BOX);
					percentageCell.setPaddingBottom(10);
					
					
					String degreeName= Common_Utilty.isNotEmptyString(resumeData.educationDetail.get(i).degree) ? resumeData.educationDetail.get(i).degree:"";
					String universityName=Common_Utilty.isNotEmptyString(resumeData.educationDetail.get(i).university ) ? resumeData.educationDetail.get(i).university :"";
					
					String year=Common_Utilty.isNotEmptyString(resumeData.educationDetail.get(i).passout) ? resumeData.educationDetail.get(i).passout : "";
					String percentage= Common_Utilty.isNotEmptyString(resumeData.educationDetail.get(i).result) ?resumeData.educationDetail.get(i).result:"";
					
					Chunk degreeNameChunk=new Chunk(degreeName,contentFont);
					Chunk universityNameChunk=new Chunk(universityName,contentFont);
					Chunk yearChunk=new Chunk(year,contentFont);
					Chunk percentageChunk=new Chunk(percentage,contentFont);
					
					degreeParagraph=new Paragraph(degreeNameChunk);
					degreeParagraph.setAlignment(Paragraph.ALIGN_CENTER);
					degreeCell.addElement(degreeParagraph);
					
					
				
					universityParagraph=new Paragraph(universityNameChunk);
					universityParagraph.setAlignment(Paragraph.ALIGN_CENTER);
					universityCell.addElement(universityParagraph);
					
					
					yearParagraph=new Paragraph(yearChunk);
					yearParagraph.setAlignment(Paragraph.ALIGN_CENTER);
					passYearCell.addElement(yearParagraph);
					
					
					percentageParagraph=new Paragraph(percentageChunk);
					percentageParagraph.setAlignment(Paragraph.ALIGN_CENTER);
					percentageCell.addElement(percentageParagraph);
					
					
					educationTable.addCell(degreeCell);
					educationTable.addCell(universityCell);
					educationTable.addCell(passYearCell);
					educationTable.addCell(percentageCell);
					
					educationTable.setWidthPercentage(100);
					educationTable.setWidths(new float[] { (float) 2.0, (float) 2.0 , (float) 2.0,(float)2.0});
				
				}
				blockBody.add(educationTable);
			}

		
				
				
					
					 
				
				
				

			

		}

		else if (blockName.equals(EXPERIENCE_HEADING)) {
			Paragraph workParagraph;
			for (int i = 0; i < resumeData.workExperience.size(); i++) {
				workParagraph = new Paragraph();

				Chunk compName = new Chunk(
						resumeData.workExperience.get(i).compName, headingFont);
				workParagraph.add(compName);
				if (Common_Utilty.isNotEmptyString(resumeData.workExperience
						.get(i).timeStart)
						&& Common_Utilty
								.isNotEmptyString(resumeData.workExperience
										.get(i).timeEnd)) {
					String timePeriod = resumeData.workExperience.get(i).timeStart
							+ "-" + resumeData.workExperience.get(i).timeEnd;
					Chunk time = new Chunk(timePeriod, contentFontItalic);

					float compNameWidth = (compName.getWidthPoint());
					float timeWidth = time.getWidthPoint() + 20.0f;
					String fillerExp = ResumePdf.getFiller(
							(documentWidth - compNameWidth), timeWidth);
					Chunk fillerExperience = new Chunk(fillerExp);

					workParagraph.add(fillerExperience);
					workParagraph.add(time);
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
								: "");

				Chunk expDetail = new Chunk(workExp, contentFont);
				workParagraph.add(expDetail);
				if(i<resumeData.educationDetail.size()-1){
					workParagraph.setSpacingAfter(5);
				}
				blockBody.add(workParagraph);

			}
		}

		else if (blockName.equals(PROJECTS_HEADING)) {
			Paragraph projectParagraph;
			for (int i = 0; i < resumeData.project.size(); i++) {
				projectParagraph = new Paragraph();
				Chunk projNameChunk = new Chunk(
						resumeData.project.get(i).title, headingFont);
				projectParagraph.add(projNameChunk);
				if (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).timebeg)
						&& Common_Utilty.isNotEmptyString(resumeData.project
								.get(i).timeend)) {

					String projectTime = resumeData.project.get(i).timebeg
							+ "-" + resumeData.project.get(i).timeend;
					Chunk projecttimeChunk = new Chunk(projectTime,
							contentFontItalic);
					float projectNameWidth = projNameChunk.getWidthPoint();
					float projectTimeWidth = projecttimeChunk.getWidthPoint() + 20.0f;
					String projectFiller = ResumePdf.getFiller(
							(documentWidth - projectNameWidth),
							projectTimeWidth);
					Chunk projectFillerChunk = new Chunk(projectFiller);
					projectParagraph.add(projectFillerChunk);
					projectParagraph.add(projecttimeChunk);
					

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
								+ resumeData.project.get(i).description : "");

				Chunk projectDetailChunk = new Chunk(projectDetail, contentFont);
				projectParagraph.add(projectDetailChunk);
				if(i<resumeData.project.size()-1){
					projectParagraph.setSpacingAfter(5);
				}
				blockBody.add(projectParagraph);

			}
		}

		else if (blockName.equals(RESEARCH_HEADING)) {
			Paragraph researchParagraph;
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

			Chunk skillChunk =new Chunk(skillStr.toString(), contentFont);
			blockBody.add(skillChunk);

//			blockBody.setFont(contentFont);

		}

		else if (blockName.equals(HOBBIES_HEADING)) {

			StringBuilder hobbyStr = new StringBuilder();
			int i = 0;

			for (String hobby : resumeData.hobbies) {

				i++;

				if (i < resumeData.hobbies.size())
					hobbyStr.append(hobby + '\n');

				else if (i == resumeData.hobbies.size())
					hobbyStr.append(hobby);

			}

			blockBody.add(new Chunk(hobbyStr.toString(),contentFont));

//			blockBody.setFont(contentFont);

		}

		else if (blockName.equals(ACHEIVEMENT_HEADING)) {

			StringBuilder acheivementStr = new StringBuilder();
			int i = 0;

			for (String acheivement : resumeData.achive) {

				i++;

				if (i < resumeData.achive.size())
					acheivementStr.append(acheivement + '\n');

				else if (i == resumeData.achive.size())
					acheivementStr.append(acheivement);

			}

			blockBody.add(new Chunk(acheivementStr.toString(),contentFont));

//			blockBody.setFont(contentFont);

		}

		else if (blockName.equals(EXTRA_CURRICULAR_HEADING)) {

			StringBuilder extraCurricularStr = new StringBuilder();
			int i = 0;

			for (String extraCarricular : resumeData.exCarr) {

				i++;

				if (i < resumeData.exCarr.size())
					extraCurricularStr.append(extraCarricular + '\n');

				else if (i == resumeData.exCarr.size())
					extraCurricularStr.append(extraCarricular);

			}

			blockBody.add(new Chunk(extraCurricularStr.toString(),contentFont));

//			blockBody.setFont(contentFont);

		}

		else if (blockName.equals(STRENGTH_HEADING)) {

			StringBuilder strengthStr = new StringBuilder();
			int i = 0;

			for (String strength : resumeData.strength) {

				i++;

				if (i < resumeData.strength.size())
					strengthStr.append(strength + '\n');

				else if (i == resumeData.strength.size())
					strengthStr.append(strength );

			}

			blockBody.add(new Chunk(strengthStr.toString(),contentFont));

//			blockBody.setFont(contentFont);

		}

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

					Chunk fillerChunk = new Chunk(ResumePdf.getFiller(
							documentWidth - phoneChunk1.getWidthPoint() - 20,
							phoneChunk2.getWidthPoint()));

					referenceParagraph.add(phoneChunk1);
					referenceParagraph.add(fillerChunk);
					referenceParagraph.add(phoneChunk2);
				}

				if(i<resumeData.reference.size()-1){
					referenceParagraph.setSpacingAfter(5);
				}
				blockBody.add(referenceParagraph);

			}
		}

		else if (blockName.equals(DECLARATION_HEADING)) {
		

			
			String declarationString = Common_Utilty.isNotEmptyString(resumeData.declaration)?resumeData.declaration:"";
			Chunk date = new Chunk("Date : ", contentFont);
			Chunk declarationChunk = new Chunk(declarationString, contentFont);
			Chunk place = new Chunk("Place : ", contentFont);
			Chunk name = new Chunk(resumeData.name, contentFont);
			float gap = documentWidth - place.getWidthPoint() - 20;
			String cfiller = ResumePdf.getFiller(gap, name.getWidthPoint());
			blockBody.add(declarationChunk);
			
			if(!declarationString.equals("")){
				blockBody.add(new Chunk("\n"));
				blockBody.add(getLine(0.8f, BaseColor.DARK_GRAY));
			}
//			
			
			
			blockBody.add("\n\n");
			blockBody.add(date);
			blockBody.add("\n");
			blockBody.add(place);
			blockBody.add(new Chunk(cfiller));
			blockBody.add(name);
			

			System.out.println("inside getBlockBodyparagraph() . blockname : "
					+ DECLARATION_HEADING);

		}

		return blockBody;
	}

	@Override
	protected Font getHeadingFont() {
		
		return headingFont;
	}

	@Override
	protected Font getContentFont() {
		
		return contentFont;
	}

	@Override
	protected Font getContentFontItalic() {
		
		return contentFontItalic;
	}

	@Override
	protected Font getTitleFont() {
		
		return titleFont;
	}

}
