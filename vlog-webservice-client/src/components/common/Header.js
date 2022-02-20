import React, { useState } from "react";
import HeaderStyled from "../styled/commonStyled/HeaderStyled";
import Wrapper from "../styled/commonStyled/Wrapper";
import Logo from "./Logo";
import Search from "./Search";
import UserBlock from "./UserBlock";
import Login from "./Login";
import UploadButton from "../common/UploadButton";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

const Header = () => {
  const isLoggedIn = useSelector((state) => state.login.isLoggedIn);

  const [user, setUser] = useState("철수");
  return (
    <HeaderStyled>
      <Logo />
      <Search />
      <Wrapper>
        {isLoggedIn && <UploadButton />}
        {isLoggedIn ? (
          <Link to={`/channel/${user}`}>
            <UserBlock userName={user} />
          </Link>
        ) : (
          <Login />
        )}
      </Wrapper>
    </HeaderStyled>
  );
};

export default Header;
