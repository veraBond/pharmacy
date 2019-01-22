package com.bandarovich.pharmacy.command;

public enum CommandType {
    START_PAGE,
    SET_LOCALE,
    REGISTRATION,
    LOG_IN,
    LOG_OUT,
    BOOK_MEDICINE,
    COMPLETE_ORDER,
    CLIENT_MEDICINE_LIST,
    CLIENT_PRESCRIPTION_LIST,
    REQUEST_PRESCRIPTION_FOR_EXTENSION,
    DOCTOR_MEDICINE_LIST,
    DOCTOR_PRESCRIPTION_LIST,
    WRITE_PRESCRIPTION,
    COMPLETE_WRITE_PRESCRIPTION,
    EXTEND_PRESCRIPTION,
    COMPLETE_EXTEND_PRESCRIPTION,
    PHARMACIST_MEDICINE_LIST,
    ADD_MEDICINE,
    COMPLETE_ADD_MEDICINE,
    MODIFY_MEDICINE,
    COMPLETE_MODIFY_MEDICINE,
    DELETE_MEDICINE,
    COMPLETE_DELETE_MEDICINE
}
