package org.codejudge.sb.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.codejudge.sb.dto.MarkModel;
import org.codejudge.sb.exception.CustomException;
import org.codejudge.sb.exception.CustomException404;
import org.codejudge.sb.model.Lead;
import org.codejudge.sb.repo.LeadRepository;
import org.codejudge.sb.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeadServiceImpl implements LeadService {

	Set<String> emails = new HashSet<>();
	Set<Long> mobiles = new HashSet<>();
	@Autowired
	private LeadRepository leadRepository;

	@Override
	public Lead getLead(String id) throws CustomException, CustomException404 {
		int leadId = parseId(id);
		Lead lead = leadRepository.findOne(leadId);
		if (lead == null) {
			throw new CustomException404();
		}
		return lead;
	}

	@Override
	public boolean checkEmailAlreadyPresent(String email) {
		return emails.contains(email);
	}

	@Override
	public Lead saveLead(Lead lead) throws CustomException {
		if (mobiles.contains(lead.getMobile()) || lead.getEmail().equals("") || emails.contains(lead.getEmail())) {
			throw new CustomException();
		}
		emails.add(lead.getEmail());
		mobiles.add(lead.getMobile());
		lead.setStatus("Created");
		return leadRepository.save(lead);
	}

	@Override
	public void deleteLead(String id) throws CustomException {
		int leadId = parseId(id);
		leadRepository.delete(leadId);
	}

	@Override
	public void markLeader(String id, @Valid MarkModel markModel) throws CustomException {
		int leadId = parseId(id);
		Lead lead = leadRepository.findOne(leadId);
		if (lead != null) {
			lead.setCommunication(markModel.getCommunication());
			lead.setStatus("Contacted");
			leadRepository.save(lead);
		}
	}

	@Override
	public void updateLead(String id, Lead updatedLead) throws CustomException {
		int leadId = parseId(id);
		Lead lead = leadRepository.findOne(leadId);
		if (lead != null) {
			if ((!lead.getEmail().equals(updatedLead.getEmail())) && emails.contains(updatedLead.getEmail())) {
				throw new CustomException();
			}
			if ((!lead.getMobile().equals(updatedLead.getMobile())) && mobiles.contains(updatedLead.getMobile())) {
				throw new CustomException();
			}
			updatedLead.setId(leadId);
			leadRepository.save(updatedLead);
		} else {
			throw new CustomException();
		}
	}

	private int parseId(String id) throws CustomException {
		try {
			int leadId = Integer.parseInt(id);
			return leadId;
		} catch (Exception e) {
			throw new CustomException();
		}
	}

}
