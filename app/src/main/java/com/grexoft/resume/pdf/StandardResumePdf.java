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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;

public class StandardResumePdf extends ResumePdf {

	public StandardResumePdf(Context context, String templateName) {

		
		super(context, templateName);
		isTopAddress=false;

	}
	@Override
	protected void setTemplateSpecifications(String templateName) {

		this.templateName = templateName;
		fontFileName = "CAMBRIA.TTF";

	}


	@Override
	protected Paragraph getBlockHeadingParagraph(String blockName) {

		System.out.println("getBlockHeadingParagraph()");

		Paragraph heading = new Paragraph();

		Chunk headChunk = new Chunk(blockName.toUpperCase(Locale.US),
				headingFont);
//		headChunk.setCharacterSpacing(1.5f);

		headChunk
				.setBackground(
						BaseColor.LIGHT_GRAY,
						4,
						4,
						document.getPageSize().getWidth()
								- (document.leftMargin()
										+ document.rightMargin() + headChunk
											.getWidthPoint()), 8);

		heading.setSpacingAfter(12);

		heading.add(headChunk);

		System.out.println("inside getBlockheadingparagraph() . blockname : "
				+ blockName);

		return heading;
	}
	
	@Override
	protected Paragraph getBlockBodyParagraph(String blockName) {

		Paragraph blockBody = new Paragraph();

		blockBody.setIndentationLeft(5);

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

			Paragraph educatipnParagraph;
			
			for (int i = 0; i < resumeData.educationDetail.size(); i++) {
				educatipnParagraph=new Paragraph();
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
				Phrase educationPharase=new Phrase();
				educationPharase.add(degree);
				educationPharase.add(body);
				educatipnParagraph.add(educationPharase);
			
				if(i< resumeData.educationDetail.size()-1){
					educatipnParagraph.setSpacingAfter(4);
				}
				

				blockBody.add(educatipnParagraph);
				

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
				
				if(i<resumeData.workExperience.size()-1){
					workParagraph.setSpacingAfter(4);	
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
					projectParagraph.setSpacingAfter(4);
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
					researchParagraph.setSpacingAfter(4);
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

			blockBody.add(new Chunk(skillStr.toString(),contentFont));

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
					strengthStr.append(strength);

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
					referenceParagraph.setSpacingAfter(4);
				}
				blockBody.add(referenceParagraph);

			}
		}

		else if (blockName.equals(DECLARATION_HEADING)) {

			String declarationString = (resumeData.declaration);
			Chunk date = new Chunk("Date : ", contentFont);
			Chunk declarationChunk = new Chunk(declarationString, contentFont);
			Chunk place = new Chunk("Place : ", contentFont);
			Chunk name = new Chunk(resumeData.name, contentFont);
			float gap = documentWidth - place.getWidthPoint() - 20;
			String cfiller = ResumePdf.getFiller(gap, name.getWidthPoint());
			blockBody.add(declarationChunk);
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
	protected void onBlockCreated(Paragraph block, String blockName) {

		block.setSpacingBefore(12);
	}

	
	@Override
	protected void setTitleOfDocument() throws DocumentException {

		if (!document.isOpen())
			return;

		 String name= Common_Utilty.isNotEmptyString(resumeData.name)?resumeData.name:"";

			Chunk nameChunk = new Chunk(name.toUpperCase(Locale.US),
					titleFont);

			nameChunk.setCharacterSpacing(1.5f);
			Paragraph titlePara = new Paragraph();

			titlePara.add(nameChunk);
			
		String title = (Common_Utilty.isNotEmptyString(resumeData.title) && resumeData.showTitle)? "\n" + resumeData.title: "";
		titlePara.add(new Chunk(WordUtils.capitalize(title),headingFont));
		titlePara.setSpacingAfter(10);
		
		document.add(titlePara);
		
		
		String emailstr = (resumeData.email != null) ? resumeData.email : "";

		String contactstr = (resumeData.primarycontact != null) ? resumeData.primarycontact
				: "";

		

		Chunk emailChunk = new Chunk("E-mail : ", headingFont);

		Chunk emailBodyChunk = new Chunk(emailstr, contentFont);

		
		Float Emailwidth = emailChunk.getWidthPoint()
				+ emailBodyChunk.getWidthPoint();
		Phrase emailPhrase = new Phrase();

		emailPhrase.add(emailChunk);

		emailPhrase.add(emailBodyChunk);


		Chunk contactchunk = new Chunk("Contact : ", headingFont);

		Chunk contactBodyChunk = new Chunk(contactstr, contentFont);

		
		Float contactWidth = contactchunk.getWidthPoint()
				+ contactBodyChunk.getWidthPoint();
		Phrase contactPhrase = new Phrase();

		contactPhrase.add(contactchunk);

		contactPhrase.add(contactBodyChunk);


		String filler = ResumePdf.getFiller(contactWidth, Emailwidth);

		if (contactWidth > Emailwidth) {
			emailPhrase.add(new Chunk(filler));
		} else {
			contactPhrase.add(new Chunk(filler));
		}
		Paragraph emailContactParagraph = new Paragraph();

		if(!emailstr.equals("")){
			emailContactParagraph.add(emailPhrase);	
		}
		

		if(!contactstr.equals("")){
			emailContactParagraph.add(new Chunk("\n"));

			emailContactParagraph.add(contactPhrase);

		}
		
		emailContactParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
		if(!emailContactParagraph.equals("")){
			document.add(ResumePdf.getLine(1.5f,BaseColor.DARK_GRAY));
			document.add(emailContactParagraph);
		}
		

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
