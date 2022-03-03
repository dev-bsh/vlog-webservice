import React from "react";
import SigninWrapper from "../styled/signinStyled/SigninWrapper";
import IconWrapper from "../styled/signinStyled/IconWrapper";
import SignIconBlock from "../styled/signinStyled/SignIconBlock";
import GlobalWrapper from "../styled/commonStyled/GlobalWrapper";
import { FcGoogle } from "react-icons/fc";
import { RiKakaoTalkFill } from "react-icons/ri";
import { SiNaver } from "react-icons/si";
import { createPortal } from "react-dom";
import OverlayStyled from "../styled/modalStyled/OverlayStyled";
import SigninModalStyled from "../styled/signinStyled/SigninModalStyled";
import CloseButton from "../styled/modalStyled/CloseButton";
import { MdClose } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { loginRequest, getPath } from "../../redux/module/login";
import Cookies from "universal-cookie";

const SignIn = ({ isOpened, children, onClose }) => {
  const cookies = new Cookies();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isLoggedIn = useSelector((state) => state.login.isLoggedIn);

  if (!isOpened) {
    return null;
  }

  const handleAuthClick = (e) => {
    const path = e.target.dataset.auth;
    cookies.set("path", path, { path: "/" });
    window.location.href = `/api/v1/login/${path}`;
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

          <IconWrapper onClick={handleAuthClick} data-auth="google">
            <SignIconBlock>
              <FcGoogle />
            </SignIconBlock>
            Login with Google
          </IconWrapper>
          <IconWrapper onClick={handleAuthClick} data-auth="kakao">
            <SignIconBlock>
              <RiKakaoTalkFill />
            </SignIconBlock>
            Login with KakaoTalks
          </IconWrapper>

          <IconWrapper onClick={handleAuthClick} data-auth="naver">
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
