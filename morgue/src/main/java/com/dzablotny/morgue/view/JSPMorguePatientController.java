package com.dzablotny.morgue.view;

import com.dzablotny.legacy.soap.Patient;
import com.dzablotny.morgue.jms.receiver.JMSReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("/morgue")
public class JSPMorguePatientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSPMorguePatientController.class);
    private final List<Patient> patients = new ArrayList<>();

    @Autowired
    JMSReceiver jmsReceiver;

    @GetMapping({"/morgue"})
    public String updateMorguePage(Model model) {
        Patient patient = jmsReceiver.receivePatient();
        LOGGER.info(String.format("Received patient with id = %d.", patient.getId()));
        patients.add(patient);
        model.addAttribute("patient", patients);
        return "index";
    }

    @GetMapping({"/"})
    public String showMorguePage(Model model) {
        model.addAttribute("patient", patients);
        return "index";
    }
}