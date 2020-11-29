package com.dzablotny.legacy.repository;

import com.dzablotny.legacy.drivermanager.DriverManagerConfiguration;
import com.dzablotny.legacy.soap.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LegacyPatientRepositoryImpl implements LegacyPatientRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(LegacyPatientRepositoryImpl.class);
    private static final String URL = "jdbc:mysql://db:3306/db?autoreconnect=true";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";
    private static final String SQL = "SELECT * FROM patients";
    private static final String ADD_SQL = "INSERT INTO patients VALUES "
            + "(%d, \'%s\', \'%s\', \'%s\', \'%s\', %d)";
    private static final String DELETE_SQL = "DELETE FROM patients WHERE id = %d";
    private static final String GET_SQL = "SELECT * FROM patients WHERE id = %d";
    private static final String UPDATE_SQL = "UPDATE patients SET name = \'%s\', date_of_birth = "
            + "\'%s\',  date_of_arrival = \'%s\', date_of_leave = \'%s\', current_status = %d "
            + "WHERE id = %d";

    private DriverManagerConfiguration driverManager = DriverManagerConfiguration.create(URL,
            USERNAME, PASSWORD);

    public LegacyPatientRepositoryImpl() throws SQLException {
    }

    @Override
    public void addPatient(Patient patient) {
        try {
            Statement statement = driverManager.getStatement();

            statement.executeUpdate(String.format(
                    ADD_SQL,
                    patient.getId(),
                    patient.getName(),
                    patient.getDateOfBirth(),
                    patient.getDateOfArrival(),
                    patient.getDateOfLeave(),
                    patient.getCurrentStatus()
            ));

            driverManager.releaseStatement(statement);

            LOGGER.info(String.format(
                    "Added new patient with name = %s",
                    patient.getName()
            ));
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Unable to send query to database, %s",
                    e
            ));
        }
    }

    @Override
    public void deletePatient(int patientId) {
        try {
            Statement statement = driverManager.getStatement();

            statement.executeUpdate(String.format(
                    DELETE_SQL,
                    patientId
            ));

            driverManager.releaseStatement(statement);

            LOGGER.info(String.format(
                    "Deleted patient with id = %d",
                    patientId
            ));
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Unable to send query to database, %s",
                    e
            ));
        }
    }

    @Override
    public Patient getPatient(int patientId) {
        try {
            Statement statement = driverManager.getStatement();

            ResultSet result = statement.executeQuery(String.format(
                    GET_SQL,
                    patientId
            ));

            driverManager.releaseStatement(statement);

            Patient patient = new Patient();
            while (result.next()) {
                patient.setId(result.getInt("id"));
                patient.setName(result.getString("name"));
                patient.setDateOfBirth(result.getString("date_of_birth"));
                patient.setDateOfArrival(result.getString("date_of_arrival"));
                patient.setDateOfLeave(result.getString("date_of_leave"));
                patient.setCurrentStatus(result.getInt("current_status"));
            }

            LOGGER.info(String.format(
                    "Returned patient with id = %d",
                    patientId
            ));
            return patient;
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Unable to send query to database, %s",
                    e
            ));
            return null;
        }
    }

    @Override
    public List<Patient> getPatients() {
        List<Patient> patientList = new ArrayList<>();
        try {
            Statement statement = driverManager.getStatement();

            ResultSet result = statement.executeQuery(SQL);

            driverManager.releaseStatement(statement);

            while (result.next()) {
                Patient patient = new Patient();
                patient.setId(result.getInt("id"));
                patient.setName(result.getString("name"));
                patient.setDateOfBirth(result.getString("date_of_birth"));
                patient.setDateOfArrival(result.getString("date_of_arrival"));
                patient.setDateOfLeave(result.getString("date_of_leave"));
                patient.setCurrentStatus(result.getInt("current_status"));

                patientList.add(patient);
            }
            return patientList;
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Unable to send query to database, %s",
                    e
            ));
            return patientList;
        }
    }

    @Override
    public void updatePatient(Patient patient) {
        updateQuery(patient);
    }

    @Override
    public void updatePatient(int id, String dateOfLeave, int currentStatus) {
        Patient patient = getPatient(id);
        updateQuery(patient);
    }

    private void updateQuery(Patient patient) {
        try {
            Statement statement = driverManager.getStatement();

            statement.executeUpdate(String.format(
                    UPDATE_SQL,
                    patient.getName(),
                    patient.getDateOfBirth(),
                    patient.getDateOfArrival(),
                    patient.getDateOfLeave(),
                    patient.getCurrentStatus(),
                    patient.getId()
            ));

            driverManager.releaseStatement(statement);

            LOGGER.info(String.format(
                    "Updated patient with id = %d",
                    patient.getId()
            ));
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Unable to send query to database, %s",
                    e
            ));
        }
    }
}
