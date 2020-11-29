package com.dzablotny.fancy.jsp.service;

import com.dzablotny.fancy.memcached.controller.MemcachedPatientController;
import com.dzablotny.fancy.requests.client.SOAPClient;
import com.dzablotny.legacy.repository.LegacyPatientRepository;
import com.dzablotny.legacy.soap.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JSPPatientServiceImpl implements JSPPatientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSPPatientServiceImpl.class);

    @Autowired
    LegacyPatientRepository legacyPatientRepository;

    @Autowired
    MemcachedPatientController memcachedPatientController;

    @Override
    public void deletePatient(int id) {
        legacyPatientRepository.deletePatient(id);
        memcachedPatientController.deletePatientFromCache(id);
        LOGGER.info(String.format(
                "Deleted patient with id = %d from database, sent to morgue.",
                id
        ));
    }

    @Override
    public List<Patient> getAllPatients() {
        return legacyPatientRepository.getPatients();
    }

    @Override
    public Patient getPatientById(int id) {
        return legacyPatientRepository.getPatient(id);
    }

    @Override
    public void updatePatient(Patient patient) {
        SOAPClient.sendSOAPRequest(patient);
    }
}