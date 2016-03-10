package com.grexoft.resume.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

import android.content.Context;
import android.os.Environment;

import com.grexoft.resume.helpers.Common_Utilty;
import com.grexoft.resume.helpers.SdCardManager;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class StandOutResumePdf extends ResumePdf {

	public static final String TITLE = "title";

	private static final String contactPng = "CONTACT";
	private static final String emailPng = "EMAIL";
	private static final String ringPng = "RING";
	private static final String myPhoto = "PHOTO";

	Font subheadingFont;
	private String secondaryFontFileName;

	public StandOutResumePdf(Context context, String templateName) {
		super(context, templateName);
		isTopAddress = false;

	}

	protected void createDocument() {

		document = new Document(PageSize.A4, 0, 0, 0, 0);
		
		documentWidth= document.getPageSize().getWidth()
				- document.leftMargin() - document.rightMargin();
		setFonts();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(resumeFile));
		} catch (FileNotFoundException | DocumentException e) {

			e.printStackTrace();
		}
		
	}

	protected void setFonts() {

		BaseFont baseFont = null, baseFontContent = null;

		String fontDirectoryName = null;
		SdCardManager.CopyAssetsFontsToSdCard(context);
		try {
			fontDirectoryName = Environment.getExternalStorageDirectory()
					.toString()
					+ "/"
					+ Common_Utilty.APPLICATION_DIRECTORY
					+ "/" + Common_Utilty.FONT_DIRECTORY;

			baseFont = BaseFont.createFont(fontDirectoryName + "/"
					+ fontFileName, "UTF-8", BaseFont.EMBEDDED);
			baseFontContent = BaseFont.createFont(fontDirectoryName + "/"
					+ secondaryFontFileName, "UTF-8", BaseFont.EMBEDDED);
		} catch (DocumentException e) {

			e.printStackTrace();
		} catch (IOException e) {

			// SdCardManager.CopyAssetsFontsToSdCard(context);
			//
			// try {
			// fontDirectoryName = Environment.getExternalStorageDirectory()
			// .toString()
			// + "/"
			// + Common_Utilty.APPLICATION_DIRECTORY
			// + "/" + Common_Utilty.FONT_DIRECTORY;
			// baseFont = BaseFont.createFont(fontDirectoryName
			// + "/" + fontFileName, "UTF-8", BaseFont.EMBEDDED);
			// baseFontContent = BaseFont
			// .createFont(fontDirectoryName
			// + "/" + secondaryFontFileName, "UTF-8",
			// BaseFont.EMBEDDED);
			// e.printStackTrace();
			// } catch (DocumentException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			e.printStackTrace();
		}

		titleFont = new Font(baseFont, 16, Font.BOLD);

		headingFont = new Font(baseFont, 12, Font.BOLD);
		subheadingFont = new Font(baseFont, 10, Font.BOLD);

		contentFont = new Font(baseFontContent, 10);

		contentFontItalic = new Font(baseFontContent, 10, Font.ITALIC);

	}

	@Override
	protected void setTitleOfDocument() throws DocumentException {
		// TODO Auto-generated method stub

	}

	private Font getWhiteFont(Font f) {
		Font whiteFont = new Font(f);
		whiteFont.setColor(BaseColor.WHITE);
		return whiteFont;

	}

	private Image getPngFromSd(String blockName) throws DocumentException,
			BadElementException, IOException {
		Image image = null;
		String imageDirectory = null;

		String imageName = null;

		switch (blockName) {
		case myPhoto:
			imageName = Common_Utilty.isNotEmptyString(resumeData.image) ? resumeData.image.toString() : "";
			break;
		case contactPng:
			imageName = "ic_contact_hollow_white.png";
			break;
		case emailPng:
			imageName = "ic_email_hollow_white.png";
			break;
		case SKILLS_HEADING:
			imageName = "ic_skills_hollow_white.png";
			break;
		case STRENGTH_HEADING:
			imageName = "ic_strength_hollow_white.png";
			break;
		case OBJECTIVE_HEADING:
			imageName = "ic_objective_hollow.png";
			break;
		case EDUCATION_HEADING:
			imageName = "ic_degree_hollow.png";
			break;
		case EXPERIENCE_HEADING:
			imageName = "ic_work_hollow.png";
			break;
		case PROJECTS_HEADING:
			imageName = "ic_project_hollow.png";
			break;
		case RESEARCH_HEADING:
			imageName = "ic_research_hollow.png";
			break;
		case HOBBIES_HEADING:
			imageName = "ic_hobbies_hollow.png";
			break;
		case EXTRA_CURRICULAR_HEADING:
			imageName = "ic_extra_curricular_hollow.png";
			break;
		case ACHEIVEMENT_HEADING:
			imageName = "ic_achievement_hollow.png";
			break;
		case PERSONAL_HEADING:
			imageName = "ic_personal_hollow.png";
			break;
		case REFERENCE_HEADING:
			imageName = "ic_reference_hollow.png";
			break;
		case DECLARATION_HEADING:
			imageName = "ic_declaration_hollow.png";
			break;
		case ringPng:
			imageName = "ic_ring_white.jpg";
			break;
		}
		try {
			SdCardManager.CopyAssetsImageToSdCard(context);
			if (blockName.equals(myPhoto)) {
				imageDirectory = Environment.getExternalStorageDirectory()
						.toString()
						+ "/"
						+ Common_Utilty.APPLICATION_DIRECTORY
						+ "/" + Common_Utilty.IMAGE_DIRECTORY;
			} else {
				imageDirectory = Environment.getExternalStorageDirectory()
						.toString()
						+ "/"
						+ Common_Utilty.APPLICATION_DIRECTORY
						+ "/"
						+ Common_Utilty.IMAGE_DIRECTORY
						+ "/"
						+ Common_Utilty.PNG_DIRECTORY;
				System.out.println("in first try block image dirc"
						+ imageDirectory);

			}

			image = Image.getInstance(imageDirectory + "/" + imageName);

		} catch (DocumentException e) {
			e.printStackTrace();

		} catch (IOException ie) {
			ie.printStackTrace();
			// SdCardManager.CopyAssetsImageToSdCard(context);
			// try {
			// imageDirectory = Environment.getExternalStorageDirectory()
			// .toString()
			// + "/"
			// + Common_Utilty.APPLICATION_DIRECTORY
			// + "/" + Common_Utilty.IMAGE_DIRECTORY + "/"
			// +Common_Utilty.PNG_DIRECTORY;
			// image = Image.getInstance(imageDirectory + "/" + imageName);
			//
			// } catch (DocumentException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
		}

		return image;

	}

	protected Paragraph getTitleOfDocument() {

		Paragraph nameParagraph = new Paragraph();
		Paragraph photoParagraph = new Paragraph();
		Paragraph contactParagraph = new Paragraph();
		Paragraph emailParagraph = new Paragraph();
		Paragraph resumeTitleParagraph = new Paragraph();

		Paragraph finalParagraph = new Paragraph();
		

		String name = Common_Utilty.isNotEmptyString(resumeData.name) ? resumeData.name
				: "";
		Font titleWhiteFont = getWhiteFont(titleFont);
		Chunk nameChunk = new Chunk(name.toUpperCase(Locale.US), titleWhiteFont);
		nameChunk.setCharacterSpacing(1.5f);
		nameParagraph.add(nameChunk);
		nameParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		if (!name.equals("")) {
			finalParagraph.add(new Chunk("\n", contentFont));
			
		}
		finalParagraph.add(nameParagraph);
		String mobileNumber = Common_Utilty.isNotEmptyString(resumeData.primarycontact) ? resumeData.primarycontact
				: "";
		Font contentWhite = getWhiteFont(contentFont);

		Chunk mobileNumberChunk = new Chunk(mobileNumber, contentWhite);

		String email = Common_Utilty.isNotEmptyString(resumeData.email) ? resumeData.email
				: "";

		Chunk emailChunk = new Chunk(email, contentWhite);

		
		
     

		Image userImage = null, contactImage = null, mailImage = null;
		

		try {
			
			
			if(Common_Utilty.isNotEmptyString(resumeData.image) && resumeData.showImage){
				userImage = getPngFromSd(myPhoto);
				userImage.scalePercent(80);
				userImage.setSpacingBefore(10);
				photoParagraph.add(new Chunk(userImage, 0, 0, true));
				photoParagraph.setAlignment(Paragraph.ALIGN_CENTER);
				finalParagraph.add(new Chunk("\n",contentWhite));
				finalParagraph.add(photoParagraph);
			}
			
			
			
			mailImage = getPngFromSd(emailPng);
			
			if(!email.equals("")){
				mailImage.scaleAbsolute(15f, 15f);
				mailImage.setSpacingBefore(10);
				emailParagraph.add(new Chunk(mailImage, -5, -5, true));
				emailParagraph.add(emailChunk);
				emailParagraph.setAlignment(Paragraph.ALIGN_CENTER);
				finalParagraph.add(new Chunk("\n",contentWhite));
				finalParagraph.add(emailParagraph);
				
			}
			

			contactImage = getPngFromSd(contactPng);
			
			if(!mobileNumber.equals("")){
				contactImage.scaleAbsolute(15f, 15f);
				contactImage.setSpacingBefore(5);
				contactParagraph.add(new Chunk(contactImage, -5, -5, true));
				contactParagraph.add(mobileNumberChunk);
				
				finalParagraph.add(new Chunk("\n",contentWhite));
			
			}
			finalParagraph.add(contactParagraph);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		String resumeTitle = (Common_Utilty.isNotEmptyString(resumeData.title)&& resumeData.showTitle) ? 
				 resumeData.title
				: "";
		Chunk resumTitleChunk = new Chunk(resumeTitle.toUpperCase(Locale.US), titleWhiteFont);

		resumTitleChunk.setCharacterSpacing(1.5f);

		resumeTitleParagraph.add(resumTitleChunk);

		resumeTitleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		
		if(!resumeTitle.equals("")){
			finalParagraph.add(new Chunk("\n\n",contentWhite));
			

		}
		finalParagraph.add(resumeTitleParagraph);
	  finalParagraph.setAlignment(Paragraph.ALIGN_CENTER);

	return finalParagraph;

	}

	@Override
	protected void setTemplateSpecifications(String templateName) {
		this.templateName = templateName;
		secondaryFontFileName = "DroidSerif-Regular.ttf";
		fontFileName = "OSWALDL.TTF";

	}

	@Override
	protected Paragraph getBlockHeadingParagraph(String blockName) {
		Paragraph textParagraph = new Paragraph();
		Paragraph imageParagraph = new Paragraph();
		Paragraph heading = new Paragraph();
		Image headingImage = null;
		Chunk headChunk = null;

		if (blockName.equals(SKILLS_HEADING)
				|| blockName.equals(STRENGTH_HEADING)) {

			Font whiteHeadingFont = getWhiteFont(headingFont);

			headChunk = new Chunk(blockName.toUpperCase(Locale.US),
					whiteHeadingFont);

			headChunk.setCharacterSpacing(1.5f);
			textParagraph.add(headChunk);

			textParagraph.setAlignment(Paragraph.ALIGN_CENTER);
			heading.add(textParagraph);

			try {
				headingImage = getPngFromSd(blockName);

				headingImage.scaleAbsolute(25f, 25f);

				headingImage.setSpacingBefore(10.0f);

				imageParagraph.add(new Chunk(headingImage, 0, 0, true));

				imageParagraph.setAlignment(Paragraph.ALIGN_CENTER);

				heading.add(new Chunk("\n", contentFont));
				heading.add(imageParagraph);
				heading.setAlignment(Paragraph.ALIGN_CENTER);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			headChunk = new Chunk(blockName.toUpperCase(Locale.US), headingFont);
			headChunk.setCharacterSpacing(1.5f);
			try {
				headingImage = getPngFromSd(blockName);

				// headingImage.scaleAbsolute(25f, 25f);

				headingImage.scalePercent(25, 25);
				textParagraph.add(new Chunk(headingImage, -8, -7, true));
				textParagraph.add(headChunk);

				// heading.add(imageParagraph);
				heading.add(textParagraph);

			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		heading.setSpacingAfter(10.0f);
		return heading;
	}

	@Override
	public String createPdf() {
		try {

			createDocument();
			document.open();

			PdfPTable table = new PdfPTable(2);

			PdfPCell rightSideCell = new PdfPCell();

			PdfPCell leftSideCell = new PdfPCell();
			float pageHeight = document.getPageSize().getHeight();

			leftSideCell.setFixedHeight(pageHeight);
			leftSideCell.setBackgroundColor(BaseColor.DARK_GRAY);
			leftSideCell.disableBorderSide(Rectangle.BOX);
			leftSideCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			rightSideCell.setBackgroundColor(BaseColor.WHITE);
			rightSideCell.disableBorderSide(Rectangle.BOX);

			rightSideCell.setPadding(20);
			// leftSideCell.setPadding(30);
			leftSideCell.addElement(getLeftSideContent(TITLE));

			if (resumeData.skills != null && !resumeData.skills.isEmpty()) {

				leftSideCell.addElement(getLeftSideContent(SKILLS_HEADING));

			}
			if (resumeData.strength != null && !resumeData.strength.isEmpty()) {

				leftSideCell.addElement(getLeftSideContent(STRENGTH_HEADING));
			}

			if (Common_Utilty.isNotEmptyString(resumeData.objectives)) {

				rightSideCell
						.addElement(getRightSideContent(OBJECTIVE_HEADING));

			}

			if (resumeData.educationDetail != null
					&& !resumeData.educationDetail.isEmpty()) {

				rightSideCell
						.addElement(getRightSideContent(EDUCATION_HEADING));
			}

			if (resumeData.project != null && !resumeData.project.isEmpty()) {

				rightSideCell.addElement(getRightSideContent(PROJECTS_HEADING));

			}

			if (resumeData.workExperience != null
					&& !resumeData.workExperience.isEmpty()) {

				rightSideCell
						.addElement(getRightSideContent(EXPERIENCE_HEADING));

			}

			if (resumeData.hobbies != null && !resumeData.hobbies.isEmpty()) {

				rightSideCell.addElement(getRightSideContent(HOBBIES_HEADING));
			}

			if (resumeData.achive != null && !resumeData.achive.isEmpty()) {

				rightSideCell
						.addElement(getRightSideContent(ACHEIVEMENT_HEADING));

			}
			if (resumeData.exCarr != null && !resumeData.exCarr.isEmpty()) {

				rightSideCell
						.addElement(getRightSideContent(EXTRA_CURRICULAR_HEADING));

			}

			if (resumeData.research != null && !resumeData.research.isEmpty()) {

				rightSideCell.addElement(getRightSideContent(RESEARCH_HEADING));
				System.out
						.println("Iin creat pdf reasrch cell" + rightSideCell);
			}

			rightSideCell.addElement(getRightSideContent(PERSONAL_HEADING));

			if (resumeData.reference != null && !resumeData.reference.isEmpty()) {

				rightSideCell
						.addElement(getRightSideContent(REFERENCE_HEADING));

			}
			if (Common_Utilty.isNotEmptyString(resumeData.declaration)) {
				rightSideCell
						.addElement(getRightSideContent(DECLARATION_HEADING));

			}

			table.addCell(leftSideCell);
			table.addCell(rightSideCell);
			table.setWidthPercentage(100f);
			table.setWidths(new float[] { 3.0f, 7.0f });
			document.add(table);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			document.close();
			File dir1 = new File(Environment.getExternalStorageDirectory()
					.toString()
					+ "/"
					+ Common_Utilty.APPLICATION_DIRECTORY
					+ "/" + Common_Utilty.FONT_DIRECTORY);
			File dir2 = new File(Environment.getExternalStorageDirectory()
					.toString()
					+ "/"
					+ Common_Utilty.APPLICATION_DIRECTORY
					+ "/"
					+ Common_Utilty.IMAGE_DIRECTORY
					+ "/"
					+ Common_Utilty.PNG_DIRECTORY);
			 if(dir1.exists()){
			SdCardManager.deleteDirectory(dir1);
			 }
			 if(dir2.exists()){
			SdCardManager.deleteDirectory(dir2);

			 }

		}
		return templateName;
		
	}

	@Override
	protected Paragraph getBlockBodyParagraph(String blockName)
			throws DocumentException {

		Paragraph blockBody = new Paragraph();
		Font WhiteFont = getWhiteFont(contentFont);
		if (blockName.equals(OBJECTIVE_HEADING)) {

			String objectiveString = Common_Utilty
					.isNotEmptyString(resumeData.objectives) ? resumeData.objectives
					: "";

			Chunk objectiveChunk = new Chunk(objectiveString, contentFont);

			blockBody.add(objectiveChunk);
			blockBody.add(new Chunk("\n", contentFont));
			blockBody.add(getLine(0.5f, BaseColor.BLACK));

		} else if (blockName.equals(EXPERIENCE_HEADING)) {
			Paragraph work;

			for (int i = 0; i < resumeData.workExperience.size(); i++) {
				work = new Paragraph();

				Chunk compName = new Chunk(
						resumeData.workExperience.get(i).compName
								.toUpperCase(Locale.US),
						subheadingFont);
				compName.setCharacterSpacing(1.5f);
				work.add(compName);

				if (Common_Utilty.isNotEmptyString(resumeData.workExperience
						.get(i).timeStart)
						&& Common_Utilty
								.isNotEmptyString(resumeData.workExperience
										.get(i).timeEnd)) {
					String timePeriod = resumeData.workExperience.get(i).timeStart
							+ "-" + resumeData.workExperience.get(i).timeEnd;
					Chunk time = new Chunk(timePeriod, contentFont);

					work.add("   ");
					work.add(time);
				}

				String designation = (Common_Utilty
						.isNotEmptyString(resumeData.workExperience.get(i).jobDesig) ? "\n"
						+ resumeData.workExperience.get(i).jobDesig
						: "");
				Chunk designationChunk = new Chunk(
						designation.toUpperCase(Locale.US), subheadingFont);
				designationChunk.setCharacterSpacing(1.5f);

				String description = (Common_Utilty
						.isNotEmptyString(resumeData.workExperience.get(i).description) ? "\n"
						+ "Description: "
						+ resumeData.workExperience.get(i).description
						: "");

				Chunk descriptionChunk = new Chunk(description, contentFont);
				work.add(designationChunk);
				work.add(descriptionChunk);

				if (i < resumeData.workExperience.size() - 1) {
					work.add(new Chunk("\n\n", contentFont));
				}
				work.setSpacingBefore(-16);

				try {
					Image ringImage = getPngFromSd(ringPng);

					ringImage.scalePercent(25);
					Paragraph ringParagraph = new Paragraph(new Chunk(
							ringImage, -24.f, -0, true));
					blockBody.add(ringParagraph);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				blockBody.add(work);

			}
		}

		else if (blockName.equals(EDUCATION_HEADING)) {

			Paragraph eduPara;

			for (int i = 0; i < resumeData.educationDetail.size(); i++) {
				eduPara = new Paragraph();
				Chunk degree = new Chunk(
						resumeData.educationDetail.get(i).degree
								.toUpperCase(Locale.US),
						subheadingFont);

				degree.setCharacterSpacing(1.5f);

				String year = (Common_Utilty
						.isNotEmptyString(resumeData.educationDetail.get(i).passout) ? resumeData.educationDetail
						.get(i).passout : "");

				Chunk yearChunk = new Chunk(year, contentFont);

				String university = (Common_Utilty
						.isNotEmptyString(resumeData.educationDetail.get(i).university) ? "\n"
						+ resumeData.educationDetail.get(i).university
						: "");
				Chunk universityChunk = new Chunk(
						university.toUpperCase(Locale.US), subheadingFont);

				universityChunk.setCharacterSpacing(1.5f);
				String percentage = (Common_Utilty
						.isNotEmptyString(resumeData.educationDetail.get(i).result) ? "\nPercentage/CGPA : "
						+ resumeData.educationDetail.get(i).result
						: "");

				Chunk percentageChunk = new Chunk(percentage, contentFont);

				try {
					Image ringImage = getPngFromSd(ringPng);

					ringImage.scalePercent(25);
					Paragraph ringParagraph = new Paragraph(new Chunk(
							ringImage, -24.f, -0, true));
					blockBody.add(ringParagraph);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				eduPara.add(degree);
				eduPara.add("   ");
				eduPara.add(yearChunk);
				eduPara.add(universityChunk);
				eduPara.add(percentageChunk);
				if (i < resumeData.educationDetail.size() - 1) {
					eduPara.add(new Chunk("\n\n", contentFont));
				}
				//
				eduPara.setSpacingBefore(-16);

				blockBody.add(eduPara);
				//
			}

		} else if (blockName.equals(PROJECTS_HEADING)) {
			Paragraph projectPara;
			for (int i = 0; i < resumeData.project.size(); i++) {

				projectPara = new Paragraph();
				Chunk projNameChunk = new Chunk(
						resumeData.project.get(i).title.toUpperCase(Locale.US),
						subheadingFont);
				projNameChunk.setCharacterSpacing(1.5f);

				projectPara.add(projNameChunk);

				if (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).timebeg)
						&& Common_Utilty.isNotEmptyString(resumeData.project
								.get(i).timeend)) {

					String projectTime = resumeData.project.get(i).timebeg
							+ "-" + resumeData.project.get(i).timeend;
					Chunk projecttimeChunk = new Chunk(projectTime, contentFont);

					Chunk projectFillerChunk = new Chunk("   ");
					projectPara.add(projectFillerChunk);
					projectPara.add(projecttimeChunk);

				}
				String role = (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).role) ? "\n"
						+ resumeData.project.get(i).role
						: "");
				Chunk roleChunk = new Chunk(role.toUpperCase(Locale.US),
						subheadingFont);
				roleChunk.setCharacterSpacing(1.5f);

				String projectDetail = (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).size) ? "\n"
						+ "Team size: " + resumeData.project.get(i).size
						: "")
						+ (Common_Utilty.isNotEmptyString(resumeData.project
								.get(i).description) ? "\n" + "Description: "
								+ resumeData.project.get(i).description : "");

				Chunk projectDetailChunk = new Chunk(projectDetail, contentFont);
				projectPara.add(roleChunk);
				projectPara.add(projectDetailChunk);

				try {
					Image ringImage = getPngFromSd(ringPng);

					ringImage.scalePercent(25);
					Paragraph ringParagraph = new Paragraph(new Chunk(
							ringImage, -24.f, -0, true));
					blockBody.add(ringParagraph);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i < resumeData.project.size() - 1) {
					projectPara.add(new Chunk("\n\n", contentFont));

				}
				projectPara.setSpacingBefore(-16);

				blockBody.add(projectPara);
				//
			}
		} else if (blockName.equals(RESEARCH_HEADING)) {

			Paragraph researchParagraph;
			for (int i = 0; i < resumeData.research.size(); i++) {
				researchParagraph = new Paragraph();
				Chunk researchTitle = new Chunk(
						resumeData.research.get(i).papertitle
								.toUpperCase(Locale.US),
						subheadingFont);

				researchTitle.setCharacterSpacing(1.5f);
				researchParagraph.add(researchTitle);

				String journal = Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).journal) ? "\n"
						+ resumeData.research.get(i).journal
						: "";

				Chunk journalChunk = new Chunk(journal.toUpperCase(Locale.US),
						subheadingFont);
				journalChunk.setCharacterSpacing(1.5f);

				String reserchDetail = (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).volume) ? "\n"
						+ "Volume: " + resumeData.research.get(i).volume
						: "")
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
				researchParagraph.add(journalChunk);
				researchParagraph.add(researchDetailChunk);

				if (i < resumeData.research.size() - 1) {
					researchParagraph.add(new Chunk("\n\n", contentFont));
				}

				try {
					Image ringImage = getPngFromSd(ringPng);

					ringImage.scalePercent(25);
					Paragraph ringParagraph = new Paragraph(new Chunk(
							ringImage, -24.f, -0, true));
					blockBody.add(ringParagraph);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				researchParagraph.setSpacingBefore(-16);
				System.out.println("In get block Parar: Resrch para"
						+ researchParagraph);
				blockBody.add(researchParagraph);
				System.out.println("In get block Parar: Resrch para blockbody"
						+ blockBody);
			}
		} else if (blockName.equals(REFERENCE_HEADING)) {
			for (int i = 0; i < resumeData.reference.size(); i++) {
				Paragraph referenceParagraph = new Paragraph();
				Chunk refName = new Chunk(
						resumeData.reference.get(i).name.toUpperCase(Locale.US),
						subheadingFont);

				refName.setCharacterSpacing(1.5f);
				referenceParagraph.add(refName);

				String desig = (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).title) ? "\n"
						+ resumeData.reference.get(i).title
						: "");

				Chunk desigChunk = new Chunk(desig.toUpperCase(Locale.US),
						subheadingFont);

				desigChunk.setCharacterSpacing(1.5f);

				String refDetail = (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).workAddress) ? "\n"
						+ "Work address: "
						+ resumeData.reference.get(i).workAddress
						: "")
						+ (Common_Utilty.isNotEmptyString(resumeData.reference
								.get(i).email) ? "\n" + "E-mail: "
								+ resumeData.reference.get(i).email : "");

				Chunk refDetailChunk = new Chunk(refDetail, contentFont);

				referenceParagraph.add(desigChunk);
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
					referenceParagraph.add(new Chunk("\n"));
					referenceParagraph.add(phoneChunk2);

				}

				try {
					Image ringImage = getPngFromSd(ringPng);

					ringImage.scalePercent(25);
					Paragraph ringParagraph = new Paragraph(new Chunk(
							ringImage, -24.f, -0, true));
					blockBody.add(ringParagraph);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				referenceParagraph.setSpacingBefore(-16);
				if (i < resumeData.reference.size() - 1) {
					referenceParagraph.add(new Chunk("\n\n", contentFont));
				}

				blockBody.add(referenceParagraph);

			}
		} else if (blockName.equals(DECLARATION_HEADING)) {

			String declarationString = Common_Utilty
					.isNotEmptyString(resumeData.declaration) ? resumeData.declaration
					: "";
			Chunk date = new Chunk("Date : ", contentFont);
			Chunk declarationChunk = new Chunk(declarationString, contentFont);
			Chunk place = new Chunk("Place : ", contentFont);
			Chunk name = new Chunk(resumeData.name, contentFont);

			float gap = (float) (documentWidth * 0.75 - 20 - place
					.getWidthPoint());

			String filler = ResumePdf.getFiller(gap, name.getWidthPoint());

			if (!declarationString.equals("")) {
				blockBody.setSpacingBefore(-10);
				blockBody.add(getLine(0.8f, BaseColor.DARK_GRAY));
			}
			blockBody.add(declarationChunk);
			blockBody.add("\n\n");
			blockBody.add(date);
			blockBody.add("\n");
			blockBody.add(place);
			blockBody.add(new Chunk(filler, contentFont));

			blockBody.add(name);

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
			Chunk skillChunk = new Chunk(skillStr.toString(), WhiteFont);
			blockBody.add(skillChunk);
			blockBody.setAlignment(Paragraph.ALIGN_CENTER);

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

			Chunk achive = new Chunk(acheivementStr.toString(), contentFont);
			blockBody.add(achive);

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

			blockBody.add(new Chunk(strengthStr.toString(), WhiteFont));

			blockBody.setAlignment(Paragraph.ALIGN_CENTER);

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

		}

		return blockBody;
	}

	private PdfPTable getRightSideContent(String blockName)
			throws Exception {
		PdfPTable table = new PdfPTable(1);
		
		try{
			
			PdfPCell cellHeading;
			if (blockName.equals(OBJECTIVE_HEADING)) {
				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading.setPadding(5);
				cellHeading.addElement(getBlockHeadingParagraph(OBJECTIVE_HEADING));
				cellHeading.addElement(getBlockBodyParagraph(OBJECTIVE_HEADING));

				table.addCell(cellHeading);
			} else if (blockName.equals(EDUCATION_HEADING)) {
				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading.addElement(getBlockHeadingParagraph(EDUCATION_HEADING));
				cellHeading.addElement(getExtraBorderTable());
				cellHeading.addElement(getBlockBodyTable(EDUCATION_HEADING));

				table.addCell(cellHeading);
				table.setSpacingBefore(10);
			} else if (blockName.equals(PROJECTS_HEADING)) {
				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading.addElement(getBlockHeadingParagraph(PROJECTS_HEADING));
				cellHeading.addElement(getExtraBorderTable());
				cellHeading.addElement(getBlockBodyTable(PROJECTS_HEADING));

				table.addCell(cellHeading);
				table.setSpacingBefore(10);

			} else if (blockName.equals(EXPERIENCE_HEADING)) {

				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading
						.addElement(getBlockHeadingParagraph(EXPERIENCE_HEADING));
				cellHeading.addElement(getExtraBorderTable());
				cellHeading.addElement(getBlockBodyTable(EXPERIENCE_HEADING));

				table.addCell(cellHeading);
				table.setSpacingBefore(10);
			} else if (blockName.equals(HOBBIES_HEADING)) {
				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading.addElement(getBlockHeadingParagraph(HOBBIES_HEADING));
				cellHeading.addElement(getExtraBorderTable());
				cellHeading.addElement(getBlockBodyTable(HOBBIES_HEADING));

				table.addCell(cellHeading);
				table.setSpacingBefore(10);

			} else if (blockName.endsWith(ACHEIVEMENT_HEADING)) {
				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading
						.addElement(getBlockHeadingParagraph(ACHEIVEMENT_HEADING));
				cellHeading.addElement(getExtraBorderTable());
				cellHeading.addElement(getBlockBodyTable(ACHEIVEMENT_HEADING));

				table.addCell(cellHeading);
				table.setSpacingBefore(10);

			} else if (blockName.equals(EXTRA_CURRICULAR_HEADING)) {
				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading
						.addElement(getBlockHeadingParagraph(EXTRA_CURRICULAR_HEADING));
				cellHeading.addElement(getExtraBorderTable());
				cellHeading.addElement(getBlockBodyTable(EXTRA_CURRICULAR_HEADING));

				table.addCell(cellHeading);
				table.setSpacingBefore(10);
			} else if (blockName.equals(RESEARCH_HEADING)) {
				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading.addElement(getBlockHeadingParagraph(RESEARCH_HEADING));
				cellHeading.addElement(getExtraBorderTable());
				cellHeading.addElement(getBlockBodyTable(RESEARCH_HEADING));
				System.out.println("In right side block" + cellHeading);
				table.addCell(cellHeading);
				table.setSpacingBefore(10);
			} else if (blockName.equals(PERSONAL_HEADING)) {
				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading.addElement(getBlockHeadingParagraph(PERSONAL_HEADING));
				cellHeading.addElement(getExtraBorderTable());
				cellHeading.addElement(getBlockBodyTable(PERSONAL_HEADING));

				table.addCell(cellHeading);
				table.setSpacingBefore(10);
			} else if (blockName.equals(REFERENCE_HEADING)) {
				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading.addElement(getBlockHeadingParagraph(REFERENCE_HEADING));
				cellHeading.addElement(getExtraBorderTable());
				cellHeading.addElement(getBlockBodyTable(REFERENCE_HEADING));

				table.addCell(cellHeading);
				table.setSpacingBefore(10);
			} else if (blockName.equals(DECLARATION_HEADING)) {
				cellHeading = new PdfPCell();
				cellHeading.disableBorderSide(Rectangle.BOX);
				cellHeading.setPadding(5);
				cellHeading
						.addElement(getBlockHeadingParagraph(DECLARATION_HEADING));
				cellHeading.addElement(getBlockBodyParagraph(DECLARATION_HEADING));
				table.addCell(cellHeading);
				table.setSpacingBefore(10);
			}
			table.setWidthPercentage(100);

			System.out.println("returnd table" + table);
		}
		catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}
		
		
		
		return table;

	}

	private Paragraph getLeftSideContent(String block) throws Exception {
		Paragraph leftBlockParagraph = new Paragraph();

		try{
			
			if (block.equals(SKILLS_HEADING)) {
				leftBlockParagraph.add(getBlockHeadingParagraph(SKILLS_HEADING));
				leftBlockParagraph.add(getBlockBodyParagraph(SKILLS_HEADING));

			} else if (block.equals(STRENGTH_HEADING)) {
				leftBlockParagraph.add(getBlockHeadingParagraph(STRENGTH_HEADING));
				leftBlockParagraph.add(getBlockBodyParagraph(STRENGTH_HEADING));
			} else if (block.equals(TITLE)) {
				leftBlockParagraph.add(getTitleOfDocument());
			}

			if (leftBlockParagraph != null && !leftBlockParagraph.equals("")) {

				Paragraph lineParagraph = new Paragraph();
				lineParagraph.setIndentationLeft(10);
				lineParagraph.setIndentationRight(10);
				lineParagraph.add(getLine(0.5f, BaseColor.WHITE));
				lineParagraph.setAlignment(Paragraph.ALIGN_CENTER);
				leftBlockParagraph.add(lineParagraph);
			}
			leftBlockParagraph.setAlignment(Paragraph.ALIGN_CENTER);
			leftBlockParagraph.setSpacingBefore(10);
			
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		
		
		return leftBlockParagraph;

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
