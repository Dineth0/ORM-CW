package lk.ijse.gdse.ormcw.dao;

import lk.ijse.gdse.ormcw.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
       PATIENT, PAYMENT,THERAPIST,THERAPYOROGRAM,THERAPY_SESSION,USER,PATIENT_REGISTRATION,QUERY
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case USER:
                return (SuperDAO) new UserDAOImpl();
            case PATIENT:
                return (SuperDAO) new PatientDAOImpl();
            case THERAPIST:
                return new TherapistDAOImpl();
//            case THERAPYSESSION:
//                return (SuperDAO) new TherapySessionDAOImpl();
            case THERAPY_SESSION:
                return new TherapySessionDAOImpl();

            case THERAPYOROGRAM:
                return new TherapyProgramDAOImpl();

            case PATIENT_REGISTRATION:
                return (SuperDAO) new PatientRegistrationDAOImpl();

            case PAYMENT:
                return (SuperDAO) new PaymentDAOImpl();

            case QUERY:
                return (SuperDAO) new QueryDAOImpl();

            default:
                return null;
        }
    }
}

