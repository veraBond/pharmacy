/*
 * 
 */
package com.bandarovich.pharmacy.command;

/**
 * The Enum CommandType.
 */
public enum CommandType {
    
    /** The start page. */
    START_PAGE,
    
    /** The set locale. */
    SET_LOCALE,
    
    /** The registration. */
    REGISTRATION,
    
    /** The log in. */
    LOG_IN,
    
    /** The log out. */
    LOG_OUT,
    
    /** The book medicine. */
    BOOK_MEDICINE,
    
    /** The complete order. */
    COMPLETE_ORDER,
    
    /** The client medicine list. */
    CLIENT_MEDICINE_LIST,
    
    /** The client prescription list. */
    CLIENT_PRESCRIPTION_LIST,
    
    /** The request prescription for extension. */
    REQUEST_PRESCRIPTION_FOR_EXTENSION,
    
    /** The doctor medicine list. */
    DOCTOR_MEDICINE_LIST,
    
    /** The doctor prescription list. */
    DOCTOR_PRESCRIPTION_LIST,
    
    /** The write prescription. */
    WRITE_PRESCRIPTION,
    
    /** The complete write prescription. */
    COMPLETE_WRITE_PRESCRIPTION,
    
    /** The extend prescription. */
    EXTEND_PRESCRIPTION,
    
    /** The complete extend prescription. */
    COMPLETE_EXTEND_PRESCRIPTION,
    
    /** The pharmacist medicine list. */
    PHARMACIST_MEDICINE_LIST,
    
    /** The add medicine. */
    ADD_MEDICINE,
    
    /** The complete add medicine. */
    COMPLETE_ADD_MEDICINE,
    
    /** The modify medicine. */
    MODIFY_MEDICINE,
    
    /** The complete modify medicine. */
    COMPLETE_MODIFY_MEDICINE,
    
    /** The delete medicine. */
    DELETE_MEDICINE,
    
    /** The complete delete medicine. */
    COMPLETE_DELETE_MEDICINE
}
