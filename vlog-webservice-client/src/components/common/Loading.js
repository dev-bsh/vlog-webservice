import React, { useEffect } from "react";
import Loader from "../main/Loader";
import LoadingWrapper from "../styled/commonStyled/LoadingWrapper";
import { useDispatch, useSelector } from "react-redux";
import { getCode, loginRequest } from "../../redux/module/login";
import { useNavigate } from "react-router-dom";
import Cookies from "universal-cookie";

const Loading = () => {
  const cookies = new Cookies();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const code = useSelector((state) => state.login.code);
  const user = useSelector((state) => state.login.user);
  const path = cookies.get("path");

  useEffect(() => {
    const authCode = new URL(window.location.href).searchParams.get("code");
    dispatch(loginRequest(path, authCode));
    setTimeout(() => {
      window.location.href = "/";
    }, 500);
  }, []);

  return (
    <LoadingWrapper>
      <Loader />
      <div>잠시만 기다려주세요...</div>
    </LoadingWrapper>
  );
};

export default Loading;
