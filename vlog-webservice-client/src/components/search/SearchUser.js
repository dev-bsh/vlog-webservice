import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getSearch } from "../../redux/module/search";
import SearchUserStyled from "../styled/searchStyled/SearchUserStyled";
import UserBlock from "../common/UserBlock";
import SearchResultWrapper from "../styled//searchStyled/SearchResultWrapper";
import { Link } from "react-router-dom";

const SearchUser = () => {
  const userNameList = ["철수", "짱구", "훈이"];

  const userList = userNameList.map((user, index) => (
    <Link to={`/channel/${user}`} key={index}>
      <UserBlock key={index} userName={user} />
    </Link>
  ));
  return (
    <SearchUserStyled>
      <p>Users</p>
      <SearchResultWrapper>{userList}</SearchResultWrapper>
    </SearchUserStyled>
  );
};

export default SearchUser;
