import React, { useState } from "react";
import expireToken from "./utils/expireToken";
import axios from "axios";
import Cookies from "universal-cookie";
const Test = () => {
  const cookies = new Cookies();
  const accessToken = cookies.get("user").accessToken;
  const refreshToken = cookies.get("user").refreshToken;
  const user = cookies.get("user");

  console.log(user);
  const config = {
    headers: {
      ACCESS_TOKEN: accessToken,
    },
  };

  const handleClick = async () => {
    expireToken();
  };
  return (
    <>
      <input type="text" placeholder="..." />
      <button onClick={handleClick}>click</button>
    </>
  );
};

export default Test;
