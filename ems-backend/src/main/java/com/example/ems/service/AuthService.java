package com.example.ems.service;

import com.example.ems.dto.JwtUtil;
import com.example.ems.entity.Employee;
import com.example.ems.entity.Role;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public Object register(Employee e) {

        // 1️⃣ Fetch role from DB using role_id
        Role role = roleRepository.findById(e.getRole().getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 2️⃣ Set managed role
        e.setRole(role);

        // 3️⃣ Auto set role name column
        e.setRoleName(role.getRoleName());

        // 4️⃣ Set system values
        e.setCreatedAt(LocalDateTime.now());
        e.setPasswordHash(passwordEncoder.encode(e.getPasswordHash()));

        // 5️⃣ Save employee
        return employeeRepository.save(e);
    }


    public Object login(Map<String, String> data) {
        String email = data.get("email");
        String password = data.get("password");

        // 1️⃣ Find employee by email
        Employee emp = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // 2️⃣ Check password
        if (!passwordEncoder.matches(password, emp.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 3️⃣ Generate JWT token (if you are using JWT)
        String token = jwtUtil.generateToken(emp.getEmail());

        // 4️⃣ Return response
        return Map.of(
                "employeeId", emp.getEmployeeId(),
                "fullName", emp.getFullName(),
                "email", emp.getEmail(),
                "token", token
        );
    }

}
