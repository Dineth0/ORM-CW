package lk.ijse.gdse.ormcw.bo;

import lk.ijse.gdse.ormcw.bo.custom.impl.*;


public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        PATIENT, PAYMENT,THERAPIST,THERAPYOROGRAM,THERAPY_SESSION,USER,PATIENT_REGISTRATION
    }
    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){

            case USER:
                return (SuperBO) new UserBOImpl();

            case PATIENT:
                return (SuperBO) new PatientBOImpl();

            case THERAPIST:
                return new TherapistBOImpl();

           case THERAPY_SESSION:
                return (SuperBO) new TherapySessionBOImpl();

           case THERAPYOROGRAM:
               return new TherapyProgramBOImpl();

            case PATIENT_REGISTRATION:
                return (SuperBO) new PatientRegistrationBOImpl();

             case PAYMENT:
                 return (SuperBO) new PaymentBOImpl();


            default:
                return null;
        }
    }

}
