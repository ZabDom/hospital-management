package com.dzablotny.legacy.repository;

import com.dzablotny.legacy.soap.Patient;

import java.util.List;

public interface LegacyPatientRepository {
    void addPatient(Patient patient);

    void deletePatient(int id);

    Patient getPatient(int id);

    List<Patient> getPatients();

    void updatePatient(Patient patient);

    void updatePatient(int id, String dateOfLeave, int currentStatus);
}