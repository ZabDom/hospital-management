package com.dzablotny.legacy.endpoint;

import com.dzablotny.legacy.repository.LegacyPatientRepository;
import com.dzablotny.legacy.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PatientEndpoint {
    @Autowired
    public LegacyPatientRepository legacyPatientRepository;

    @PayloadRoot(namespace = "http://legacy.com/patients", localPart = "addPatientRequest")
    @ResponsePayload
    public AddPatientResponse addPatientRequest(@RequestPayload AddPatientRequest request) {
        legacyPatientRepository.addPatient(request.getPatient());
        AddPatientResponse response = new AddPatientResponse();
        response.setResponseMessage(String.format(
                "%s added to patients database",
                request.getPatient().getName()
        ));
        return response;
    }

    @PayloadRoot(namespace = "http://legacy.com/patients", localPart = "deletePatientRequest")
    @ResponsePayload
    public DeletePatientResponse deletePatientRequest(@RequestPayload DeletePatientRequest request) {
        legacyPatientRepository.deletePatient(request.getId());
        DeletePatientResponse response = new DeletePatientResponse();
        response.setResponseMessage(String.format(
                "Patient with ID: %d deleted from database",
                request.getId()
        ));
        return response;
    }

    @PayloadRoot(namespace = "http://legacy.com/patients", localPart = "getPatientRequest")
    @ResponsePayload
    public GetPatientResponse getPatientRequest(@RequestPayload GetPatientRequest request) {
        GetPatientResponse response = new GetPatientResponse();
        response.setPatient(legacyPatientRepository.getPatient(request.getId()));
        return response;
    }

    @PayloadRoot(namespace = "http://legacy.com/patients", localPart = "updatePatientRequest")
    @ResponsePayload
    public UpdatePatientResponse updatePatientRequest(@RequestPayload UpdatePatientRequest request) {
        legacyPatientRepository.updatePatient(request.getPatient());
        UpdatePatientResponse response = new UpdatePatientResponse();
        response.setResponseMessage(String.format(
                "Patient with ID: %d updated in database",
                request.getPatient().getId()
        ));
        return response;
    }
}
