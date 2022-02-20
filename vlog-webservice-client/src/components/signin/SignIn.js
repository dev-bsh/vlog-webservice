import React from "react";
import SigninWrapper from "../styled/signinStyled/SigninWrapper";
import IconWrapper from "../styled/signinStyled/IconWrapper";
import SignIconBlock from "../styled/signinStyled/SignIconBlock";
import GlobalWrapper from "../styled/commonStyled/GlobalWrapper";
import { FcGoogle } from "react-icons/fc";
import { RiKakaoTalkFill } from "react-icons/ri";
import { FaFacebook } from "react-icons/fa";
import { createPortal } from "react-dom";
import OverlayStyled from "../styled/modalStyled/OverlayStyled";
import SigninModalStyled from "../styled/signinStyled/SigninModalStyled";
import CloseButton from "../styled/modalStyled/CloseButton";
import { MdClose } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { loginRequest } from "../../redux/module/login";
import {SiNaver} from "react-icons/all";

const SignIn = ({ isOpened, children, onClose }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isLoggedIn = useSelector((state) => state.login.isLoggedIn);

  if (!isOpened) {
    return null;
  }
  const handleGoogleClick = async (e) => {
    e.preventDefault();
    window.location.href="http://localhost:8080/api/v1/login/google";
  };
  const handleNaverClick = async (e) => {
    e.preventDefault();
    window.location.href="http://localhost:8080/api/v1/login/naver"
  };
  const handleKakaoClick = async (e) => {
    e.preventDefault();
    window.location.href="http://localhost:8080/api/v1/login/kakao"
  };
  return createPortal(
    <GlobalWrapper>
      <OverlayStyled></OverlayStyled>

      <SigninModalStyled>
        <CloseButton onClick={onClose}>
          <MdClose color="black" />
        </CloseButton>

        <SigninWrapper>
          <div>
            <p>Join in MAEN</p>
          </div>
          <IconWrapper onClick={handleGoogleClick}>
            <SignIconBlock>
              <FcGoogle />
            </SignIconBlock>
            Login with Google
          </IconWrapper>
          <IconWrapper onClick={handleKakaoClick}>
            <SignIconBlock>
              <RiKakaoTalkFill />
            </SignIconBlock>
            Login with KakaoTalks
          </IconWrapper>
          <IconWrapper onClick={handleNaverClick}>
            <SignIconBlock>
              <SiNaver />
            </SignIconBlock>
            Login with Naver
          </IconWrapper>
        </SigninWrapper>
      </SigninModalStyled>
    </GlobalWrapper>,
    document.getElementById("signin")
  );
};

export default SignIn;
