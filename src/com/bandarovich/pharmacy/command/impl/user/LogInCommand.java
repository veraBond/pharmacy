package com.bandarovich.pharmacy.command.impl.user;

import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.PharmacyCommand;
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
import java.util.Optional;

public class LogInCommand implements PharmacyCommand{
    private final static Logger logger = LogManager.getLogger();
    private final static String POSITION_ERROR_MESSAGE = "Log in error: unexpected position.";
    private final static String LOG_IN_ERROR_MESSAGE = "Log in error.";
    private final static String INPUT_ERROR_MESSAGE = "Incorrect e-mail address or password.";

    @Override
    public Router execute(HttpServletRequest request) {
        String mail = request.getParameter(JspAttribute.MAIL);
        String password = request.getParameter(JspAttribute.PASSWORD);
        Router router = new Router();
        try{
            Optional<PharmacyUser> user = UserServiceImpl.INSTANCE.findUser(mail, password);
            if(user.isPresent()){
                PharmacyPosition position = user.get().getPosition();
                request.getSession().setAttribute(JspAttribute.USER_NAME, user.get().getName());
                request.getSession().setAttribute(JspAttribute.POSITION, position);
                request.getSession().setAttribute(JspAttribute.MAIL, mail);
                switch (position) {
                    case CLIENT:
                        List<Medicine> clientMedicines = MedicineServiceImpl.INSTANCE.findClientMedicineList(mail);
                        clientMedicines.addAll(MedicineServiceImpl.INSTANCE.findAllClientAvailableMedicineList());
                        request.getSession().setAttribute(JspAttribute.MEDICINE_LIST, clientMedicines);
                        request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.CLIENT_PAGE);
                        router.setRedirect(JspPath.CLIENT_PAGE);
                        break;
                    case DOCTOR:
                        request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.DOCTOR_PAGE);
                        List<Medicine> doctorMedicines = MedicineServiceImpl.INSTANCE.findDoctorMedicineList();
                        request.getSession().setAttribute(JspAttribute.MEDICINE_LIST, doctorMedicines);
                        router.setRedirect(JspPath.DOCTOR_PAGE);
                        break;
                    case PHARMACIST:
                        List<Medicine> medicines = MedicineServiceImpl.INSTANCE.findAllMedicineList();
                        request.getSession().setAttribute(JspAttribute.MEDICINE_LIST, medicines);
                        request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.PHARMACIST_PAGE);
                        router.setRedirect(JspPath.PHARMACIST_PAGE);
                        break;
                    default:
                        logger.warn(POSITION_ERROR_MESSAGE + position);
                        request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, POSITION_ERROR_MESSAGE);
                        request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.START_PAGE);
                        router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
                }
            } else {
                request.setAttribute(JspAttribute.INPUT_ERROR_MESSAGE, INPUT_ERROR_MESSAGE);
                router.setForward(JspPath.LOGIN_PAGE);
            }
        } catch (ServiceException e){
            logger.error(LOG_IN_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, LOG_IN_ERROR_MESSAGE + e);
            request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.START_PAGE);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
