package com.github.marceloleite2604.spring.webflux.model.httpmessageconverter;

import com.github.marceloleite2604.spring.webflux.model.Person;
import com.github.marceloleite2604.spring.webflux.model.mapper.PersonToJsonStringMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonXndJsonHttpMessageConverter extends AbstractHttpMessageConverter<List<Person>> {

    private final PersonToJsonStringMapper personToJsonStringMapper;

    @Override
    protected boolean supports(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    @Override
    protected void writeInternal(List<Person> people, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        try (var bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputMessage.getBody()))) {
            people.stream()
                    .map(personToJsonStringMapper::mapTo)
                    .map(jsonString -> jsonString += Strings.LINE_SEPARATOR)
                    .forEach(newLinedJsonString -> writeHandlingException(bufferedWriter, newLinedJsonString));
        }
    }

    @Override
    protected List<Person> readInternal(Class<? extends List<Person>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        List<String> bodyLines = mapInputMessageBodyIntoStringList(inputMessage);
        return personToJsonStringMapper.mapAllFrom(bodyLines);
    }

    private void writeHandlingException(BufferedWriter bufferedWriter, String content) {
        try {
            bufferedWriter.write(content);
        } catch (IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    private List<String> mapInputMessageBodyIntoStringList(HttpInputMessage inputMessage) throws IOException {
        List<String> bodyLines = new LinkedList<>();
        String bodyLine;
        try (var isr = new BufferedReader(new InputStreamReader(inputMessage.getBody()))) {
            while ((bodyLine = isr.readLine()) != null) {
                bodyLines.add(bodyLine);
            }
        }
        return bodyLines;
    }
}
