import React, { useState } from "react";
import { Link } from "react-router-dom";
import UserBlock from "./UserBlock";
import SignIn from "../signin/SignIn";
import LoginButtonStyled from "../styled/commonStyled/LoginButtonStyled";

const Login = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);

  const toggleModal = () => {
    setIsLoginModalOpen(!isLoginModalOpen);
  };
  return isLoggedIn ? (
    <Link to="/channel">
      <UserBlock />
    </Link>
  ) : (
    <div>
      <LoginButtonStyled onClick={toggleModal}>Login</LoginButtonStyled>
      <SignIn isOpened={isLoginModalOpen} onClose={toggleModal} />
    </div>
  );
};

export default Login;
