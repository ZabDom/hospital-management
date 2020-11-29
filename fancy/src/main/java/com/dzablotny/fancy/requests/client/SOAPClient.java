package com.dzablotny.fancy.requests.client;

import com.dzablotny.fancy.requests.configuration.SOAPClientConfiguration;
import com.dzablotny.legacy.soap.GetPatientResponse;
import com.dzablotny.legacy.soap.ObjectFactory;
import com.dzablotny.legacy.soap.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.*;

public class SOAPClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SOAPClient.class);
    private static final String MY_NAMESPACE = "patient";
    private static final String MY_NAMESPACE_URI = "http://legacy.com/patients";
    private static final String URL = "http://legacy:8080/soap";
    private static SOAPClientConfiguration soapConfiguration;

    static {
        try {
            soapConfiguration = SOAPClientConfiguration.create();
        } catch (SOAPException e) {
            LOGGER.error(String.format(
                    "Unable to create SOAP connction pool, %s",
                    e
            ));
        }
    }

    private static SOAPMessage createGetSOAPRequest(int patientId) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(MY_NAMESPACE, MY_NAMESPACE_URI);
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("getPatientRequest",
                MY_NAMESPACE);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("id", MY_NAMESPACE);
        soapBodyElem1.addTextNode(String.valueOf(patientId));
        soapMessage.saveChanges();
        return soapMessage;
    }

    private static SOAPMessage createUpdateSOAPRequest(Patient patient) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(MY_NAMESPACE, MY_NAMESPACE_URI);
        SOAPBody soapBody = envelope.getBody();

        SOAPElement updatePatientRequestElement = soapBody.addChildElement(
                "updatePatientRequest",
                MY_NAMESPACE
        );
        SOAPElement patientElement = updatePatientRequestElement.addChildElement(
                MY_NAMESPACE,
                MY_NAMESPACE
        );

        SOAPElement soapElement1 = patientElement.addChildElement(
                "id", MY_NAMESPACE);
        soapElement1.addTextNode(String.valueOf(patient.getId()));

        SOAPElement soapElement2 = patientElement.addChildElement(
                "name", MY_NAMESPACE);
        soapElement2.addTextNode(String.valueOf(patient.getName()));

        SOAPElement soapElement3 = patientElement.addChildElement(
                "dateOfBirth", MY_NAMESPACE);
        soapElement3.addTextNode(String.valueOf(patient.getDateOfBirth()));

        SOAPElement soapElement4 = patientElement.addChildElement(
                "dateOfArrival", MY_NAMESPACE);
        soapElement4.addTextNode(String.valueOf(patient.getDateOfArrival()));

        SOAPElement soapElement5 = patientElement.addChildElement(
                "dateOfLeave", MY_NAMESPACE);
        soapElement5.addTextNode(String.valueOf(patient.getDateOfLeave()));

        SOAPElement soapElement6 = patientElement.addChildElement(
                "currentStatus", MY_NAMESPACE);
        soapElement6.addTextNode(String.valueOf(patient.getCurrentStatus()));

        soapMessage.saveChanges();
        return soapMessage;
    }

    public static SOAPMessage sendSOAPRequest(int patientId) {
        try {
            SOAPConnection soapConnection = soapConfiguration.getConnection();
            SOAPMessage soapResponse = soapConnection.call(createGetSOAPRequest(patientId), URL);
            LOGGER.info(String.format(
                    "Returned patient with id = %d via SOAP request.",
                    patientId
            ));
            soapConfiguration.releaseConnection(soapConnection);
            return soapResponse;
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Unable to send SOAP request, %s",
                    e
            ));
            return null;
        }
    }

    public static SOAPMessage sendSOAPRequest(Patient patient) {
        SOAPConnection soapConnection = null;

        try {
            soapConnection = soapConfiguration.getConnection();

            SOAPMessage soapResponse = soapConnection.call(createUpdateSOAPRequest(patient), URL);
            LOGGER.info(String.format(
                    "Patient with id = %d updated via SOAP request",
                    patient.getId()
            ));

            soapConfiguration.releaseConnection(soapConnection);
            return soapResponse;
        } catch (Exception e) {
            LOGGER.error(
                    "Unable to send SOAP request",
                    e
            );
        }
        return null;
    }

    public static Patient responseToObject(SOAPMessage soapMessage)
            throws JAXBException, SOAPException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Patient.class, ObjectFactory.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        GetPatientResponse response = (GetPatientResponse) jaxbUnmarshaller.
                unmarshal(soapMessage.getSOAPBody().extractContentAsDocument());
        return response.getPatient();
    }
}
