package com.bandarovich.pharmacy.command.impl.medicine;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DoctorMedicineListCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) {
//        String nextPage;
//        try{
//            List<Medicine> doctorMedicines = MedicineServiceImpl.INSTANCE.findDoctorMedicines();
//            request.setAttribute(JspAttribute.MEDICINE_LIST, doctorMedicines);
//            nextPage = JspPath.DOCTOR_MEDICINE_PAGE;
//        } catch (ServiceException e){
//            logger.error(e);
//            request.setAttribute(JspAttribute.ERROR_MESSAGE, "Error while loading medicine list.");
//            nextPage = JspPath.DOCTOR_PAGE;
//        }
        return null;
    }
}
