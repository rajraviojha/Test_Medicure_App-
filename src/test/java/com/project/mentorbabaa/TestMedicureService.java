package com.project.mentorbabaa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMedicureService {

    @Autowired
    MedicureService doctorService;

    @Test
    public void testRegisterDoctor() {
        // Create a dummy doctor object for comparison
        Doctor expectedDoctor = new Doctor("dummyId", "Dummy Doctor", "Dummy Specialty", "0 years");

        // Register a dummy doctor using the service method
        Doctor registeredDoctor = doctorService.registerDummyDoctor();

        // Ensure the registered doctor is not null
        assertNotNull(registeredDoctor, "Registered Doctor should not be null");

        // Assert that the registration ID of the registered doctor matches the expected value
        assertEquals(expectedDoctor.getDoctorRegistrationId(), registeredDoctor.getDoctorRegistrationId(), "Doctor Registration ID should be 'dummyId'");

        // Assert other fields to ensure consistency
        assertEquals(expectedDoctor.getDoctorName(), registeredDoctor.getDoctorName(), "Doctor name should be 'Dummy Doctor'");
        assertEquals(expectedDoctor.getDoctorSpeciality(), registeredDoctor.getDoctorSpeciality(), "Doctor speciality should be 'Dummy Specialty'");
        assertEquals(expectedDoctor.getDoctorExperience(), registeredDoctor.getDoctorExperience(), "Doctor experience should be '0 years'");
    }
}