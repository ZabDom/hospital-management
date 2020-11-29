package com.dzablotny.fancy.memcached.controller;

import com.dzablotny.fancy.memcached.config.MemcachedConfig;
import com.dzablotny.fancy.requests.client.SOAPClient;
import com.dzablotny.legacy.repository.LegacyPatientRepository;
import com.dzablotny.legacy.soap.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

@RestController
public class MemcachedPatientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedPatientController.class);
    private static final int EXPIRATION = 10800;
    private final MemcachedConfig memcachedPool = new MemcachedConfig(5,
            "memcached:11211");

    @Autowired
    public LegacyPatientRepository legacyPatientRepository;

    public void deletePatientFromCache(int id) {
        memcachedPool.getCache().delete(String.valueOf(id));
        LOGGER.info(String.format(
                "Deleted patient with id = %d from cache.",
                id
        ));
    }

    @GetMapping(value = "/patients/{id}")
    public @ResponseBody Patient getPatientById(@PathVariable int id) throws JAXBException,
            SOAPException {
        Patient patient = (Patient) memcachedPool.getCache().get(String.valueOf(id));
        if (patient == null) {
            patient = SOAPClient.responseToObject(SOAPClient.sendSOAPRequest(id));
            memcachedPool.getCache().add(String.valueOf(id), EXPIRATION, patient);
            LOGGER.info(String.format(
                    "Patient with id = %d added to cache.",
                    id
            ));
        }
        return patient;
    }

    @PatchMapping(value = "/patient/{id}/{dateOfLeave}/{currentStatus}")
    public void updatePatient(@PathVariable int id, @PathVariable String dateOfLeave,
                              @PathVariable int currentStatus) throws JAXBException, SOAPException {
        Patient patient = (Patient) memcachedPool.getCache().get(String.valueOf(id));
        if (patient == null) {
            Patient patientToUpdate = SOAPClient.responseToObject(SOAPClient.sendSOAPRequest(id));
            patientToUpdate.setDateOfLeave(dateOfLeave);
            patientToUpdate.setCurrentStatus(currentStatus);
            SOAPClient.sendSOAPRequest(patientToUpdate);
            LOGGER.info(String.format(
                    "Patient with id = %d updated in database.",
                    id
            ));
            memcachedPool.getCache().add(String.valueOf(id), EXPIRATION, patientToUpdate);
        } else {
            Patient patientToUpdate = SOAPClient.responseToObject(SOAPClient.sendSOAPRequest(id));
            patientToUpdate.setDateOfLeave(dateOfLeave);
            patientToUpdate.setCurrentStatus(currentStatus);
            SOAPClient.sendSOAPRequest(patientToUpdate);
            LOGGER.info(String.format(
                    "%s, %d",
                    patientToUpdate.getDateOfLeave(), patientToUpdate.getCurrentStatus()
            ));
            LOGGER.info(String.format(
                    "Patient with id = %d updated in database.",
                    id
            ));
            memcachedPool.getCache().set(String.valueOf(id), EXPIRATION, patientToUpdate);
        }
        LOGGER.info(String.format(
                "Patient with id = %d updated in cache.",
                id
        ));
    }
}
