import React from "react";
import SettingStyled from "../styled/commonStyled/SettingStyled";
import SettingBox from "../styled/commonStyled/SettingBox";
import { useDispatch } from "react-redux";
import { deleteUser } from "../../redux/module/login";
import { useNavigate } from "react-router-dom";
import SignOutButton from "../styled/commonStyled/SignOutButton";
import Cookies from "universal-cookie";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

toast.configure();
const Setting = () => {
  const cookies = new Cookies();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleClick = () => {
    dispatch(deleteUser(cookies.get("user").userId));
    toast("회원탈퇴 되었습니다. :)", {
      position: toast.POSITION.TOP_CENTER,
    });
    navigate("/");
    setTimeout(() => {
      window.location.reload();
    }, 1000);
  };

  return (
    <SettingStyled>
      <SettingBox>
        <SignOutButton onClick={handleClick}>회원 탈퇴</SignOutButton>
      </SettingBox>
    </SettingStyled>
  );
};

export default Setting;
