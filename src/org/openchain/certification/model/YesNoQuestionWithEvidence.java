/**
 * Copyright (c) 2016 Source Auditor Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
*/
package org.openchain.certification.model;
import java.util.regex.Pattern;

/**
 * A Yes/No question with additional artifact information (or evidence) provided
 * @author Gary O'Neall
 *
 */
public class YesNoQuestionWithEvidence extends YesNoQuestion {
	
	public static String TYPE_NAME = "YES_NO_EVIDENCE";

	private String evidencePrompt;
	private Pattern evidenceValidation;
	
	/**
	 * @param question Text for the question
	 * @param sectionName Name of the section containing the question
	 * @param number Number for the question
	 * @param specVersion Specification version
	 * @param language ISO 639 alpha-2 or alpha-3 language code
	 * @param correctAnswer Correct answer
	 * @param evidencePrompt Prompt to display when asking for the evidence
	 * @param evidenceValidation
	 * @throws QuestionException
	 */
	public YesNoQuestionWithEvidence(String question, String sectionName, 
			String number, String specVersion, String language, YesNo correctAnswer,
			String evidencePrompt, Pattern evidenceValidation) throws QuestionException {
		super(question, sectionName, number, specVersion, language, correctAnswer);
		this.evidencePrompt = evidencePrompt;
		this.evidenceValidation = evidenceValidation;
		this.type = TYPE_NAME;
	}

	/**
	 * @return the evidencePrompt
	 */
	public String getEvidencePrompt() {
		return evidencePrompt;
	}

	/**
	 * @param evidencePrompt the evidencePrompt to set
	 */
	public void setEvidencePrompt(String evidencePrompt) {
		this.evidencePrompt = evidencePrompt;
	}

	/**
	 * @return the evidenceValidation
	 */
	public Pattern getEvidenceValidation() {
		return evidenceValidation;
	}

	/**
	 * @param evidenceValidation the evidenceValidation to set
	 */
	public void setEvidenceValidation(Pattern evidenceValidation) {
		this.evidenceValidation = evidenceValidation;
	}
	
	@Override
	public boolean validate(Object answer) {
		return super.validate(answer);
	}
	
	public boolean validateEvidence(String evidence) {
		if (this.evidenceValidation == null) {
			return true;
		} else {
			return this.evidenceValidation.matcher(evidence).matches();
		}
	}
}
