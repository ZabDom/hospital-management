package com.dzablotny.fancy.jsp.controller;

import com.dzablotny.fancy.jms.producer.JMSProducer;
import com.dzablotny.fancy.jsp.service.JSPPatientService;
import com.dzablotny.legacy.soap.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("/fancy")
public class JSPFancyPatientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSPFancyPatientController.class);

    @Autowired
    JSPPatientService jspPatientService;

    @Autowired
    JMSProducer jmsProducer;

    @GetMapping({"/fancy"})
    public String viewPatientsPage(Model model) {
        List<Patient> patients = jspPatientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "index";
    }

    @GetMapping({"/update-patient"})
    public String viewUpdatePage(@RequestParam int id, Model model) {
        model.addAttribute("patient", jspPatientService.getPatientById(id));
        return "update";
    }

    @PostMapping({"/update-patient"})
    public String updatePatient(Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        jspPatientService.updatePatient(patient);
        return "redirect:/fancy";
    }

    @GetMapping({"/send-to-morgue"})
    public String viewSendPage(@RequestParam int id, Model model) {
        model.addAttribute("patient", id);
        return "send";
    }

    @PostMapping({"/send-to-morgue"})
    public String sendToMorgue(@RequestParam int id) {
        Patient patient = jspPatientService.getPatientById(id);
        LOGGER.info(String.valueOf(patient.getCurrentStatus()));
        if (patient.getCurrentStatus() != 1) {
            return "error-fancy";
        } else {
            jmsProducer.sendPatient(patient);
            jspPatientService.deletePatient(patient.getId());
            LOGGER.info(String.format("Patient with id = %d sent to morgue.", patient.getId()));
            return "redirect:/fancy";
        }
    }
}
