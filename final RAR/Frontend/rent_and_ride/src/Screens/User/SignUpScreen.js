/* eslint-disable react-hooks/exhaustive-deps */
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
// import { createLogger } from "redux-logger";
import { signup } from "../../Actions/UserActions";
import Header from "../../Components/Header";
import { USER_SIGNUP_RESET } from "../../Constants/UserConstants";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from 'axios';

const SignUpScreen = (props) => {
    const [name,setName] = useState("");
    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");
    const [mobile_no,setMobile_no] = useState("");
    const [aadhar_card,setAadhar_card] = useState("");
    const [license_no,setLicense_no] = useState("");
    const [dob,setDob] = useState("");
    const [role,setRole] = useState("USER");

    const [nameError,setNameError] = useState("");
    const [emailError,setEmailError] = useState("")
    const [passwordError,setPasswordError] = useState("")
    const [mobile_noError,setMobile_noError] = useState("");
    const [aadhar_cardError,setAadhar_cardError] = useState("");
    const [license_noError,setLicense_noError] = useState("");
    const [dobError,setDobError] = useState("");
    const [roleError,setRoleError] = useState("")

    const {loading,response,error} = useSelector(store => store.userSignup)
    const userSignin = useSelector(store => store.userSignin)
    const dispatch = useDispatch()
    const navigate = useNavigate()
const valid=/^[a-zA-Z\s]*$/;

 
 var birthdayDate = new Date(dob);
 var dtCurrent = new Date();
    const onSignUp = () => {
        let errors = {}
       // --------------------------------------------------------------
        console.log("Before Validations")
        if((role === "USER" || role === "") && userSignin.response &&userSignin.response.data.role === "ADMIN")
            setRoleError("Select a valid role")
        else
            setRoleError("")

    //------------------------------------------------------------------------
        if(name.length<1){
           // setNameError(' Name is required'); 
            toast.error("Name is required") 
        }
       else if(name.match(valid)){

       }
       else {
        //setNameError("enter Valid name ");
        toast.error("enter Valid name");
       }
       //----------------------------------------------------------------------


      // email validation
        if(email.length<1){
            //setEmailError('email is Required');
            toast.error("email is Required");
        }
         else if(email.match('[0-9a-z]+@[a-z]+\.[a-z]{2,3}')){
          
          }
          else
          {
           // setEmailError('valid email is Required');
            toast.error("valid email is Required")
          }
          //-----------------------------------------------------------------
         //password validation
        
        if(password.length<3){
          //  setPasswordError("Password is required ");
            toast.error("Password is required");
        }
        
     else if(!password.match("((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]))")){
    
        //setPasswordError("Enter Strong Password");
        toast.error("Enter Strong Password");
        }
        else
       {

       }
       //-------------------------------------------------------------------
        //mobile_no validation
        if(!mobile_no)
        {
       
           // setMobile_noError("Mobile No is required")
            toast.error("Mobile No is required");
        }
        else if(mobile_no.length>10 || mobile_no.length<10)
        {
            
           // setMobile_noError("Enter 10 Digit Mobile Number");
            toast.error("Enter 10 Digit Mobile Number");
        }
        else{
           
        }
        
        
       //------------------------------------------------------------
        if(aadhar_card.length>12 && aadhar_card.length<12){
           
          //  setAadhar_cardError("Aadhaar No must be required")
            toast.error("Aadhaar No must be required");
        }
        else if(aadhar_card.match('(^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$)')){
            // String str1 = "3675 9834 6015";
            
        }
        else {
        //setAadhar_cardError(' Aadhar number is Required');
          toast.error("Aadhar number is Required");
        }
        //-----------------------------------------------------------------
        //license_no validation  HR-0619850034761
        if(!license_no) { 
       
            //setLicense_noError("License No must be Required")
            toast.error("License No must be Required");
        }
        else if(license_no.length>15 || license_no.length<15){
        
        }
        else if(license_no.match( "^(([A-Z]{2}[0-9]{2})"
        + "( )|([A-Z]{2}-[0-9]"
        + "{2}))((19|20)[0-9]"
        + "[0-9])[0-9]{7}$")){
        }
        else{
            //setLicense_noError("enter valid license_no Number ")
            toast.error("enter valid license_no Number");
        }

        //-------------------------------------------------------------------------------
        if(birthdayDate.getDate==="dd-mm-yyyy"){
           // setDobError("Date of Birth must be required");
            toast.error("Date of Birth must be required");
        }
        else if(dtCurrent.getFullYear() - birthdayDate.getFullYear() < 18){
         // setDobError("age is less than 18");
          toast.error("age is less than 18");
             //toast.error("Date of Birth must be required")
        }
        
else{
        //----------------------------------------------------
       // if((Object.keys(errors).length===0))
        if(name && 
            email && 
            password &&
            mobile_no &&
            aadhar_card &&
            license_no &&
            dob)
            {console.log("After Validations")
             dispatch(signup(name,email,password,mobile_no,aadhar_card,license_no,dob,role))
        }}
    }
        useEffect(()=>{
                if(response && response.status === 200){
                    navigate("/signin")
                    dispatch({
                        type : USER_SIGNUP_RESET,
                    })
                }
        },[loading,response,error,nameError,props.history])
    
    return (
        <div className="Screen">
                <div>
                    <Header title="SignUp"/>
                    {
                        error && error.status === "error" && <h5 className="text-danger text-center">{error.message}</h5>
                    }
                    <div className="row">
                        <div className="col-md-3"></div>
                        <div className="col-md-6">
                            
                            <div className="row">
                                <div className="mb-3">
                                    <label className="form-label">Name</label>
                                    <input required onChange={(e)=>{setName(e.target.value)}} type="text" className="form-control"  placeholder="Name"></input>
                                   <h6 className="text-danger text-center">{nameError}</h6>
                                </div>

                            <div className="mb-3">
                                <label className="form-label">Email Id</label>
                                <input required onChange={(e)=>{setEmail(e.target.value)}} type="email" className="form-control"  placeholder="abc@example.com"></input>
                                <h6 className="text-danger text-center">{emailError}</h6>
                            </div>

                            <div className="mb-3">
                                <label className="form-label">Password</label>
                                <input required onChange={(e)=>{setPassword(e.target.value)}} type="password" className="form-control"  placeholder="********"></input>
                                <h6 className="text-danger text-center">{passwordError}</h6>
                            </div>
                            <div className="mb-3">
                                <label className="form-label">Mobile Number</label>
                                <input required onChange={(e)=>{setMobile_no(e.target.value)}} type="mobile_no" className="form-control"  placeholder="+91"></input>
                                <h6 className="text-danger text-center">{mobile_noError}</h6>
                            </div>
                            <div className="mb-3">
                                <label className="form-label">Aadhaar Card</label>
                                <input required onChange={(e)=>{setAadhar_card(e.target.value)}} type="aadhar_card" className="form-control"  placeholder="0000 0000 0000"></input>
                                <h6 className="text-danger text-center">{aadhar_cardError}</h6>
                            </div>
                            <div className="mb-3">
                                <label className="form-label">License No</label>
                                <input required onChange={(e)=>{setLicense_no(e.target.value)}} type="license_no" className="form-control"  placeholder="xx-0000000000000"></input>
                                <h6 className="text-danger text-center">{license_noError}</h6>
                            </div>
                            <div className="mb-3">
                                <label className="form-label">Date of Birth</label>
                                <input required onChange={(e)=>{setDob(e.target.value)}} type="date" className="form-control" id="date" placeholder="YYYY-MM-DD"></input>
                                <h6 className="text-danger text-center">{dobError}</h6>
                            </div>

                            {
                                userSignin.response && userSignin.response.data.role === "ADMIN" &&
                                <div className="mb-3 col-md-3">
                                    <h5 className="form-label">Role</h5>
                                    <select className="form-select" onChange={(e)=>{setRole(e.target.value.toUpperCase())}}>
                                        <option disabled selected>-- Select </option>
                                        <option >USER</option>
                                        <option >EMPLOYEE</option>
                                    </select>
                                    <h6 className="text-danger text-center">{roleError}</h6>
                                </div>
                                
                            }

                            { 
                                !userSignin.response && 
                                <div className="float-end">
                                    <br/> Already a User? <Link to="/signin">SignIn</Link>
                                </div>
                            }

                      
                       <div className="mb-3 float-start">
                                <button onClick={onSignUp} type="button" className="btn btn-outline-primary">SignUp</button>
                        </div>
              
                        </div>
                        <div className="col-md-3"></div>
                    </div>
            </div>
       </div>
       </div>
    )
};
export default SignUpScreen;
