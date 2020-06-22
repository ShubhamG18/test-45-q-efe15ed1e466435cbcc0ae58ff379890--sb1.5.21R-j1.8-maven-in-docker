package org.codejudge.sb.controller;

import javax.validation.Valid;

import org.codejudge.sb.dto.ErrorResponseDto;
import org.codejudge.sb.dto.MarkModel;
import org.codejudge.sb.exception.CustomException;
import org.codejudge.sb.exception.CustomException404;
import org.codejudge.sb.model.Lead;
import org.codejudge.sb.service.LeadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class AppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @ApiOperation("This is the hello world api")
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!!";
    }

    @Autowired
    private LeadService leadService;

    @GetMapping(value = { "/api/leads", "/api/leads/{id}" })
    public ResponseEntity<Object> fetchLead(@PathVariable(required = false, value = "id") String id)
            throws CustomException, CustomException404 {
        Lead lead = leadService.getLead(id);
        return new ResponseEntity<>(lead, HttpStatus.OK);
    }

    @PostMapping("/api/leads/")
    public ResponseEntity<Object> saveLead(@Valid @RequestBody Lead lead) throws CustomException {
        Lead lead2 = leadService.saveLead(lead);
        return new ResponseEntity<>(lead2, HttpStatus.CREATED);
    }

    @PutMapping(value = { "/api/leads", "/api/leads/{id}" })
    public ResponseEntity<Object> updateLead(@PathVariable(required = false, value = "id") String id,
            @Valid @RequestBody Lead lead) throws CustomException {
        leadService.updateLead(id, lead);
        ErrorResponseDto erd = new ErrorResponseDto("sucesss");
        return new ResponseEntity<>(erd, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = { "/api/leads", "/api/leads/{id}" })
    public ResponseEntity<Object> deleteLead(@PathVariable(required = false, value = "id") String id)
            throws CustomException {
        leadService.deleteLead(id);
        ErrorResponseDto erd = new ErrorResponseDto("sucesss");
        return new ResponseEntity<>(erd, HttpStatus.OK);
    }

    @PutMapping(value = { "/api/mark_lead", "/api/mark_lead/{id}" })
    public ResponseEntity<ErrorResponseDto> markLead(@PathVariable(required = false, value = "id") String id,
            @Valid @RequestBody MarkModel markModel) throws CustomException {
        leadService.markLeader(id, markModel);
        ErrorResponseDto erd = new ErrorResponseDto("Contacted");
        erd.setCommunication(markModel.getCommunication());
        return new ResponseEntity<>(erd, HttpStatus.ACCEPTED);
    }

}
