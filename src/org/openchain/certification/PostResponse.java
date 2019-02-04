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
package org.openchain.certification;

/**
 * JSON Response to a post
 * @author Gary O'Neall
 *
 */
public class PostResponse {
	
	// NOT_VERIFIED indicates the user has a valid password but has not been verified
	public enum Status {OK, ERROR, NOT_VERIFIED};
	private Status status;
	private String sectionName;
	private String error;
	private boolean admin;
	private SurveyUpdateResult surveyUpdateResult;
	private String language;

	public PostResponse(Status status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * @param sectionName the sectionName to set
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public boolean isAdmin() {
		return this.admin;
	}

	public void setSurveyUpdateResult(SurveyUpdateResult result) {
		this.surveyUpdateResult = result;
	}

	public SurveyUpdateResult getSurveyUpdateResult() {
		return this.surveyUpdateResult;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getLanguage() {
		return this.language;
	}
}
