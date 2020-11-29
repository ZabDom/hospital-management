package com.dzablotny.fancy.jsp.service;

import com.dzablotny.legacy.soap.Patient;

import java.util.List;

public interface JSPPatientService {

    void deletePatient(int id);

    List<Patient> getAllPatients();

    Patient getPatientById(int id);

    void updatePatient(Patient patient);
}
