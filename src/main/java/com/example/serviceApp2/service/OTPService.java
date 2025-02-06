package com.example.serviceApp2.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class OTPService {

    @Autowired
    private JavaMailSender mailSender;

    public String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void sendOTPEmail(String email, String otp) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP for registration is: " + otp);
        mailSender.send(message);
    }
}
