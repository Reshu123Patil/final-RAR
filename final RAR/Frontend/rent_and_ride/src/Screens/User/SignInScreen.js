import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { signin } from "../../Actions/UserActions";
import Header from "../../Components/Header";
//import { Toast } from "react-bootstrap";


import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const SignInScreen = (props) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const dispatch = useDispatch();

  let navigate = useNavigate()

  const { loading, response, error } = useSelector((store) => store.userSignin);
  console.log('--------------- ', loading, response, error);

  const onSignIn = () => {

    if(email.length<1 ){
      toast.error("Email is Required");
    }
    else if(password.length<1){
      toast.error("Password is Required");
    }
    else{
    dispatch(signin(email, password));
   console.log('unser signin '+ email, password);
    }
  };

  useEffect(() => {
    console.log('--------------- ',response);
    console.log('user effect'+JSON.stringify(response));
    if (response && response.status ===200) {
      sessionStorage.setItem("Authorization", "Bearer " + response.token);
      if (response.data.role === "ADMIN") {
        navigate("/admin-home");
        toast.success("Admin SuccessFully Login")
      } else if (response.data.role === "EMPLOYEE") {
        navigate("/employee-home");
        toast.success("EMPLOYEE SuccessFully Login")
      } else if (response.data.role === "USER") {
        navigate('/user-home')
        toast.success("USER SuccessFully Login")
      } 
    }
  }, [loading, response, error, props.history]);

  return (
    <div className="Screen">
      <Header title="Login" />
      {error && error.status === "error" && (
        <h5 className="text-danger text-center">{error.message}</h5>
      )}
      <div className="mb-3">
        <label className="form-label">Email Address</label>
        <input
          onChange={(e) => {
            setEmail(e.target.value);
          }}
          type="email"
          className="form-control"
          placeholder="abc@example.com"
        />
      </div>

      <div className="mb-3">
        <label className="form-label">Password</label>
        <input
          onChange={(e) => {
            setPassword(e.target.value);
          }}
          type="password"
          className="form-control"
          placeholder="*******"
        />
      </div>

      <div className="float-end">
          <br/>
          New around here? <Link to="/signup">Sign Up</Link>
      </div>
      <div>
      
      <a href ="/forgetPasswordUser" 
      style={{color: "#393f81"}}>
      Forget Password?{" "}
      </a>
      </div>


      <div className="mb-3">
      <br/>
        <button onClick={onSignIn} className="btn btn-outline-info">
          Login
        </button>
       
      </div>
    </div>
  );
};

export default SignInScreen;
