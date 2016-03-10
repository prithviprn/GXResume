package com.grexoft.resume.pdf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class ElegantResumePdf extends ResumePdf {

	private float blockBodyCellWidth;

	public ElegantResumePdf(Context context, String templateName) {
		super(context, templateName);
	}

	@Override
	protected void setTemplateSpecifications(String templateName) {
		this.templateName = templateName;
		fontFileName = "CALIBRIL.TTF";

	}

	@Override
	protected Paragraph getBlockParagraph(String blockName)
			throws DocumentException {

		Paragraph block = new Paragraph();

		PdfPTable blockTable = new PdfPTable(2);

		PdfPCell blockHeadingCell = new PdfPCell();

		blockHeadingCell.addElement(getBlockHeadingParagraph(blockName));

		blockHeadingCell.setBorder(Rectangle.NO_BORDER);

		blockHeadingCell.setPaddingRight(20);
		blockHeadingCell.setPaddingTop(5);
		blockHeadingCell.setPaddingBottom(5);

		// blockHeadingCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		blockTable.addCell(blockHeadingCell);

		PdfPCell blockBodyCell = new PdfPCell();

		blockBodyCell.addElement(getBlockBodyParagraph(blockName));

		blockBodyCell.setBorder(Rectangle.NO_BORDER);
		blockBodyCell.setPaddingTop(5);
		blockBodyCell.setPaddingBottom(5);

		blockBodyCellWidth = blockBodyCell.getWidth();

		System.out.println(blockBodyCellWidth);

		// blockBodyCell.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);

		blockTable.addCell(blockBodyCell);
		// blockTable.setSpacingBefore(5);
		// blockTable.setSpacingAfter(5);
		blockTable.setWidthPercentage(100);
		blockTable.setWidths(new float[] { (float) 2.0, (float) 6.0 });

		block.add(blockTable);

		onBlockCreated(block, blockName);

		return block;
	}

	@Override
	protected Paragraph getBlockHeadingParagraph(String blockName) {
		System.out.println("getBlockHeadingParagraph()");

		Paragraph heading = new Paragraph();

		Chunk headChunk = new Chunk(blockName.toUpperCase(Locale.US),
				headingFont);

		heading.add(headChunk);
		heading.setAlignment(Paragraph.ALIGN_RIGHT);

		System.out.println("inside getBlockheadingparagraph() . blockname : "
				+ blockName);

		return heading;

	}

	@Override
	protected void setTitleOfDocument() throws DocumentException {

		try {

			if (!document.isOpen())
				return;

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
					presentAddressStr = presentAddressStr + resumeData.state
							+ ",";

				}
				presentAddressStr = presentAddressStr + resumeData.country;
			}

			Paragraph headPara = new Paragraph();

			if (!presentAddressStr.equals("")) {

				Chunk presentAddChunk = new Chunk(presentAddressStr,
						contentFont);
				Phrase addPhrase = new Phrase();

				addPhrase.add(presentAddChunk);
				headPara.add(addPhrase);

			}

			String contactstr = (resumeData.primarycontact != null) ? resumeData.primarycontact
					: "";

			Chunk contactBodyChunk = new Chunk(contactstr, contentFont);

			Phrase contactPhrase = new Phrase();

			contactPhrase.add(contactBodyChunk);

			headPara.add(new Chunk("\n"));
			headPara.add(contactPhrase);

			String emailstr = (resumeData.email != null) ? resumeData.email
					: "";

			Chunk emailBodyChunk = new Chunk(emailstr, contentFont);

			Phrase emailPhrase = new Phrase();

			emailPhrase.add(emailBodyChunk);
			headPara.add(new Chunk("\n"));
			headPara.add(emailPhrase);
			headPara.setAlignment(Paragraph.ALIGN_RIGHT);
			headPara.setSpacingAfter(10);
			document.add(headPara);

			
			String titleOfResume = "";
			if (Common_Utilty.isNotEmptyString(resumeData.title)
					&& resumeData.showTitle) {

				titleOfResume = " [ " + WordUtils.capitalize(resumeData.title)
						+ " ] ";
			}

			Chunk titleOfResumeChunk = new Chunk(titleOfResume, headingFont);

			String name = "";

			if (Common_Utilty.isNotEmptyString(resumeData.name)) {

				name = resumeData.name.toUpperCase(Locale.US);

			}
			Chunk nameChunk = new Chunk(name, titleFont);
			
			Chunk nameAndTitle=new Chunk(nameChunk+titleOfResume);
			nameAndTitle.setBackground(
					 BaseColor.LIGHT_GRAY,
					 4,
					 4,
					 document.getPageSize().getWidth()
					 - (document.leftMargin()
					 + document.rightMargin() + nameAndTitle
					 .getWidthPoint()), 8);

			//
			// Chunk nameChunk = new Chunk(
			// resumeData.name.toUpperCase(Locale.US), titleFont);
			//
			// nameChunk.setBackground(
			// BaseColor.LIGHT_GRAY,
			// 4,
			// 4,
			// document.getPageSize().getWidth()
			// - (document.leftMargin()
			// + document.rightMargin() + nameChunk
			// .getWidthPoint()), 8);
			//
			 Paragraph titlePara = new Paragraph();
			
			 titlePara.add(nameAndTitle);
			
			 document.add(titlePara);

		} catch (DocumentException e) {

			e.printStackTrace();

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected Paragraph getBlockBodyParagraph(String blockName)
			throws DocumentException {
		Paragraph blockBody = new Paragraph();

	//	blockBody.setIndentationLeft(5);

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

			Paragraph eduactionParagraph;

			for (int i = 0; i < resumeData.educationDetail.size(); i++) {
				eduactionParagraph = new Paragraph();
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
				eduactionParagraph.add(degree);

				eduactionParagraph.add(body);
				if (i < resumeData.educationDetail.size() - 1) {
					eduactionParagraph.setSpacingAfter(4);
				}

				blockBody.add(eduactionParagraph);

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
					float timeWidth = time.getWidthPoint();
					String fillerExp = ResumePdf
							.getFiller((float) (documentWidth * 0.75
									- compNameWidth - 10), timeWidth);
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
				if (i < resumeData.workExperience.size() - 1) {
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

					String fillerString = ResumePdf.getFiller(
							(float) (documentWidth * 0.75 - 10 - projNameChunk
									.getWidthPoint()), projecttimeChunk
									.getWidthPoint());

					Chunk fillerChunk = new Chunk(fillerString);

					projectParagraph.add(fillerChunk);

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

				if (i < resumeData.project.size() - 1) {
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
				if (i < resumeData.research.size() - 1) {
					researchParagraph.setSpacingAfter(4);
				}
				blockBody.add(researchParagraph);
			}
		} else if (blockName.equals(SKILLS_HEADING)) {

			StringBuilder skillStr = new StringBuilder();
			int i = 0;

			for (String skill : resumeData.skills) {

				i++;

				if (i < resumeData.skills.size())
					skillStr.append(skill + '\n');

				else if (i == resumeData.skills.size())
					skillStr.append(skill);

			}

			blockBody.add(skillStr.toString());

			blockBody.setFont(contentFont);

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

			blockBody.add(acheivementStr.toString());

			blockBody.setFont(contentFont);

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

			blockBody.add(strengthStr.toString());

			blockBody.setFont(contentFont);

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

			blockBody.add(extraCurricularStr.toString());

			blockBody.setFont(contentFont);

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

			blockBody.add(hobbyStr.toString());

			blockBody.setFont(contentFont);

		}

		else if (blockName.equals(PERSONAL_HEADING)) {
			Paragraph personalInfoPara = new Paragraph();
			String personalInfo = "";
			if (Common_Utilty.isNotEmptyString(resumeData.name)) {
				personalInfo = personalInfo + "Name : " + resumeData.name;
			}
			if (Common_Utilty.isNotEmptyString(resumeData.gender.getString()) &&!resumeData.gender.getString().equals("Not specified")) {
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

			if (resumeData.addressesAreSame) {
				if (Common_Utilty.isNotEmptyString(permanentAddressStr)) {
					personalInfo = personalInfo + "\n" + "Address : "
							+ permanentAddressStr;
				}
			} else {
				if (Common_Utilty.isNotEmptyString(permanentAddressStr)) {
					personalInfo = personalInfo + "\n" + "Parmanent address : "
							+ permanentAddressStr;
				}
			}

			Chunk personalInfoChunk = new Chunk(personalInfo, contentFont);

			blockBody.add(personalInfoChunk);

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

					// Chunk fillerChunk = new Chunk(Common_Utilty.getFiller(
					// documentWidth - phoneChunk1.getWidthPoint() - 20,
					// phoneChunk2.getWidthPoint()));

					referenceParagraph.add(phoneChunk1);
					referenceParagraph.add(new Chunk("\n"));
					referenceParagraph.add(phoneChunk2);
				}

				if (i < resumeData.reference.size() - 1) {
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

			String cfiller = ResumePdf
					.getFiller((float) (documentWidth * 0.75 - 20 - place
							.getWidthPoint()), name.getWidthPoint());
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

		block.setSpacingBefore(8f);

		if (!blockName.equals(ResumePdf.DECLARATION_HEADING)) {

			// LineSeparator line = new LineSeparator();
			//
			// line.setLineWidth((float) 0.8);
			//
			// line.setLineColor(BaseColor.DARK_GRAY);

			block.add(getLine(0.8f, BaseColor.DARK_GRAY));
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
