package com.bandarovich.pharmacy.command.impl.user;

import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.entity.PharmacyUser;
import com.bandarovich.pharmacy.service.ServiceException;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import com.bandarovich.pharmacy.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RegistrationCommand implements PharmacyCommand {
    private final static Logger logger = LogManager.getLogger();
    private final static String POSITION_ERROR_MESSAGE = "Registration error: unexpected position.";
    private final static String REGISTRATION_ERROR_MESSAGE = "Registration error.";
    private final static String EMPTY_MEDICINE_LIST_MESSAGE = "You have no available medicines.";

    @Override
    public Router execute(HttpServletRequest request)  {
        PharmacyPosition position = PharmacyPosition.valueOf(request.getParameter(JspAttribute.POSITION).toUpperCase());
        String mail = request.getParameter(JspAttribute.MAIL);
        String password = request.getParameter(JspAttribute.PASSWORD);
        PharmacyUser user = new PharmacyUser(position, request.getParameter(JspAttribute.USER_NAME), mail, password);
        Router router = new Router();
        try {
            List<String> errors = UserServiceImpl.INSTANCE.register(user);
            if(errors.isEmpty()){
                request.getSession().setAttribute(JspAttribute.USER_NAME, user.getName());
                request.getSession().setAttribute(JspAttribute.POSITION, user.getPosition());
                request.getSession().setAttribute(JspAttribute.MAIL, user.getMail());
                List<Medicine> clientMedicineList = MedicineServiceImpl.INSTANCE.findClientMedicineList(mail);
                request.getSession().setAttribute( JspAttribute.CLIENT_MEDICINE_LIST, clientMedicineList);
                switch (position) {
                    case CLIENT:
                        request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.CLIENT_PAGE);
                        router.setRedirect(JspPath.CLIENT_PAGE);
                        break;
                    case DOCTOR:
                        List<Medicine> doctorMedicines = MedicineServiceImpl.INSTANCE.findDoctorMedicineList();
                        request.getSession().setAttribute( JspAttribute.DOCTOR_MEDICINE_LIST, doctorMedicines);
                        request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.DOCTOR_PAGE);
                        request.getSession().setAttribute(JspAttribute.IS_DOCTOR, Boolean.TRUE);
                        router.setRedirect(JspPath.DOCTOR_PAGE);
                        break;
                    case PHARMACIST:
                        List<Medicine> medicines = MedicineServiceImpl.INSTANCE.findAllMedicineList();
                        request.getSession().setAttribute( JspAttribute.PHARMACIST_MEDICINE_LIST, medicines);
                        request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.PHARMACIST_PAGE);
                        request.getSession().setAttribute(JspAttribute.IS_PHARMACIST, Boolean.TRUE);
                        router.setRedirect(JspPath.PHARMACIST_PAGE);
                        break;
                    default:
                        logger.warn(POSITION_ERROR_MESSAGE + position);
                        request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, POSITION_ERROR_MESSAGE);
                        request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.START_PAGE);
                        router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
                }
            }
            else{
                request.setAttribute(JspAttribute.USER_NAME, user.getName());
                request.setAttribute(JspAttribute.MAIL, user.getMail());
                request.setAttribute(JspAttribute.POSITION, user.getPosition());
                errors.forEach(error -> {request.setAttribute(error, Boolean.TRUE);});
                router.setForward(JspPath.REGISTRATION_PAGE);
            }
        } catch (ServiceException e){
            logger.error(REGISTRATION_ERROR_MESSAGE, e);
            request.setAttribute(JspAttribute.ERROR_MESSAGE, REGISTRATION_ERROR_MESSAGE);
            request.setAttribute(JspAttribute.START_PAGE, JspPath.START_PAGE);
            request.setAttribute(JspAttribute.ERROR, e);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
