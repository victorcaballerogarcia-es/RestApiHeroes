package com.payworks.restapi.service;

import java.text.ParseException;

public interface Converter<A, B> {
    B convertToDTO(A a);

    A convertToDO(B b) throws ParseException;
}