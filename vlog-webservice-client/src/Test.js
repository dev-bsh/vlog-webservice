import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { GoogleLogin } from "react-google-login";
const Test = () => {
  const [inputs, setInputs] = useState({
    email: "",
    password: "",
  });

  const handleGoogleClick = async () => {
    await axios.get("http://localhost:3000").then((res) => {
      console.log(res);
    });
  };

  const fetchPost = async () => {
    const response = await axios.get(
      "https://jsonplaceholder.typicode.com/posts"
    );
    console.log(response);
    console.log("headers", response.headers);
  };
  const code = new URL(window.location.href).searchParams.get("code");
  console.log(code)

  return (
    <div>
      <button onClick={fetchPost}>{code}</button>
      <GoogleLogin
        clientId="361528373689-cb76q46e3uuqoc90299inhil1no02e4j.apps.googleusercontent.com"
        success={handleGoogleClick}
        failure={handleGoogleClick}
      />
    </div>
  );


};

export default Test;
