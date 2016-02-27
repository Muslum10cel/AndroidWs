/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muslumyusuf.webserviceforvaccineapp;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import com.muslumyusuf.initialize.*;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author muslumoncel
 */
public class DBOperations {

    private static final SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
    private static final HttpTransportSE httpTransport = new HttpTransportSE(Url.URL);
    private static SoapObject soapObject;

    /**
     * Log-in method
     *
     * @param username username of attempt to log-in
     * @param password password of user
     * @return 87 normal user, 37 doctor, -2 user not available, -1 for
     * exception
     * @throws org.xmlpull.v1.XmlPullParserException
     * @throws java.io.IOException
     */
    public int log_in(String username, String password) throws XmlPullParserException, IOException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.LOG_IN);
        soapObject.addProperty(Tags.USERNAME_TAG, username);
        soapObject.addProperty(Tags.PASSWORD_TAG, password);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.LOG_IN_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Get all babies of currently logged in user
     *
     * @param username username of logged in user
     * @return an JSON object includes baby name and baby id or null if catches
     * exception
     * @throws org.json.JSONException
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public JSONObject getBabies(String username) throws JSONException, IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.GET_BABIES);
        soapObject.addProperty(Tags.USERNAME_TAG, username);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.GET_BABIES_ACTION, soapSerializationEnvelope);
        return new JSONObject(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Gets vaccine completion detail desired baby
     *
     * @param baby_id id of desired baby
     * @return a JSON object includes completion detail of desired baby or null
     * if catches exception
     * @throws org.xmlpull.v1.XmlPullParserException
     * @throws java.io.IOException
     * @throws org.json.JSONException
     */
    public JSONObject getCompletedVaccines(int baby_id) throws XmlPullParserException, IOException, JSONException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.GET_COMPLETED_VACCINES);
        soapObject.addProperty(Tags.BABY_ID_TAG.toLowerCase(), baby_id);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.GET_COMPLETED_VACCINES_ACTION, soapSerializationEnvelope);
        return new JSONObject(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Registration of user
     *
     * @param username a unique username
     * @param fullName name and surname of user
     * @param password password of user
     * @param e_mail an active e-mail address of user
     * @return 2 registered, -1 if catches exception, 1 user available
     * @throws org.xmlpull.v1.XmlPullParserException
     * @throws java.io.IOException
     */
    public int register(String username, String fullName, String password, String e_mail) throws XmlPullParserException, IOException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.REGISTER);
        soapObject.addProperty(Tags.USERNAME_TAG, username);
        soapObject.addProperty(Tags.FULLNAME_TAG, fullName);
        soapObject.addProperty(Tags.PASSWORD_TAG, password);
        soapObject.addProperty(Tags.E_MAIL, e_mail);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.REGISTER_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Adds a baby
     *
     * @param username username of currently logged in system
     * @param baby_name name of added baby
     * @param date_of_birth date of birth added baby
     * @return 1 added, -1 exception
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int addBaby(String username, String baby_name, String date_of_birth) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.ADD_BABY);
        soapObject.addProperty(Tags.USERNAME_TAG, username);
        soapObject.addProperty(Tags.BABY_NAME_TAG.toLowerCase(), baby_name);
        soapObject.addProperty(Tags.DATE_TAG, date_of_birth);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.ADD_BABY_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Updates DABT IPA HIB vaccine of baby
     *
     * @param baby_id id of desired baby
     * @param flag vaccine flag
     * @return 1 updated, -1 exception, -2 wrong flag
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int update_DaBT_Ipa_Hib_Vaccine(int baby_id, int flag) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.UPDATE_DABT_IPA_HIB_STATUS);
        soapObject.addProperty(Tags.BABY_ID_TAG.toLowerCase(), baby_id);
        switch (flag) {
            case 1:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.ONE_FLAG);
                break;
            case 2:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.TWO_FLAG);
                break;
            case 3:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.THREE_FLAG);
                break;
            case 4:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.FOUR_FLAG);
                break;
            case 5:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.FIVE_FLAG);
                break;
            case 6:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.SIX_FLAG);
                break;
            default:
                break;
        }
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.UPDATE_DABT_IPA_HIB_STATUS_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Updates HEPATITIS A vaccine of baby
     *
     * @param baby_id id of desired baby
     * @param flag vaccine flag
     * @return 1 updated, -1 exception, -2 wrong flag
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int update_Hepatit_A(int baby_id, int flag) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.UPDATE_HEPATIT_A_STATUS);
        soapObject.addProperty(Tags.BABY_ID_TAG.toLowerCase(), baby_id);
        switch (flag) {
            case 1:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.ONE_FLAG);
                break;
            case 2:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.TWO_FLAG);
                break;
        }
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.UPDATE_HEPATIT_A_STATUS_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());

    }

    /**
     * Updates HEPATITIS B vaccine of baby
     *
     * @param baby_id id of desired baby
     * @param flag vaccine flag
     * @return 1 updated, -1 exception, -2 wrong flag
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int update_Hepatit_B(int baby_id, int flag) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.UPDATE_HEPATIT_B_STATUS);
        soapObject.addProperty(Tags.BABY_ID_TAG.toLowerCase(), baby_id);
        switch (flag) {
            case 1:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.ONE_FLAG);
                break;
            case 2:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.TWO_FLAG);
                break;
        }
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.UPDATE_HEPATIT_B_STATUS_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Updates KKK vaccine of baby
     *
     * @param baby_id id of desired baby
     * @param flag vaccine flag
     * @return 1 updated, -1 exception, -2 wrong flag
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int update_KKK(int baby_id, int flag) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.UPDATE_KKK_STATUS);
        soapObject.addProperty(Tags.BABY_ID_TAG.toLowerCase(), baby_id);
        switch (flag) {
            case 1:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.ONE_FLAG);
                break;
            case 2:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.TWO_FLAG);
                break;
        }
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.UPDATE_KKK_STATUS_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());

    }

    /**
     * Updates KPA vaccine of baby
     *
     * @param baby_id id of desired baby
     * @param flag vaccine flag
     * @return 1 updated, -1 exception, -2 wrong flag
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int update_KPA(int baby_id, int flag) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.UPDATE_KPA_STATUS);
        soapObject.addProperty(Tags.BABY_ID_TAG.toLowerCase(), baby_id);
        switch (flag) {
            case 1:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.ONE_FLAG);
                break;
            case 2:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.TWO_FLAG);
                break;
            case 3:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.THREE_FLAG);
                break;
            case 4:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.FOUR_FLAG);
                break;
            default:
                break;
        }
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.UPDATE_KPA_STATUS_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Updates OPA vaccine of baby
     *
     * @param baby_id id of desired baby
     * @param flag vaccine flag
     * @return 1 updated, -1 exception, -2 wrong flag
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int update_OPA(int baby_id, int flag) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.UPDATE_OPA_STATUS);
        soapObject.addProperty(Tags.BABY_ID_TAG.toLowerCase(), baby_id);
        switch (flag) {
            case 1:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.ONE_FLAG);
                break;
            case 2:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.TWO_FLAG);
                break;
        }
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.UPDATE_OPA_STATUS_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());

    }

    /**
     * Updates RVA vaccine of baby
     *
     * @param baby_id id of desired baby
     * @param flag vaccine flag
     * @return 1 updated, -1 exception, -2 wrong flag
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int update_RVA(int baby_id, int flag) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.UPDATE_RVA_STATUS);
        soapObject.addProperty(Tags.BABY_ID_TAG.toLowerCase(), baby_id);
        switch (flag) {
            case 1:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.ONE_FLAG);
                break;
            case 2:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.TWO_FLAG);
                break;
            case 3:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.THREE_FLAG);
                break;
            default:
                break;
        }
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.UPDATE_RVA_STATUS_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());

    }

    /**
     * Updates vaccine of baby such as VARICELLA, BCG
     *
     * @param baby_id id of desired baby
     * @param flag vaccine flag
     * @return 1 updated, -1 exception, -2 wrong flag
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int update_Vaccines(int baby_id, int flag) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.UPDATE_VACCINES_STATUS);
        soapObject.addProperty(Tags.BABY_ID_TAG.toLowerCase(), baby_id);
        switch (flag) {
            case 1:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.BCG_FLAG);
                break;
            case 2:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.DaBT_IPA_FLAG);
                break;
            case 3:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.VARICELLA_FLAG);
                break;
            case 4:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.KMA4_FLAG);
                break;
            case 5:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.HPA_FLAG);
                break;
            case 6:
                soapObject.addProperty(Tags.VACCINE_FLAG, Flags.INFLUENZA_FLAG);
                break;
            default:
                break;
        }
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.UPDATE_VACCINES_STATUS_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Adds a comment about selected vaccine
     *
     * @param username username of currently logged in system
     * @param vaccine_name vaccine name for writing comment about it
     * @param comment written comment
     * @return 1 updated, 0 not updated, -1 exception
     * @throws org.xmlpull.v1.XmlPullParserException
     * @throws java.io.IOException
     */
    public int addComment(String username, String vaccine_name, String comment) throws XmlPullParserException, IOException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.ADD_COMMENT);
        soapObject.addProperty(Tags.USERNAME_TAG, username);
        soapObject.addProperty(Tags.VACCINE_NAME_TAG, vaccine_name);
        soapObject.addProperty(Tags.COMMENT_TAG, comment);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.ADD_COMMENT_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     *
     * @param username username of attempt to change password
     * @param newPassword new password
     * @return 1 updated, -1 exception, -2 user not available
     * @throws org.xmlpull.v1.XmlPullParserException
     * @throws java.io.IOException
     */
    public int forgottenpassword(String username, String newPassword) throws XmlPullParserException, IOException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.FORGOTTEN_PASSWORD);
        soapObject.addProperty(Tags.USERNAME_TAG, username);
        soapObject.addProperty(Tags.NEW_PASSWORD, newPassword);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.FORGOTTEN_PASSWORD_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Gets comment about selected vaccine name
     *
     * @param vaccine_name vaccine name that written comment
     * @param begin first index of comments
     * @param end last index of comments
     * @return a JSON object includes username, vaccine name, comment and
     * comment date or null if catches exception
     * @throws org.json.JSONException
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public JSONObject getComments(String vaccine_name, int begin, int end) throws JSONException, IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.GET_COMMENTS);
        soapObject.addProperty(Tags.VACCINE_NAME_TAG, vaccine_name);
        soapObject.addProperty(Tags.BEGINNING_TAG, begin);
        soapObject.addProperty(Tags.END_TAG, end);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.GET_COMMENTS_ACTION, soapSerializationEnvelope);
        return new JSONObject(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Gets all vaccine name that recorded to database
     *
     * @return a JSON object includes vaccine names or null if catches exception
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     * @throws org.json.JSONException
     */
    public JSONObject getVaccineNames() throws IOException, XmlPullParserException, JSONException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.ALL_VACCINE_NAMES);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.GET_VACCINE_NAMES_ACTION, soapSerializationEnvelope);
        return new JSONObject(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Gets vaccination detail of desired baby
     *
     * @param baby_id id of baby
     * @return a JSON object includes vaccine names and date of vaccine or null
     * if catches exception
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     * @throws org.json.JSONException
     */
    public JSONObject getBabyVaccineDetails(int baby_id) throws IOException, XmlPullParserException, JSONException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.BABY_VACCINE_DETAILS);
        soapObject.addProperty(Tags.BABY_ID_TAG.toLowerCase(), baby_id);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.BABY_VACCINE_DETAILS_ACTION, soapSerializationEnvelope);
        return new JSONObject(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Sends verification code to the user before changing his/her password
     *
     * @param e_mail E-mail address that recorded by user during registration
     * @return 10 code sent, -2 if catches exception
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int sendVerificationCode(String e_mail) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.SEND_VERIFICATION_CODE);
        soapObject.addProperty(Tags.E_MAIL, e_mail);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.SEND_VERIFICATIN_CODE_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     * Validates verification code sent by system
     *
     * @param e_mail E-mail address that recorded by user during registration
     * @param code code sent by system to the user's e-mail
     * @return 1 validated, 0 not validated, -2 exception
     * @throws org.xmlpull.v1.XmlPullParserException
     * @throws java.io.IOException
     */
    public int validateVerificationCode(String e_mail, String code) throws XmlPullParserException, IOException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.VALIDATE_VERIFICATION_CODE);
        soapObject.addProperty(Tags.E_MAIL, e_mail);
        soapObject.addProperty(Tags.CODE, code);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.VALIDATE_VERIFICATIN_CODE_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }

    /**
     *
     * Uploads image to ftp and insert image URL to database
     *
     * @param username username of currently logged-in
     * @param fileName image file selected by user
     * @param imageBytes bytes of selected file
     * @return -2 if exception occur during upload ftp, -1 if exception occurs
     * during insert database 1 successfully updated, 0 not updated
     * @throws java.io.IOException
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public int uploadImage(String username, String fileName, byte[] imageBytes) throws IOException, XmlPullParserException {
        soapObject = new SoapObject(NameSpace.NAMESPACE, Methods.UPLOAD_IMAGE);
        soapObject.addProperty(Tags.USERNAME_TAG, username);
        soapObject.addProperty(Tags.FILENAME, fileName);
        soapObject.addProperty(Tags.IMAGE_BYTES, imageBytes);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransport.call(Actions.UPLOAD_IMAGE_ACTION, soapSerializationEnvelope);
        return Integer.parseInt(soapSerializationEnvelope.getResponse().toString());
    }
}
