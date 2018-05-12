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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import com.opencsv.CSVWriter;

/**
 * Responses to a survey from a responder
 * @author Gary O'Neall
 *
 */
public class SurveyResponse {
	
	transient static final String[] CSV_COLUMNS = new String[] {
		"Number", "Question", "Answer", "Additional Information"
	};

	User responder;
	/**
	 * Map of the question number to answer
	 */
	Map<String, Answer> responses;
	String specVersion;
	String language;
	Survey survey;
	boolean submitted;

	private boolean approved;

	private boolean rejected;

	private String id;
	
	/**
	 * @param specVersion Version for the spec
	 * @param language tag in IETF RFC 5646 format
	 */
	public SurveyResponse(String specVersion, String language) {
		this.specVersion = specVersion;
		this.language = language;
	}
	/**
	 * @return the responder
	 */
	public User getResponder() {
		return responder;
	}
	/**
	 * @param responder the responder to set
	 */
	public void setResponder(User responder) {
		this.responder = responder;
	}
	/**
	 * @return the responses
	 */
	public Map<String, Answer> getResponses() {
		return responses;
	}
	/**
	 * @param responses the responses to set
	 */
	public void setResponses(Map<String, Answer> responses) {
		this.responses = responses;
	}
	/**
	 * @return the surveyVersion
	 */
	public String getSpecVersion() {
		return specVersion;
	}
	/**
	 * @param surveyVersion the surveyVersion to set
	 */
	public void setSpecVersion(String surveyVersion) {
		this.specVersion = surveyVersion;
	}
	/**
	 * @return the survey
	 */
	public Survey getSurvey() {
		return survey;
	}
	/**
	 * @param survey the survey to set
	 */
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	/**
	 * @return the submitted
	 */
	public boolean isSubmitted() {
		return submitted;
	}
	/**
	 * @param submitted the submitted to set
	 */
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
	
	/**
	 * @return a list of all questions which either have not been answered or where the answer is invalid
	 */
	public List<Question> invalidAnswers() {
		List<Question> retval = new ArrayList<Question>();
		Set<String> allQuestionNumbers = this.survey.getQuestionNumbers();
		for (String questionNumber:allQuestionNumbers) {
			Question question = this.survey.getQuestion(questionNumber);
			if (question.getSubQuestionOfNumber() == null) {
				// we ignore any subquestions since they will be covered by the parent
				Answer answer = this.responses.get(questionNumber);
				if (answer == null || !question.validate(answer)) {
					retval.add(question);
				}
			}
		}
		Collections.sort(retval);
		return retval;
	}
	/**
	 * Output the responses in a CSV format
	 * @param out
	 * @throws IOException
	 */
	public void printCsv(PrintWriter out) throws IOException {
		CSVWriter csv = new CSVWriter(out);
		try {
			csv.writeNext(new String[] {"Questionnaire Version=",this.getSpecVersion()});
			csv.writeNext(CSV_COLUMNS);
			
			// sort the responses by question number
			TreeMap<String, Answer> sortedMap = new TreeMap<String, Answer>();
			sortedMap.putAll(responses);
			Iterator<Entry<String, Answer>> iter = sortedMap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Answer> entry = iter.next();
				Question question = this.survey.getQuestion(entry.getKey());
				if (question != null) {
					if (entry.getValue() instanceof SubQuestionAnswers &&
							((SubQuestionAnswers)entry.getValue()).getSubAnswers().size() == 0) {
						continue;	// skip any subquestion answers that do not have any subquestions
					}
					String[] nextLine = new String[CSV_COLUMNS.length];
					nextLine[0] = question.getNumber();
					nextLine[1] = question.getQuestion();
					nextLine[2] = entry.getValue().getAnswerString();
					if (entry.getValue() instanceof YesNoAnswerWithEvidence) {
						nextLine[3] = ((YesNoAnswerWithEvidence)entry.getValue()).getEvidence();
					} else {
						nextLine[3] = "";
					}
					csv.writeNext(nextLine);
				}
				
			}
		} finally {
			csv.close();
		}
	}
	public boolean isApproved() {
		return this.approved;
	}
	public boolean isRejected() {
		return this.rejected;
	}
	/**
	 * @param approved the approved to set
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	/**
	 * @param rejected the rejected to set
	 */
	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
