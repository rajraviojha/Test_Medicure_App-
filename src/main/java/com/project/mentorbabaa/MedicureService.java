package com.project.mentorbabaa;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicureService {

    @Autowired
    MedicureRepository doctorRepository;

    public Doctor registerDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor createDoctor() {
        Doctor doctor = new Doctor("MP1110", "Shubham", "Neurologist", "15 Years");
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctorDetails(String doctorRegistrationId) {
        return doctorRepository.findById(doctorRegistrationId).orElse(null);
    }

    public List<Doctor> searchDoctor(String doctorName) {
        return doctorRepository.findByDoctorNameIgnoreCase(doctorName);
    }

    public Doctor updateDoctor(String doctorRegNo, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(doctorRegNo).orElse(null);
        if (existingDoctor != null) {
            existingDoctor.setDoctorName(doctor.getDoctorName());
            existingDoctor.setDoctorSpeciality(doctor.getDoctorSpeciality());
            existingDoctor.setDoctorExperience(doctor.getDoctorExperience());
            return doctorRepository.save(existingDoctor);
        }
        return null;
    }

    public void deleteDoctor(String doctorRegNo) {
        doctorRepository.deleteById(doctorRegNo);
    }

    public String sayHello() {
        return "Hello from Doctor Shubham";
    }

    public Doctor registerDummyDoctor() {
        return new Doctor("dummyId", "Dummy Doctor", "Dummy Specialty", "0 years");
    }
}