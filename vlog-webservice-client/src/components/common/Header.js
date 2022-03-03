import React, { useState, useEffect } from "react";
import HeaderStyled from "../styled/commonStyled/HeaderStyled";
import Wrapper from "../styled/commonStyled/Wrapper";
import Logo from "./Logo";
import Search from "./Search";
import UserBlock from "./UserBlock";
import Login from "./Login";
import UploadButton from "../common/UploadButton";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import Logout from "../styled/commonStyled/Logout";
import DropDown from "../styled/commonStyled/DropDown";
import { logoutRequset } from "../../redux/module/login";
import { useNavigate } from "react-router-dom";
import { MdAccountCircle, MdLogout } from "react-icons/md";
import { BsGear } from "react-icons/bs";
import IconWrapper from "../styled/commonStyled/IconWrapper";
import Cookies from "universal-cookie";
import UserProfileStyled from "../styled/commonStyled/UserProfileStyled";
import axios from "axios";
import Setting from "./Setting";
const Header = () => {
  const cookies = new Cookies();
  const isLoggedIn = cookies.get("isLoggedIn");
  const userData = cookies.get("user");
  const [isVisible, setIsVisible] = useState(false);
  const dispatch = useDispatch();

  const handleClick = () => {
    setIsVisible(!isVisible);
  };

  const handleLogout = () => {
    dispatch(logoutRequset(cookies.get("user").userId));
    window.location.href = "/";
    setTimeout(() => {
      window.location.reload();
    });
  };

  return (
    <HeaderStyled>
      <Logo />
      <Search />
      <Wrapper>
        {isLoggedIn && <UploadButton />}
        {isLoggedIn ? (
          <div onClick={handleClick}>
            <UserBlock
              userId={userData.userId}
              userName={userData.name}
              src={cookies.get("user").picture}
            >
              {userData.name}
            </UserBlock>
            <DropDown visible={isVisible}>
              <Link
                to={`/channel/${userData.name}/${userData.userId}`}
                color="#7f8fa6"
              >
                <IconWrapper>
                  <MdAccountCircle />
                  Channel
                </IconWrapper>
              </Link>
              <Link to={`/setting`}>
                <IconWrapper>
                  <BsGear />
                  Setting
                </IconWrapper>
              </Link>
              <Logout onClick={handleLogout}>
                <IconWrapper>
                  <MdLogout />
                  Logout
                </IconWrapper>
              </Logout>
            </DropDown>
          </div>
        ) : (
          <Login />
        )}
      </Wrapper>
    </HeaderStyled>
  );
};

export default Header;
