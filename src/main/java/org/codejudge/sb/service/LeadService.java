package org.codejudge.sb.service;

import javax.validation.Valid;

import org.codejudge.sb.dto.MarkModel;
import org.codejudge.sb.exception.CustomException;
import org.codejudge.sb.exception.CustomException404;
import org.codejudge.sb.model.Lead;

public interface LeadService {

	Lead getLead(String id) throws CustomException, CustomException404;

	boolean checkEmailAlreadyPresent(String email);

	Lead saveLead(Lead lead) throws CustomException;

	void deleteLead(String id) throws CustomException;

	void markLeader(String id, @Valid MarkModel markModel) throws CustomException;

	void updateLead(String id, Lead lead) throws CustomException;

}
