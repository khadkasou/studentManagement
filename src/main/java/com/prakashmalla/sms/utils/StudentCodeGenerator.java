package com.prakashmalla.sms.utils;


import com.prakashmalla.sms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class StudentCodeGenerator {

    private final StudentRepository studentRepository;

    public String generateStudentCode(String shortName) {
        List<String> studentCodeList = studentRepository.getAllStudentCode();

        if (studentCodeList == null || studentCodeList.isEmpty()) {
            return shortName + "001";
        }
        Pattern pattern = Pattern.compile(shortName + "(\\d+)");
        return studentCodeList.stream()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1))
                .mapToInt(Integer::parseInt)
                .max()
                .stream()
                .mapToObj(max -> String.format("%s%03d", shortName, max + 1))
                .findFirst()
                .orElse(shortName + "001");


    }
}

