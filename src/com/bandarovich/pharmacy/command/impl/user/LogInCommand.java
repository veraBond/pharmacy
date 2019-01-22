package com.bandarovich.pharmacy.command.impl.user;

import com.bandarovich.pharmacy.command.JspAttribute;
import com.bandarovich.pharmacy.command.JspPath;
import com.bandarovich.pharmacy.command.PharmacyCommand;
import com.bandarovich.pharmacy.command.Router;
import com.bandarovich.pharmacy.entity.Medicine;
import com.bandarovich.pharmacy.entity.PharmacyPosition;
import com.bandarovich.pharmacy.entity.PharmacyUser;
import com.bandarovich.pharmacy.service.impl.MedicineServiceImpl;
import com.bandarovich.pharmacy.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * The Class LogInCommand.
 */
public class LogInCommand implements PharmacyCommand{
    
    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger();
    
    /** The Constant POSITION_ERROR_MESSAGE. */
    private static final String POSITION_ERROR_MESSAGE = "Log in error: unexpected position.";
    
    /** The Constant LOG_IN_ERROR_MESSAGE. */
    private static final String LOG_IN_ERROR_MESSAGE = "Log in error.";

    /* (non-Javadoc)
     * @see com.bandarovich.pharmacy.command.PharmacyCommand#execute(HttpServletRequest)
     */
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
                List<Medicine> clientMedicineList = MedicineServiceImpl.INSTANCE.findClientMedicineList(mail);
                request.getSession().setAttribute(JspAttribute.CLIENT_MEDICINE_LIST, clientMedicineList);
                request.getSession().setAttribute( JspAttribute.TOTAL_COST, 0.0);
                switch (position) {
                    case CLIENT:
                        request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.CLIENT_PAGE);
                        router.setRedirect(JspPath.CLIENT_PAGE);
                        break;
                    case DOCTOR:
                        request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.DOCTOR_PAGE);
                        List<Medicine> doctorMedicines = MedicineServiceImpl.INSTANCE.findDoctorMedicineList();
                        request.getSession().setAttribute( JspAttribute.DOCTOR_MEDICINE_LIST, doctorMedicines);
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
            } else {
                request.setAttribute(JspAttribute.LOG_IN_ERROR, Boolean.TRUE);
                router.setForward(JspPath.LOGIN_PAGE);
            }
        } catch (Exception e){
            logger.error(LOG_IN_ERROR_MESSAGE, e);
            request.getSession().setAttribute(JspAttribute.ERROR_MESSAGE, LOG_IN_ERROR_MESSAGE + e);
            request.getSession().setAttribute(JspAttribute.START_PAGE, JspPath.START_PAGE);
            router.setRedirect(JspPath.COMMAND_ERROR_PAGE);
        }
        return router;
    }
}
