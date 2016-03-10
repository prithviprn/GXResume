package com.grexoft.resume.pdf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

import org.apache.commons.lang3.text.WordUtils;

import android.content.Context;
import android.os.Environment;

import com.grexoft.resume.helpers.Common_Utilty;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class LoftResumePdf extends ResumePdf {

	public LoftResumePdf(Context context, String templateName) {
		super(context, templateName);
		isTopAddress = false;

		System.out.println("constructer invoked");

	}

	@Override
	protected void setTemplateSpecifications(String templateName) {
		this.templateName = templateName;
		fontFileName = "CALIBRIL.TTF";

	}

	
	
	protected void onBlockCreated(Paragraph block, String blockName) {

		block.setSpacingBefore(20);
	}

	@Override
	 protected void setTitleOfDocument() throws DocumentException {
		  try {

		   if (!document.isOpen())
		    return;

		   PdfPTable titleTable = null;

		   Paragraph title = new Paragraph();

		   String name = (Common_Utilty.isNotEmptyString(resumeData.name) )? resumeData.name : "";

		   Chunk nameChunk = new Chunk(name.toUpperCase(Locale.US), titleFont);
		   title.add(nameChunk);
		   
		   String resumeTitle = (Common_Utilty.isNotEmptyString(resumeData.title) && resumeData.showTitle)? "\n" + resumeData.title : "";
		   Chunk resumeTitleChunk = new Chunk(WordUtils.capitalize(resumeTitle), headingFont);
		   title.add(resumeTitleChunk);

		   Paragraph contact_email = new Paragraph();
		   String contactstr = (resumeData.primarycontact != null) ? "\n"
		     + resumeData.primarycontact : "";

		   Chunk contactBodyChunk = new Chunk(contactstr, contentFont);

		   title.add(contactBodyChunk);

		   String emailstr = (Common_Utilty.isNotEmptyString(resumeData.email)) ? "\n"
		     + resumeData.email
		     : "";

		   Chunk emailBodyChunk = new Chunk(emailstr, contentFont);

		   title.add(emailBodyChunk);
		   if (Common_Utilty.isNotEmptyString(resumeData.image) && resumeData.showImage) {
		    Paragraph profileParagraph = new Paragraph();
		    String imageDirectory = Environment
		      .getExternalStorageDirectory().toString()
		      + "/"
		      + Common_Utilty.APPLICATION_DIRECTORY
		      + "/"
		      + Common_Utilty.IMAGE_DIRECTORY;
		    Image profilImage = Image.getInstance(imageDirectory + "/"
		      + resumeData.image.toString());
		    

		    profileParagraph.add(new Chunk(profilImage, 0, 0, true));
		    profileParagraph.setAlignment(Paragraph.ALIGN_LEFT);

		    titleTable = new PdfPTable(2);
		    PdfPCell textCell = new PdfPCell();
		    textCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    textCell.disableBorderSide(Rectangle.BOX);
		    textCell.setVerticalAlignment(Element.ALIGN_BOTTOM);

		    PdfPCell imageCell = new PdfPCell();
		    imageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    imageCell.disableBorderSide(Rectangle.BOX);
		    imageCell.setPaddingBottom(5);
//		    imageCell.setHorizontalAlignment(Element.ALIGN_BOTTOM);

		    title.setAlignment(Paragraph.ALIGN_LEFT);
		    textCell.addElement(title);
		    textCell.setPaddingBottom(5);
		    textCell.setPaddingLeft(10);
		    
		    
		      
		        

		       profilImage.scalePercent(70);
		    imageCell.addElement(profileParagraph);
//		    imageCell.setFixedHeight(hight);
		    titleTable.addCell(imageCell);
		    titleTable.addCell(textCell);
		    
		    titleTable.setWidthPercentage(100);
		    titleTable.setWidths(new float[] { 2.0f, 8.0f });

		   }

		   else  {
		    titleTable = new PdfPTable(1);
		    PdfPCell cellTextOrImage = new PdfPCell();
		    cellTextOrImage.disableBorderSide(Rectangle.BOX);
		    cellTextOrImage.addElement(title);

		    cellTextOrImage.setPaddingBottom(5);
		    titleTable.addCell(cellTextOrImage);
		    
		    titleTable.setWidthPercentage(100);
		   }

		   document.add(titleTable);

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

		heading.setSpacingAfter(8);

		heading.add(headChunk);

		return heading;
	}

	@Override
	protected Paragraph getBlockBodyParagraph(String blockName)
			throws DocumentException {
		Paragraph blockBody = new Paragraph();

		// blockBody.setIndentationLeft(5);
		//
		// blockBody.setIndentationRight(5);

		blockBody.setAlignment(Paragraph.ALIGN_JUSTIFIED);

		if (blockName.equals(OBJECTIVE_HEADING)) {

			String objectiveString = resumeData.objectives;

			Chunk objectiveChunk = new Chunk(objectiveString, contentFont);

			blockBody.add(objectiveChunk);

			System.out.println("inside getBlockBodyparagraph() . blockname : "
					+ OBJECTIVE_HEADING);

		}

		else if (blockName.equals(EDUCATION_HEADING)) {

			Paragraph educationParagraph;
			for (int i = 0; i < resumeData.educationDetail.size(); i++) {
				educationParagraph=new Paragraph();
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
				educationParagraph.add(degree);
				educationParagraph.add(body);
				if(i<resumeData.educationDetail.size()-1){
					educationParagraph.setSpacingAfter(5);
				}
				
				blockBody.add(educationParagraph);
				

			}
			
			
		}

		else if (blockName.equals(EXPERIENCE_HEADING)) {

			for (int i = 0; i < resumeData.workExperience.size(); i++) {
				Paragraph workExpDetail = new Paragraph();
				Paragraph work = new Paragraph();
				Paragraph header = new Paragraph();

				String companyName = Common_Utilty
						.isNotEmptyString(resumeData.workExperience.get(i).compName) ? resumeData.workExperience
						.get(i).compName : "";
				Chunk compName = new Chunk(companyName, headingFont);
				header.add(compName);

				String timePeriod = "";
				if (Common_Utilty.isNotEmptyString(resumeData.workExperience
						.get(i).timeStart)
						&& Common_Utilty
								.isNotEmptyString(resumeData.workExperience
										.get(i).timeEnd)) {
					timePeriod = resumeData.workExperience.get(i).timeStart
							+ "-" + resumeData.workExperience.get(i).timeEnd;
					Chunk time = new Chunk(timePeriod, contentFontItalic);

					float compNameWidth = (compName.getWidthPoint());
					float timeWidth = time.getWidthPoint() + 20.0f;
					String fillerExp = ResumePdf.getFiller(
							(documentWidth - compNameWidth), timeWidth);
					Chunk fillerExperience = new Chunk(fillerExp);

					header.add(fillerExperience);
					header.add(time);

				}

				if (!companyName.equals("") || !timePeriod.equals("")) {
					

					workExpDetail.setSpacingBefore(-8);
					workExpDetail.add(getLine(0.8f, BaseColor.DARK_GRAY));
					
					

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
				workExpDetail.add(expDetail);

				work.add(header);
				work.add(workExpDetail);

				if(i<resumeData.workExperience.size()-1){
					work.setSpacingAfter(5);
				}
				
				blockBody.add(work);

			}
		}

		else if (blockName.equals(PROJECTS_HEADING)) {

			for (int i = 0; i < resumeData.project.size(); i++) {
				Paragraph nameAndTimePara = new Paragraph();

				Paragraph roleAndSizePara = new Paragraph();

				Paragraph desriptionParagraph = new Paragraph();

				Paragraph projectParagraph = new Paragraph();

				String projName = Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).title) ? resumeData.project
						.get(i).title : "";

				Chunk projNameChunk = new Chunk(projName, headingFont);

				nameAndTimePara.add(projNameChunk);

				String projectTime = "";

				if (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).timebeg)
						&& Common_Utilty.isNotEmptyString(resumeData.project
								.get(i).timeend)) {

					projectTime = resumeData.project.get(i).timebeg + "-"
							+ resumeData.project.get(i).timeend;
					Chunk projectTimeChunk = new Chunk(projectTime,
							contentFontItalic);
					float projectNameWidth = projNameChunk.getWidthPoint();
					float projectTimeWidth = projectTimeChunk.getWidthPoint() + 20.0f;
					String nameAndTimeFiller = ResumePdf.getFiller(
							(documentWidth - projectNameWidth),
							projectTimeWidth);
					Chunk nameAndTimeFillerChunk = new Chunk(nameAndTimeFiller);
					nameAndTimePara.add(nameAndTimeFillerChunk);
					nameAndTimePara.add(projectTimeChunk);

				}

				String projectRole = (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).role) ? "Role : "
						+ resumeData.project.get(i).role
						: "");

				Chunk projectRoleChunk = new Chunk(projectRole, contentFont);

				float roleWidth = projectRoleChunk.getWidthPoint();

				String projectSize = (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).size) ? "Team size : "
						+ resumeData.project.get(i).size
						: "");

				Chunk projSizeChunk = new Chunk(projectSize, contentFont);

				float sizeWidth = projSizeChunk.getWidthPoint() + 20.0f;

				String roleAndSizeFiller = ResumePdf.getFiller(
						documentWidth - roleWidth, sizeWidth);

				Chunk roleAndSizeFillerChunk = new Chunk(roleAndSizeFiller);

				if (!projName.equals("") || !projectTime.equals("")) {

					roleAndSizePara.add(getLine(0.8f, BaseColor.DARK_GRAY));
					roleAndSizePara.setSpacingBefore(-8);
					roleAndSizePara.add(new Chunk("\n"));
				}

				roleAndSizePara.add(projectRoleChunk);
				roleAndSizePara.add(roleAndSizeFillerChunk);
				roleAndSizePara.add(projSizeChunk);

				String projectDescrip = (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).description) ? "Description : "
						+ resumeData.project.get(i).description
						: "");

				Chunk projectDescripChunk = new Chunk(projectDescrip,
						contentFont);

				desriptionParagraph.add(projectDescripChunk);

				projectParagraph.add(nameAndTimePara);
				projectParagraph.add(roleAndSizePara);
				projectParagraph.add(desriptionParagraph);

				if(i<resumeData.project.size()-1){
					projectParagraph.setSpacingAfter(5);
				}
				
				blockBody.add(projectParagraph);

			}
		}

		else if (blockName.equals(RESEARCH_HEADING)) {
			for (int i = 0; i < resumeData.research.size(); i++) {

				Paragraph titleAndJournalPara = new Paragraph();
				Paragraph volAndIssuePara = new Paragraph();
				Paragraph descriptionPara = new Paragraph();
				Paragraph researchParagraph = new Paragraph();

				String researchTitle = Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).papertitle) ? resumeData.research
						.get(i).papertitle : "";

				Chunk researchTitleChunk = new Chunk(researchTitle, headingFont);

				float researchTitleWidth = researchTitleChunk.getWidthPoint();

				String journal = Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).journal) ? resumeData.research
						.get(i).journal : "";

				Chunk journalChunk = new Chunk(journal, contentFontItalic);

				float journalWidth = journalChunk.getWidthPoint() + 20.0f;

				String titleAndJournalFiller =ResumePdf.getFiller(
						documentWidth - researchTitleWidth, journalWidth);

				Chunk titleAndJournalFillerChunk = new Chunk(
						titleAndJournalFiller);

				titleAndJournalPara.add(researchTitleChunk);
				titleAndJournalPara.add(titleAndJournalFillerChunk);
				titleAndJournalPara.add(journalChunk);

				String volume = Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).volume) ? "Volume : "
						+ resumeData.research.get(i).volume
						: "";

				String issue = Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).paperIssue) ? "Issue : "
						+ resumeData.research.get(i).paperIssue
						: "";

				Chunk volumeChunk = new Chunk(volume, contentFont);
				Chunk issueChunk = new Chunk(issue, contentFont);

				float volumeWidth = volumeChunk.getWidthPoint();
				float issueWidth = issueChunk.getWidthPoint() + 20.0f;

				String volumeAndIssueFiller =ResumePdf.getFiller(
						documentWidth - volumeWidth, issueWidth);

				Chunk volumeAndIssueFillerChunk = new Chunk(
						volumeAndIssueFiller);

				if (!researchTitle.equals("") || !journal.equals("")) {
					
					volAndIssuePara.add(getLine(0.8f, BaseColor.DARK_GRAY));
					volAndIssuePara.add(new Chunk("\n"));
					volAndIssuePara.setSpacingBefore(-8);

				}
				volAndIssuePara.add(volumeChunk);
				volAndIssuePara.add(volumeAndIssueFillerChunk);
				volAndIssuePara.add(issueChunk);

				String description = Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).paperDescription) ? "Description : "
						+ resumeData.research.get(i).paperDescription
						: "";

				Chunk descriptionChunk = new Chunk(description, contentFont);
				descriptionPara.add(descriptionChunk);

				researchParagraph.add(titleAndJournalPara);
				researchParagraph.add(volAndIssuePara);
				researchParagraph.add(descriptionPara);
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

			Chunk skillChunk = new Chunk(skillStr.toString(), contentFont);
			blockBody.add(skillChunk);

			// blockBody.setFont(contentFont);

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

			blockBody.add(new Chunk(hobbyStr.toString(), contentFont));

			// blockBody.setFont(contentFont);

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

			blockBody.add(new Chunk(acheivementStr.toString(), contentFont));

			// blockBody.setFont(contentFont);

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

			blockBody
					.add(new Chunk(extraCurricularStr.toString(), contentFont));

			// blockBody.setFont(contentFont);

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

			blockBody.add(new Chunk(strengthStr.toString(), contentFont));

			// blockBody.setFont(contentFont);

		}

		else if (blockName.equals(REFERENCE_HEADING)) {

			for (int i = 0; i < resumeData.reference.size(); i++) {
				Paragraph nameAndDesignParagraph = new Paragraph();
				Paragraph phonesParagraph = new Paragraph();
				Paragraph contactAddParagraph = new Paragraph();

				Paragraph referenceParagraph = new Paragraph();

				String refName = Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).name) ? resumeData.reference
						.get(i).name : "";

				Chunk refNameChunk = new Chunk(refName, headingFont);

				String designation = Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).title) ? resumeData.reference
						.get(i).title : "";

				Chunk designationChunk = new Chunk(designation, contentFontItalic);

				float refNameWidth = refNameChunk.getWidthPoint();
				float designationWidth = designationChunk.getWidthPoint() + 20.0f;

				String refAndDesignationFiller = ResumePdf.getFiller(
						documentWidth - refNameWidth, designationWidth);
				Chunk refAndDesignationFillerChunk = new Chunk(
						refAndDesignationFiller);

				nameAndDesignParagraph.add(refNameChunk);
				nameAndDesignParagraph.add(refAndDesignationFillerChunk);
				nameAndDesignParagraph.add(designationChunk);

				if (!refName.equals("") || !designation.equals("")) {
					
					phonesParagraph.add(getLine(0.8f, BaseColor.DARK_GRAY));
					phonesParagraph.add(new Chunk("\n"));
					phonesParagraph.setSpacingBefore(-8);
				}

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
					String phone = "Contact : "
							+ phones.entrySet().iterator().next().getValue();

					Chunk phoneChunk = new Chunk(phone, contentFont);

					phonesParagraph.add(phoneChunk);
				}

				else if (phones.size() == 2) {

					Iterator<Entry<String, String>> iterator = phones
							.entrySet().iterator();

					HashMap.Entry entry = iterator.next();

					Chunk phoneChunk1 = new Chunk(entry.getKey() + " : "
							+ entry.getValue(), contentFont);

					entry = iterator.next();

					Chunk phoneChunk2 = new Chunk(entry.getKey() + " : "
							+ entry.getValue(), contentFont);

					Chunk fillerChunk = new Chunk(
							ResumePdf.getFiller(
									documentWidth - phoneChunk1.getWidthPoint()
											- 20.0f,
									phoneChunk2.getWidthPoint()));

					phonesParagraph.add(phoneChunk1);
					phonesParagraph.add(fillerChunk);
					phonesParagraph.add(phoneChunk2);
				}
				
				
				
				 String workAddress =
				 Common_Utilty.isNotEmptyString(resumeData.reference
				 .get(i).workAddress) ? "Work address : "
				 + resumeData.reference.get(i).workAddress : "";
				 
				 Chunk workAddChunk=new Chunk(workAddress,contentFont);
				 
				 String email= Common_Utilty.isNotEmptyString(resumeData.reference
						 .get(i).email) ? "E-mail : "
						 + resumeData.reference.get(i).email : "";
						 
				Chunk emailChunk;
				if(!workAddress.equals("")){
					emailChunk=new Chunk("\n"+email,contentFont);
				}
				else{
					emailChunk=new Chunk(email,contentFont);
				}
			
				
				 contactAddParagraph.add(workAddChunk);
				 contactAddParagraph.add(emailChunk);
				 
				 referenceParagraph.add(nameAndDesignParagraph);
				 referenceParagraph.add(phonesParagraph);
				 referenceParagraph.add(contactAddParagraph);
				 if(i<resumeData.reference.size()-1){
					 referenceParagraph.setSpacingAfter(5);
				 }

				blockBody.add(referenceParagraph);

			}
		}
		
		else if (blockName.equals(DECLARATION_HEADING)) {
//			LineSeparator line = new LineSeparator();
//
//			line.setLineWidth((float) 0.8);
//
//			line.setLineColor(BaseColor.DARK_GRAY);
//			
//			document.add(new Chunk("\n"));
//			document.add(line);
			Paragraph declarationParagraph=new Paragraph();

			String declarationString = Common_Utilty.isNotEmptyString(resumeData.declaration)?resumeData.declaration:"";
			Chunk declarationChunk = new Chunk(declarationString, contentFont);
			
			Chunk date = new Chunk("Date : ", contentFont);
			Chunk place = new Chunk("Place : ", contentFont);
			
			String name=Common_Utilty.isNotEmptyString(resumeData.name)?resumeData.name:"";
			Chunk nameChunk = new Chunk(name, contentFont);
			
			
			float gap = documentWidth+20 - place.getWidthPoint();
			String cfiller = ResumePdf.getFiller(gap, nameChunk.getWidthPoint());

			
			declarationParagraph.add(declarationChunk);
			
			
			
			if(!declarationString.equals("")){
				declarationParagraph.add(new Chunk("\n",contentFont));
				declarationParagraph.add(getLine(0.8f,BaseColor.DARK_GRAY));
				

			}
			declarationParagraph.add(new Chunk("\n\n",contentFont));
			declarationParagraph.add(date);
			declarationParagraph.add(new Chunk("\n",contentFont));
			declarationParagraph.add(place);
			declarationParagraph.add(new Chunk(cfiller,contentFont));
			declarationParagraph.add(name);
			
			
			blockBody.add(declarationParagraph);
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
